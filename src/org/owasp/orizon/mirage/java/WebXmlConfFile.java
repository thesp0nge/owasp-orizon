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

import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.ConfFile;
import org.owasp.orizon.core.XmlConfFile;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonXmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class models web.xml file
 * 
 * @author thesp0nge
 * @since 1.30
 * @see ConfFile
 */
public class WebXmlConfFile extends XmlConfFile {
	private Vector<String> welcomePages;
	private Vector<String> servletClasses;
	private Servlets		servlets;
	private Filters			filters;
	private String appname;
	private static Logger	log = Logger.getLogger(WebXmlConfFile.class);
	
	public WebXmlConfFile(String s) throws OrizonXmlException, OrizonPanicException {
		super(s);
		welcomePages = new Vector<String>();
		servletClasses = new Vector<String>();
		servlets = new Servlets();
		filters = new Filters();
	}

	@Override
	public boolean read() throws OrizonXmlException, OrizonPanicException {
		boolean ret = true;
		Document root = getDocumentRoot();
		
		/* 
		 * ACTION 1. Retrieving "welcome-file" so Orizon will known from 
		 * where starting jsp pages modeling.
		 */
		NodeList wpageList = root.getElementsByTagName("welcome-file");
		
		if (wpageList==null || wpageList.getLength() == 0) {
			log.warn("no welcome page found. Setting default welcome page to index.jsp");
			welcomePages.add("index.jsp");
		 } else {
			for (int i=0; i<wpageList.getLength(); i++)
				welcomePages.add(wpageList.item(i).getFirstChild().getNodeValue());
			
		}
		
		/*
		 * ACTION 2. Check if there are some exposed servlets and if than, 
		 * retrieving the servlet list. Orizon will use this list to look for 
		 * auxiliary source files to review
		 */
		NodeList servList = root.getElementsByTagName("servlet");
		NodeList servMap = root.getElementsByTagName("servlet-mapping");
		
		for (int i=0; i<servList.getLength(); i++) {
			Node n = servList.item(i);
			Servlet servlet = new Servlet(n);
			// take all servlet-mapping
			for (int j=0; j<servMap.getLength(); j++)
				servlet.addMapping(servMap.item(j));
			servlets.add(servlet);
		}

		/*
		 * ACTION 3. Do the same thing we did in ACTION 2. but for filters 
		 * instead of servlets 
		 */
		NodeList filtList = root.getElementsByTagName("filter");
		NodeList filtMap = root.getElementsByTagName("filter-mapping");
		for (int i=0; i<filtList.getLength(); i++) {
			Node n = filtList.item(i);
			Filter fi = new Filter(n);
			// take all servlet-mapping
			for (int j=0; j<filtMap.getLength(); j++)
				fi.addMapping(filtMap.item(j));
			filters.add(fi);
		}
		
		// LAST ACTION. Retrieve the app "display-name" just for information purpose
		appname = root.getElementsByTagName("display-name").item(0).getFirstChild().getNodeValue();
		
		return ret;
		
	}
	
	public Vector<String> getWelcomePages() {
		return welcomePages;
	}
	public Servlets getServlets() {
		return servlets;
	}
	public Filters getFilters() {
		return filters;
	}
	
	public String getAppName() {
		return appname;
	}

}
