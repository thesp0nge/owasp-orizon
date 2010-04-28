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
import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.SourceFile;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.model.EntryPoint;
import org.owasp.orizon.model.HtmlEntryPoint;
import org.owasp.orizon.model.Link;
import org.owasp.orizon.model.MatchingPoint;

/**
 * This class models a Jsp dynamic page
 * @author thesp0nge
 * @since 1.30
 * @see SourceFile
 */
public class JspSourceFile extends SourceFile {
	private JspCollector collector;
	private Vector<HtmlEntryPoint> htmlEntryPoints;
	private Vector<Link> links;
	private Vector<MatchingPoint> matches;
	private Logger log = Logger.getLogger(JspSourceFile.class);
	
	public JspSourceFile(String name) throws OrizonIOException, OrizonPanicException, OrizonParseException {
		super(name);
		collector = new JspCollector(new File(name));
		collector.model();
		htmlEntryPoints = collector.getHtmlEntryPoints();
		matches = collector.getMatches();
		links = collector.getLinks();
		stats = collector.getStats();
	}
	
	
	public boolean hasLinks() {
		return (links.size()!=0);
	}

	public Vector<Link> getLinks() {
		return links;
	}
	
	public String toString() {
		return collector.toString();
	}
	
	public void printLinks() {
		for (Link l:links)
			log.debug(l.toString());
	}
	public final Vector<HtmlEntryPoint> getEntryPoints() {
		return htmlEntryPoints;
	}
}
