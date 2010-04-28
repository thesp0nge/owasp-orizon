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
package org.owasp.orizon.mirage.java;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.core.ParseFailure;
import org.owasp.orizon.core.SourceFile;
import org.owasp.orizon.core.WebApp;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.exceptions.OrizonXmlException;
import org.owasp.orizon.mirage.Identify;
import org.owasp.orizon.mirage.jsp.JspSourceFile;
import org.owasp.orizon.model.EntryPoint;


/**
 * This is the class that models a j2ee web application.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class J2eeApp extends WebApp {
	// this is the web.xml file
	private WebXmlConfFile webxml;
	private static Logger log = Logger.getLogger(J2eeApp.class);

	/**
	 * Creates a new J2eeApp object.
	 * 
	 * The first task up to the constructor is to use {@link Identify} class to
	 * scan the root folder gathering the file names.
	 * 
	 * <code>J2eeApp()</code> will be also responsible about labeling each known
	 * file contained in the root directory.
	 * 
	 * @param webroot the web application root directory
	 * @throws OrizonPanicException
	 * @throws OrizonXmlException 
	 * @throws OrizonParseException 
	 * @throws OrizonIOException 
	 */
	public J2eeApp(String webroot) throws OrizonPanicException, OrizonXmlException, OrizonIOException {
		super(webroot);
		Identify i = new Identify(getWebroot());
		if (i.tell() != Constants.O_APP_J2EE)
			throw new OrizonPanicException("non j2ee app, passed to J2eeApp class (" + i.tell() + "). Giving up!");
		Vector<String> nameList = i.getFileListing(new File(getWebroot()));
		
		for (String s:nameList) {
			switch (i.ident(s)) {
			case Constants.O_JAVA:
				JavaSourceFile j;
					try {
						j = new JavaSourceFile(s);
						stat.add(j.getStats());
						bobjs.add(j);
					} catch (OrizonParseException e1) {
						if (Orizon.beVerbose())
							log.trace(s + " has failed parsing");
						bobjErrors.add(new ParseFailure(s, e1.getMessage()));
					}
				
				break;
			case Constants.O_JSP:
				/*
				 * I won't raise a OrizonParseException here because I do want
				 * to parse all the files even I find a file that my language
				 * pack is not able to understand.
				 */
				try {
					JspSourceFile jsp = new JspSourceFile(s);
					stat.add(jsp.getStats());
					pages.add(jsp);
				} catch (OrizonParseException e) {
					if (Orizon.beVerbose())
						log.trace(s + " has failed parsing");
					pageErrors.add(new ParseFailure(s, e.getMessage()));
				}
				break;
			case Constants.O_WEBXML:
				confs.add(new WebXmlConfFile(s));
				webxml = new WebXmlConfFile(s);
				break;
			default:
				break;
			}
		}
		
	}

	/**
	 * This method builds a model for the given j2ee web application.
	 * 
	 * @throws OrizonXmlException
	 * @return <i>true</i> if the model can be built, <i>false</i> otherwise.
	 * @throws OrizonPanicException 
	 */
	public boolean model() throws OrizonXmlException, OrizonPanicException {
		boolean ret = false;
		
		/*
		 * Read the information stored in the web.xml
		 * 
		 * If the read() method returns false, Orizon will raise an exception since a very corrupted web.xml file was found. 
		 * In the future will be added the capability to ignore web.xml, but in the present... Orizon needs a good conf file.
		 * 
		 */
		if (! webxml.read() )
			throw new OrizonPanicException("the given web.xml is malformed. Orizon is not able to gather the necessary information to build the application model. Giving up!");
		if ( webxml.getAppName() != null &&  ! "".equals(webxml.getName()))
			setName(webxml.getAppName());
		
		/*
		 * Starting from the welcome page, we will create a JspSourceFile object
		 * that will internally use a JspCollector to collect data.
		 */
		Vector<String> wPages = webxml.getWelcomePages();
		
		
		log.debug("IMPLEMENT JAVACOLLECTOR");
		return ret;
	}

	@Override
	public void dump() {
		
		if (Orizon.isInDebugMode()) {
			log.debug("Application name is: " + getName());
			log.debug("Web root is: " +getWebroot());
			log.debug(pages.size() + " pages found");
			log.debug(bobjs.size() + " classes found");
			log.debug(pageErrors.size() + " pages were not successfully parsed");
			log.debug(bobjErrors.size() + " classes were not successfully parsed");
			log.debug(webxml.getServlets().size() +" servlets found");
			log.debug(webxml.getFilters().size() + " filters found");
			log.debug("---------------------------");
			if (Orizon.beVerbose()) {
				for (int z=0; z<pageErrors.size();z++) 
					log.debug(pageErrors.get(z).toString());
				log.debug("---------------------------");
				for (int z=0; z<bobjErrors.size();z++) 
					log.debug(bobjErrors.get(z).toString());
			}
			log.debug("---------------------------");
			log.debug(stat.toString());
			log.debug("---------------------------");
			for (String s:webxml.getWelcomePages()) {
				for (SourceFile p:pages) {
					JspSourceFile jsp = (JspSourceFile) p;
					if (jsp.getName().contains(s)) {
						log.debug(p.toString());
						// jsp.printLinks();
						// log.debug(jsp.getLinks().size() + " links found for " + s);
					}
				}
			}
			
			
		}
		
		return ;
	}
	@Override
	public Vector<EntryPoint> getEntryPoints() {
		Vector<EntryPoint> ret = new Vector<EntryPoint>();
		for (SourceFile p:pages) {
			JspSourceFile jsp = (JspSourceFile) p;
			ret.addAll(jsp.getEntryPoints());
		}
		return ret;
	}
}
