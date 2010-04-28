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
 */
package org.owasp.orizon.mirage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.owasp.orizon.core.Constants;
import org.owasp.orizon.utils.BooleanUtils;

/**
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class Identify {
	private int known;
	private int total;
	private boolean init;
	private File dir;
	private Vector<String> filenames;
	private int appcode;
	private int errno;
	private boolean generic;
	
	public Identify() {
		init = true;
	}
	public Identify(String d) {
		
		if ((d == null) || (d.equals(""))) 
			init = false;
		else {
			known = 0;
			total = 0;
			dir = new File(d);
			appcode = 0;
			init = true;
			errno = 0;
			generic = false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int guess() {
		return tell();
	}
	public int getErrNo() {
		return errno;
	}
	public int tell() {
		if (!init)
			return Constants.O_E_OBJECT_INIT;
		if (dir == null)
			return Constants.O_E_INVALID_PARAM;
		if (!dir.isDirectory())
			return Constants.O_E_NOT_A_DIR;
		/*
		 * step 1. now orizon will gather all the file names within the
		 * directory specified as parameter used with the object constructor.
		 * 
		 * Orizon will use this list to see if it is able to guess the type of
		 * the application.
		 */		
		filenames = getFileListing(dir);
		
		/*
		 * step 2. loop into the files list and try to identify what kind of 
		 * technolgy has been used to develop the application
		 */
		return ident(filenames);
	}
	
	
	public int ident(List<String> list){
		int codes = 0;
		if (!init)
			return Constants.O_E_OBJECT_INIT;
		if ((list == null) || list.isEmpty())
			return Constants.O_E_INVALID_PARAM;
		
		for (String n : list) {
			codes |= ident(n);
		}
		return (appcode = detectApplicationType(codes));
	}
	public String toString() {
		switch (appcode) {
		case Constants.O_APP_J2EE:
			return (!isGeneric()) ? "regular J2EE web application" : "generic J2EE application";
		case Constants.O_APP_DOT_NET:
			return "regular .NET application";
		}
		return "unknown application kind.";
	}
	
	private int detectApplicationType(int c) {
		int ret = Constants.O_E_LANG_UNKNOWN;
		if (c == 0)
			return Constants.O_E_LANG_UNKNOWN;
		if (BooleanUtils.isSet(c, Constants.O_WEBXML) && 
				BooleanUtils.isSet(c, Constants.O_JAVA) && 
				BooleanUtils.isSet(c, Constants.O_JSP))
			ret = Constants.O_APP_J2EE;
		if (BooleanUtils.isSet(c, Constants.O_WEBXML) && 
				(! BooleanUtils.isSet(c, Constants.O_JAVA) || 
						BooleanUtils.isSet(c, Constants.O_JSP))) {
			ret = Constants.O_APP_J2EE;
			errno = Constants.O_E_APP_WEB_MALFORMED;
		}
		if (BooleanUtils.isSet(c, Constants.O_WEBXML) && 
				! BooleanUtils.isSet(c, Constants.O_JAVA) &&
				! BooleanUtils.isSet(c, Constants.O_JSP)) {
			ret = Constants.O_APP_J2EE;
			errno = Constants.O_E_APP_WEB_CORRUPTED;
		}
		
		if (! BooleanUtils.isSet(c, Constants.O_WEBXML) && 
				( BooleanUtils.isSet(c, Constants.O_JAVA) || 
						BooleanUtils.isSet(c, Constants.O_JSP))) {
			ret = Constants.O_APP_J2EE;
			generic=true;
		}
		return ret;
	}
	public boolean isGeneric(){
		return generic;
	}

	public int ident(String filename) {
		if (!init)
			return Constants.O_E_OBJECT_INIT;
		if (filename.endsWith(".java"))
			return Constants.O_JAVA;
		if (filename.endsWith(".cs"))
			return Constants.O_CSHARP;
		if (filename.endsWith(".php") || filename.endsWith(".php3") || filename.endsWith(".php4") || filename.endsWith(".php5"))
			return Constants.O_PHP;
		if (filename.endsWith(".c"))
			return Constants.O_C;
		if (filename.endsWith(".jsp"))
			return Constants.O_JSP;
		if (new File(filename).getName().equals("web.xml"))
			return Constants.O_WEBXML;
		return Constants.O_E_LANG_UNKNOWN;
	}

	public Vector<String> getFileListing(File aStartingDir) {
		Vector<String> result = new Vector<String>();
		File[] filesAndDirs = aStartingDir.listFiles();
		List<File> filesDirs = Arrays.asList(filesAndDirs);
		for(File file : filesDirs) {
			total++;
			// add a file if known
			int code;
			if ((code = ident(file.getAbsolutePath())) != Constants.O_E_LANG_UNKNOWN) {
				// Later we will include also a list of Sources so we don't need
				// to scan a directory twice.
				// Source s = new Source(file.getAbsolutePath() , code);
				// s.setFile(file);
				// result.add(s); 
				result.add(file.getAbsolutePath());
				known++;		
			}
			// isDirectory() test condition here is necessary to avoid 
			// symbolic link to be followed.
			
			if ( ! file.isFile() && file.isDirectory()) {
				//must be a directory
				//recursive call!
				List<String> deeperList = getFileListing(file);
				result.addAll(deeperList);
			}
		}
		return result;
	}
}
