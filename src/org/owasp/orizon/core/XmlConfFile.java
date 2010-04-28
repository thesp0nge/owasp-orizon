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
package org.owasp.orizon.core;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonXmlException;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;

import com.sun.xml.internal.dtdparser.Resolver;

public abstract class XmlConfFile extends ConfFile {
	protected DocumentBuilderFactory docBuilderFactory;

	protected DocumentBuilder docBuilder;

	protected Document doc;
	private static Logger log = Logger.getLogger(XmlConfFile.class);
	
	public XmlConfFile(String s) throws OrizonXmlException, OrizonPanicException {
		super(s);
		xmlDocumentSetup(new File(getName()));
	}

	private static void process(InputStream input, String name) throws IOException {
		InputStreamReader isr = 
			new InputStreamReader(input);
		BufferedReader reader = new BufferedReader(isr);
		String line;
		OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(name)), "8859_1");
		while ((line = reader.readLine()) != null) 
			out.write(line);

		reader.close();
		out.close();
	}
	private boolean xmlDocumentSetup(File f) throws OrizonXmlException, OrizonPanicException {
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(f);
		} catch (ParserConfigurationException e) {
			throw new OrizonXmlException(getName() + " ("+e.getMessage() + ")");
		} catch (SAXException e) {
			throw new OrizonXmlException(getName() + " ("+e.getMessage() + ")");
		} catch (java.net.UnknownHostException e) {
			/*
			 * This case will happen when offline and trying to parse a web.xml
			 * with an external DTD.
			 * 
			 * By default java xml facilities try to fetch external DTD to
			 * validate the XML.
			 * 
			 * Since we don't care about if the web.xml is correct against its
			 * DTD, we can try to disable such checks when offline.
			 */
			log.warn("I can't resolve host " + e.getMessage() + " for external DTD validation" );
			log.warn("I try to disable external DTD validation");
			docBuilderFactory.setNamespaceAware(false);
			docBuilderFactory.setValidating(false);
			try {
				docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			} catch (ParserConfigurationException e1) {
				log.error(e1.getMessage(), e1);
				throw new OrizonPanicException("I can't setup xml document builder factory. Giving up!");
			}
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(f);
			} catch (SAXException e1) {
				throw new OrizonPanicException(getName() + " ("+e1.getMessage() +")");
			} catch (IOException e1) {
				throw new OrizonPanicException(getName() + " ("+e1.getMessage() +")");
			} catch (ParserConfigurationException e1) {
				throw new OrizonPanicException(getName() + " ("+e1.getMessage() +")");
			}
		} catch (IOException e) {
			throw new OrizonPanicException(getName() + " ("+e.getMessage() +")");
		}
		
		if (doc != null) {
			// normalize text representation
			if (doc.getDocumentElement() != null) 
				doc.getDocumentElement().normalize();
			else
				throw new OrizonXmlException("getDocumentElement() returned null...");
		} else 
			throw new OrizonXmlException("parsed DOM is null while is not supposed to be... can't handle this");
		
		return true;
	}
	protected Document getDocumentRoot() {
		return doc;
	}

	
}
