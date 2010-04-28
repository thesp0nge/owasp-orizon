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

/**
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class Constants {
	/**********************************************
	 * Application kind detection                 *
	 **********************************************/
	private static int O_APPKIND = 10;
	// Classic J2EE application, a bunch of JSPs with some Java business logic.
	// A WEB-INF/web.xml is found in the directory and the starting page can be
	// guessed.
	public static int O_AK_J2EE = O_APPKIND +1;
	// Microsoft .NET framework web application
	public static int O_AK_dotNET= O_APPKIND + 2;
	// A Java code implementing a REST interface. So there is no active pages
	// but just business logic code implementing HttpServlet interface instead.
	public static int O_AK_J_REST = O_APPKIND + 3;

	public static int O_AK_UNKNOWN = O_APPKIND + 50;
	
	
	// End of Application kind detection 

	// Errors 
	private static final int O_ERROR = -1;
	public static final int O_E_NOT_A_DIR = O_ERROR - 1;
	public static final int O_E_INVALID_PARAM = O_ERROR - 2;
	public static final int O_E_NULL_PARAM = O_ERROR - 3;
	public static final int O_E_DIR_NOT_FOUND = O_ERROR - 4;
	public static final int O_E_PERM = O_ERROR - 5;
	public static final int O_E_OBJECT_INIT = O_ERROR - 6;
	public static final int O_E_FILE_NOT_FOUND = O_ERROR - 7;
	public static final int O_E_NOT_A_FILE = O_ERROR -8;
	
	// Error strings
	public static final String O_E_MSG_NULL_DIR = "Directory should not be null.";
	public static final String O_E_MSG_DIR_NOT_FOUND = "Directory does not exist: ";
	public static final String O_E_MSG_NOT_A_DIR = "Is not a directory: ";
	public static final String O_E_MSG_CAN_READ_DIR = "Directory cannot be read: ";
	public static final String O_E_MSG_OBJECT_INIT = "Object internal initialization failed: orizon needs to give up now";
	public static final String O_E_MSG_FILE_NOT_FOUND = "File does not exists: ";
	public static final String O_E_MSG_NOT_A_FILE = "Is not a file: ";
	// File types
	public static final int O_JAVA 		= 1;
	public static final int O_CSHARP 	= 2;
	public static final int O_PHP		= 4;
	public static final int O_C			= 8;
	public static final int O_JSP		= 16;
	public static final int O_WEBXML	= 32;
	// = 64;
	// = 128;
	// = 256;
	// = 512;
	// = 1024;
	// = 2048;
	// = 4096;
	// = 8192;
	// = 16384;
	// = 32768;
	// = 65536;
	// = 131072;
	// = 262144; 
	
	
	// Reserved File Types flag to be used to describe the given file

	/**
	 * The file contains regular source code, either business logic than dynamic
	 * page code
	 */
	public static final int O_FTYPE_SRC = 524288; // 2^19
	
	/**
	 * The file is a web app configuration file
	 */
	public static final int O_FTYPE_CONFIG = 1048576; // 2^20
	
	/**
	 * The file is a static web app page
	 */
	public static final int O_FTYPE_WEBPAGE = 2097152; // 2^21
	
	/**
	 * The file is a plain text (log file, readme, ...)
	 */
	public static final int O_FTYPE_TEXT = 4194304; // 2^21
	
	
	// App type
	public static final int O_APP_J2EE	= 1;
	public static final int O_APP_DOT_NET	= 2;
	public static final int O_APP_PHP		= 4;
	public static final int O_APP_REST	= 8;
	public static final int O_APP_GRAILS	= 16;
	public static final int O_APP_RAILS	= 32;
	
	// O_APP_GENERIC is everything but a web application.
	// (e.g. a client server app written in whatever language)
	public static final int O_APP_GENERIC	= 64;
	
	private static final int O_APP_ERROR = -50;
	// O_APP_WEB_MALFORMED is a web application that lacks something but orizon
	// will try to continue the security tests.
	// (e.g. a web.xml file has been found but no jsp pages or business logic).
	// This is a non blocking error
	public static final int O_E_APP_WEB_MALFORMED = O_APP_ERROR - 1;
	
	// O_APP_WEB_CORRUPTED is a a web application that is completely corrupted
	// and for orizon is not possible to continue the tests.
	// (e.g. if a web.config file has been found with java code).
	// This is a blocking error
	public static final int O_E_APP_WEB_CORRUPTED 	= O_APP_ERROR - 2;
	public static final int O_E_LANG_UNKNOWN		= O_APP_ERROR - 3;
	
	/*
	 * Modifiers constant
	 */
	public static final int O_PUBLIC	=	1;
	public static final int O_PRIVATE	=	2;
	public static final int	O_PROTECTED	=	4;
	public static final int O_ABSTRACT	=	8;
	public static final int	O_STATIC	=	16;
	public static final int O_FINAL		= 	32;
}
