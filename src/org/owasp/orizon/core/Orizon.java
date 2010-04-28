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
 * This class is introduced to act as manager for arguments passed to Orizon
 * framework at command line.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class Orizon {
	private final static String ORIZON_ENV_VERBOSE = "orizon_be_verbose";
	private final static String ORIZON_ENV_DEBUG = "orizon_be_debug";
	
	/**
	 * Decide if Orizon must be verbose.
	 * 
	 * When launching Orizon engine if "orizon_be_verbose" is set to 1, then
	 * Orizon will be very verbose during message printing. If environment
	 * variable is set to 0 or not set, Orizon will be more quiet.
	 * 
	 * The standard behaviour is to be as quiet as possible.
	 * 
	 * @return <i>true</i> if variable "orizon_be_verbose" is set to
	 *         "true" or <i>false</i> otherwise.
	 */
	public final static boolean beVerbose() {
		boolean ret = false;
		String env = System.getProperty(ORIZON_ENV_VERBOSE);
		if (env == null)
			return false;
		return new Boolean(env).booleanValue();
	}
	
	/**
	 * Get the log4j.conf file to be used.
	 * 
	 * @return the log4j.conf pathname
	 */
	public final static String getLog4jConf() {
		return (System.getProperty("log4jconfname") == null)?"":System.getProperty("log4jconfname");
	}
	
	/**
	 * Decide if Orizon is in debug mode.
	 * 
	 * When launching Orizon engine, if "orizon_be_debug" is set to "true" then Orizon will be in debug mode.
	 * 
	 * Default is no debug.
	 * 
	 * @return <i>true</i> if variable "orizon_be_debug" is set to
	 *         "true" or <i>false</i> otherwise.
	 */
	public final static boolean isInDebugMode() {
		boolean ret = false;
		String env = System.getProperty(ORIZON_ENV_DEBUG);
		if (env == null)
			return false;
		return new Boolean(env).booleanValue();
	}
}
