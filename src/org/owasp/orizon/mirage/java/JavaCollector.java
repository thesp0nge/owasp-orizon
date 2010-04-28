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
 * @author thesp0nge 
 * @since 1.30
 */
package org.owasp.orizon.mirage.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.mirage.Collector;
import org.owasp.orizon.mirage.java.parser.ClassDeclaration;
import org.owasp.orizon.mirage.java.parser.ClassOrInterfaceBody;
import org.owasp.orizon.mirage.java.parser.ClassOrInterfaceBodyDeclaration;
import org.owasp.orizon.mirage.java.parser.Comment;
import org.owasp.orizon.mirage.java.parser.CompilationUnit;
import org.owasp.orizon.mirage.java.parser.EmptyStatement;
import org.owasp.orizon.mirage.java.parser.FieldDeclaration;
import org.owasp.orizon.mirage.java.parser.Identifier;
import org.owasp.orizon.mirage.java.parser.ImportDeclaration;
import org.owasp.orizon.mirage.java.parser.InterfaceDeclaration;
import org.owasp.orizon.mirage.java.parser.JavaCharStream;
import org.owasp.orizon.mirage.java.parser.JavaConstants;
import org.owasp.orizon.mirage.java.parser.JavaDocComment;
import org.owasp.orizon.mirage.java.parser.JavaLexer;
import org.owasp.orizon.mirage.java.parser.JavaParser;
import org.owasp.orizon.mirage.java.parser.KeyWord;
import org.owasp.orizon.mirage.java.parser.LexicalException;
import org.owasp.orizon.mirage.java.parser.MethodDeclaration;
import org.owasp.orizon.mirage.java.parser.Node;
import org.owasp.orizon.mirage.java.parser.Nodes;
import org.owasp.orizon.mirage.java.parser.ObjectType;
import org.owasp.orizon.mirage.java.parser.ParseException;
import org.owasp.orizon.mirage.java.parser.ReturnType;
import org.owasp.orizon.mirage.java.parser.Token;
import org.owasp.orizon.mirage.java.parser.TypeDeclaration;
import org.owasp.orizon.mirage.java.parser.VariableDeclarator;
import org.owasp.orizon.model.Import;
import org.owasp.orizon.model.Imports;
import org.owasp.orizon.utils.IOUtils;



/**
 * @author thesp0nge
 *
 */
public class JavaCollector extends Collector {
	private JavaLexer lexer;
	private JavaParser parser;
	private Node root;
	private static Logger log = Logger.getLogger(JavaCollector.class);
	private Imports imports;
	private boolean interfaceFound;
	private DumpJavaNode dumper;
	
	
	public JavaCollector(String filename) throws OrizonIOException{
		this(new File(filename));
		stats = new JavaStat();
		imports= new Imports();
	}
	public JavaCollector(File file) throws OrizonIOException {
		super(file);
		stats = new JavaStat();
		imports= new Imports();
		interfaceFound = false;
		try {
			if (IOUtils.isARealFile(f)) {
				lexer = new JavaLexer(new JavaCharStream(new InputStreamReader(new FileInputStream(f))));
				parser = new JavaParser(lexer);
				stats.setLines(IOUtils.countLinesInAFile(f));
			}
       	} catch (FileNotFoundException e) {
			throw new OrizonIOException(Constants.O_E_MSG_FILE_NOT_FOUND + f.getName(), Constants.O_E_FILE_NOT_FOUND);
		} catch (OrizonIOException e) {
			throw e;
		} 
	}
	
	@Override
	public boolean model() throws OrizonPanicException, OrizonParseException {
		boolean ret = true;
		CompilationUnit cu;
		if (parser == null) {
			log.debug("// XXX: this can be better handled with a custom exception to be thrown");
			throw new OrizonPanicException("parser was not properly initialized");
		}
		
		try {
			cu = parser.CompilationUnit();
		} catch (ParseException e) {
			throw new OrizonParseException(filename+ "( " + e.getMessage() +" )");
		} catch (LexicalException e) {
			throw new OrizonParseException(filename+ "( " + e.getMessage() +" )");
		}
		root = parser.rootNode();
		List<Token> tkns = Nodes.getAllTokens(root, true, true);
		for (Token t:tkns) {
			
			if (t instanceof Comment)
				stats.addLineOfComment(1);
			if (t instanceof JavaDocComment)
				stats.addLineOfComment((new StringTokenizer(t.toString(), "\n").countTokens()));
			if (t instanceof KeyWord) {
				if ("if".equals(t.toString())) 
					stats.addCyclomaticComplexityIndex(1);
			}
				//stats.addCyclomaticComplexityIndex(1);
				
			// log.debug(t.getClass().getName() + " : " + t.toString());
		}
		
		try {
			((JavaStat) stats).setEmptyLines(IOUtils.countEmptyLinesInAFile(f));
		} catch (OrizonIOException e) {
			log.error("this should never occur: " +e.getMessage());
		}
		if (Nodes.childrenOfType(root, EmptyStatement.class) != null)
			((JavaStat) stats).addEmptyLines(Nodes.childrenOfType(root, EmptyStatement.class).size());
		
		stats.setLineOfCode(stats.getLines() - stats.getLineOfComment() - ((JavaStat) stats).getEmptyLines());
		
		// Get all the compilation unit "import" declaration
		List<ImportDeclaration> impList = Nodes.childrenOfType(root, ImportDeclaration.class);
		if (impList == null) {
			((JavaStat) stats).setImports(0);
		} else {
			((JavaStat) stats).setImports(impList.size());
			for (ImportDeclaration i:impList) {
				Import ii = new Import(filename, i.getBeginLine());
				ii.setRawValue(i.toString());
				ii.setPackageName(i.getName());
				imports.add(ii);
				log.debug(ii.getPackageName()); 
			}
		}
		
		// 
		
		List<TypeDeclaration> tD = cu.getTypeDeclarations();
		
		for (TypeDeclaration t : tD) {
			KeyWord key = Nodes.firstChildOfType(t, KeyWord.class);
			
			log.debug(key.getId());
			log.debug(JavaConstants.PUBLIC);
			
			InterfaceDeclaration intDec = Nodes.firstChildOfType(t, InterfaceDeclaration.class);
			if (intDec != null)
				log.debug("interface found " + Nodes.firstChildOfType(intDec, Identifier.class));
			
			ClassDeclaration clDec = Nodes.firstChildOfType(t, ClassDeclaration.class);
			if (clDec != null) {
				handlingClass(clDec);
			}
				
			
			
		}
		
		// log.debug(filename + " " +cu.getImportDeclarations().size() + " imports");
		// log.debug(Nodes.childrenOfType(root, IfStatement.class).size() + " if(s) found");
		return ret;
	}
	
	private Class handlingClass(ClassDeclaration clDec) {
		Class ret = new Class();
		log.debug("name: " + Nodes.firstChildOfType(clDec, Identifier.class));
		ClassOrInterfaceBody clBody = Nodes.firstChildOfType(clDec, ClassOrInterfaceBody.class);
		if (clBody == null)
			log.fatal("no penalty for holding. Must raise an exception");
		if (clBody.hasDuplicateMethods())
			log.fatal("duplicated methods found!" + clBody.getDuplicatedMethodName());
		List<ClassOrInterfaceBodyDeclaration> clBodyDecList = Nodes.childrenOfType(clBody, ClassOrInterfaceBodyDeclaration.class);
		for (ClassOrInterfaceBodyDeclaration c:clBodyDecList) {
			FieldDeclaration fD = Nodes.firstChildOfType(c, FieldDeclaration.class);
			if (fD != null)// a field here 
				ret.addField(handlingField(c));
			MethodDeclaration mD = Nodes.firstChildOfType(c, MethodDeclaration.class);
			if (mD != null) // a method here
				ret.addMethods(handlingMethod(c));
		}
		return ret;
	}
	
	private Method handlingMethod(ClassOrInterfaceBodyDeclaration c) {
		Method ret = new Method();
		
		log.debug("entering...");
		List<KeyWord> keyList = Nodes.childrenOfType(c, KeyWord.class);
		// OR'ing the field modifiers 
		
		// FIXME
		// This is duplicated in handlingField. Should be better handled
		for (KeyWord k:keyList) {
			switch (k.getId()) {
			case JavaConstants.PUBLIC:
				ret.setModifiers(Constants.O_PUBLIC);
				break;
			case JavaConstants.PRIVATE:
				ret.setModifiers(Constants.O_PRIVATE);
				break;
			case JavaConstants.STATIC:
				ret.setModifiers(Constants.O_STATIC);
				break;
			case JavaConstants.FINAL:
				ret.setModifiers(Constants.O_FINAL);
				break;
			case JavaConstants.PROTECTED:
				ret.setModifiers(Constants.O_PROTECTED);
				break;
			}
		}
		MethodDeclaration mD = Nodes.firstChildOfType(c, MethodDeclaration.class);
		if (mD == null) {
			log.fatal("a MethodDeclartion not found while it was supposed to be here. Giving up");
			return new Method();
		}
		
		ReturnType retType = Nodes.firstChildOfType(mD,	ReturnType.class);
		if (retType == null) {
			log.fatal("a MethodDeclaration without the ReturnType? It doesn't sound to be a syntattically good java file.");
			return new Method();
		}
		ret.setReturnType(retType.toString());
		// DumpJavaNode.dumpChild(Nodes.firstChildOfType(c, MethodDeclaration.class));
		return ret;
	}
	private Field handlingField(ClassOrInterfaceBodyDeclaration c) {
		Field ret = new Field();
		
		log.warn("do some check around parameter");
		List<KeyWord> keyList = Nodes.childrenOfType(c, KeyWord.class);
		// OR'ing the field modifiers 
		for (KeyWord k:keyList) {
			switch (k.getId()) {
			case JavaConstants.PUBLIC:
				ret.setModifiers(Constants.O_PUBLIC);
				break;
			case JavaConstants.PRIVATE:
				ret.setModifiers(Constants.O_PRIVATE);
				break;
			case JavaConstants.STATIC:
				ret.setModifiers(Constants.O_STATIC);
				break;
			case JavaConstants.FINAL:
				ret.setModifiers(Constants.O_FINAL);
				break;
			case JavaConstants.PROTECTED:
				ret.setModifiers(Constants.O_PROTECTED);
				break;
			}
		}
		FieldDeclaration realFD = Nodes.firstChildOfType(c, FieldDeclaration.class);
		
		// take the field object type
		ObjectType oType = Nodes.firstChildOfType(realFD, ObjectType.class);
		ret.setType(oType.toString());
		
		VariableDeclarator varDec = Nodes.firstChildOfType(realFD, VariableDeclarator.class);
		if (varDec !=null) {
			Identifier id = Nodes.firstChildOfType(varDec, Identifier.class);
			ret.setName(id.getNormalizedText());
		} else {
			Identifier id = Nodes.firstChildOfType(realFD, Identifier.class);
			ret.setName(id.getNormalizedText());
		}
		log.debug(ret.toString());
		return ret;
		
	}
	public boolean isAnInterface() {
		return interfaceFound;
	}
}
