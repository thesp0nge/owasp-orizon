/**
 * This file is part of Owasp Orizon.
 * Owasp Orizon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Owasp Orizon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.owasp.orizon.mirage.jsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.core.Stat;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.mirage.Collector;
import org.owasp.orizon.mirage.jsp.parser.Attribute;
import org.owasp.orizon.mirage.jsp.parser.AttributeValue;
import org.owasp.orizon.mirage.jsp.parser.Element;
import org.owasp.orizon.mirage.jsp.parser.JSP_COMMENT_CONTENT;
import org.owasp.orizon.mirage.jsp.parser.JSP_EXPRESSION;
import org.owasp.orizon.mirage.jsp.parser.JSP_SCRIPTLET;
import org.owasp.orizon.mirage.jsp.parser.JavaCharStream;
import org.owasp.orizon.mirage.jsp.parser.JspLexer;
import org.owasp.orizon.mirage.jsp.parser.JspParser;
import org.owasp.orizon.mirage.jsp.parser.Node;
import org.owasp.orizon.mirage.jsp.parser.Nodes;
import org.owasp.orizon.mirage.jsp.parser.TAG_NAME;
import org.owasp.orizon.mirage.jsp.parser.Token;
import org.owasp.orizon.model.CodeEntryPoint;
import org.owasp.orizon.model.FormEntryPoint;
import org.owasp.orizon.model.HtmlEntryPoint;
import org.owasp.orizon.model.InputEntryPoint;
import org.owasp.orizon.model.JspExitPoint;
import org.owasp.orizon.model.Link;
import org.owasp.orizon.model.Links;
import org.owasp.orizon.model.MatchingPoint;
import org.owasp.orizon.utils.IOUtils;


/**
 * This class is the information collector for JSP language.
 * 
 * 
 * @author thesp0nge
 * @since 1.30
 * @see Collector
 */
public class JspCollector extends Collector {
	private JspParser parser;
	private JspLexer lexer;
	private Node root;
	private Vector<HtmlEntryPoint> htmlEntryPoints;
	private Vector<CodeEntryPoint> codeEntryPoints;
	private Vector<JspExitPoint> jspExitPoints;
	private Vector<MatchingPoint> matches;
	private Links links;
	private static Logger log = Logger.getLogger(JspCollector.class);
	
	public JspCollector(File file) throws OrizonIOException {
		super(file);
		htmlEntryPoints = new Vector<HtmlEntryPoint>();
		codeEntryPoints = new Vector<CodeEntryPoint>();
		jspExitPoints = new Vector<JspExitPoint>();
		matches = new Vector<MatchingPoint>();
		links = new Links();
		stats = new Stat();
		try {
			if (IOUtils.isARealFile(f)) {
				lexer = new JspLexer(new JavaCharStream(new InputStreamReader(new FileInputStream(f))));
				parser = new JspParser(lexer);
				stats.setLines(IOUtils.countLinesInAFile(f));				
			}
		} catch (FileNotFoundException e) {
			parser = null; lexer = null;
			throw new OrizonIOException(Constants.O_E_MSG_FILE_NOT_FOUND + f.getName(), Constants.O_E_FILE_NOT_FOUND);
		} catch (OrizonIOException e) {
			parser = null; lexer = null;
			throw e;
		} 
	}

	@Override
	public boolean model() throws OrizonPanicException, OrizonParseException {
		boolean ret = false;
		String name = null;
		String value = null;
		String javaCode = "";
		String outputCode = "";
		
		
		// XXX: this can be better handled with a custom exception to be thrown
		
		if (parser == null) {
			log.debug("// XXX: this can be better handled with a custom exception to be thrown");
			throw new OrizonPanicException("parser was not properly initialized");
		}
		
		// Stage 0. parsing the jsp file
		try {
			parser.CompilationUnit();
		} catch (Exception e) {
			throw new OrizonParseException(filename+ "( " + e.getMessage() +" )");
		}
		root = parser.rootNode();
		
		List<Token> tk = Nodes.getAllTokens(root, false, false);
		for (Token t:tk) {
			// Stage 1. retrieving JSP entry points
			if (t instanceof TAG_NAME) {
				// Stage 1.a retrieving INPUT fields and FORM fields
				if ("input".equalsIgnoreCase(t.toString())) {
					Element inputElem = (Element) t.getParent();
					InputEntryPoint input = new InputEntryPoint();
					List<Attribute> attrList = Nodes.childrenOfType(inputElem, Attribute.class);
					for (Attribute a:attrList) {
						name = a.getChild(1).toString();
						value = ((AttributeValue) a.getChild(3)).getImage();
						input.addAttribute(name, value);
					}
					input.setFileName(filename);
					input.setLineNo(t.getBeginLine());
					htmlEntryPoints.add(input);
				}
				if ("form".equalsIgnoreCase(t.toString())) {
					
					Element formElem = (Element) t.getParent();
					FormEntryPoint form = new FormEntryPoint();
					List<Attribute> attrList = Nodes.childrenOfType(formElem, Attribute.class);
					for (Attribute a:attrList) {
						name = a.getChild(1).toString();

						value = ((AttributeValue) a.getChild(3)).getImage();
						if ("action".equalsIgnoreCase(name))
							links.add(new Link(filename, value, t.getBeginLine()));
						form.addAttribute(name, value);
					}
					form.setFileName(filename);
					form.setLineNo(t.getBeginLine());
					htmlEntryPoints.add(form);
				}
				// Stage 2. Now try to figure it out the pages linked from this one.
				if ("a".equalsIgnoreCase(t.toString())) {
					Element aElement = (Element)t.getParent();
					List<Attribute> attrList = Nodes.childrenOfType(aElement, Attribute.class);
					for (Attribute a:attrList) {
						if ("href".equalsIgnoreCase(a.getChild(1).toString())) 
							links.add(new Link(filename, new StringTokenizer(a.getChild(3).toString(), "\"").nextToken(), t.getBeginLine()));
					}
					// log.debug("A link found: " + t.toString());
				}
			}
			if (t instanceof JSP_SCRIPTLET) {
				javaCode += t.toString();
				
				int startLine = t.getBeginLine();
				int count = 0;
				
				StringTokenizer stok = new StringTokenizer(javaCode, "\n");
				stats.addLineOfCode(stok.countTokens());
				
				while (stok.hasMoreTokens()) {
					String s = stok.nextToken();
					
					// TODO: it's a better idea to put the pattern for entry points
					// in a database, dynamically read, so Orizon can have a learn
					// phase.
					if (s.contains("request.getParameter")) {
						codeEntryPoints.add(new CodeEntryPoint(s, filename, startLine+count));
					}
					
					// I'm not interested in System.out.println statement at this stage
					if (s.trim().startsWith("out.println"))
						jspExitPoints.add(new JspExitPoint(s, filename, startLine+count));
					count ++;
				}
			}
			if (t instanceof JSP_EXPRESSION) {
				outputCode += t.toString();
			}
			
			if (t instanceof JSP_COMMENT_CONTENT) {
				int l = new StringTokenizer(t.toString(), "\n").countTokens();
				stats.addLineOfComment(l);
			}
			
		}
		
		
		
		/*
		 * This block of code, loops in the exit points found in order to look
		 * for previously saved entry points. If an entry points is found and no
		 * filtering is performed prior using this variable as output this can
		 * be a XSS.
		 */
		for (JspExitPoint j:jspExitPoints) {
			for (CodeEntryPoint c:codeEntryPoints) {
				if (j.match(c.getStoreVariableName())) 
					matches.add(new MatchingPoint(c, j));
				//{
				//	System.err.println("Variable " + c.getStoreVariableName() + " is used as output. If not filtered this can lead to a XSS vulnerability.");
				//	System.err.println(c.toString());
				//	System.err.println(j.toString());
				//}
			}
		}
		return ret;
	}
	
	public Vector<MatchingPoint> getMatches() {
		return matches;
	}
	public Vector<HtmlEntryPoint> getHtmlEntryPoints() {
		return htmlEntryPoints;
	}
	public Vector<CodeEntryPoint> getCodeEntryPoints() {
		return codeEntryPoints;
	}
	public Vector<JspExitPoint> getJspExitPoints() {
		return jspExitPoints;
	}

	public Vector<Link> getLinks() {
		return links.get();
	}
	
	public String toString() {
		if (htmlEntryPoints.size() > 0) {
			String ret = getFileName()+"\n";
			for (HtmlEntryPoint h:htmlEntryPoints) {
				ret += h.toString() + "\n";
			}
			return ret;
		}

		return "";
	}
	
}
