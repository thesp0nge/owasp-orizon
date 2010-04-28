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
package org.owasp.orizon.model;

import java.util.StringTokenizer;

import org.owasp.orizon.exceptions.OrizonParseException;

public class CodeEntryPoint extends EntryPoint {
	/**
	 * This is the name of the HTTP request parameter being read by the dynamic web page.
	 */
	protected String requestParamName;
	/**
	 * This is the name of the variable the parameter just read is stored.
	 */
	protected String storeVariableName;
	
	protected String storeVariableType;
	
	
	
	public CodeEntryPoint(String source, String filename, int lineNo) throws OrizonParseException {
		super(filename, lineNo);
		// Trying to break the string down to the following pattern:
		// type var = request.getParameter("param");
		StringTokenizer epTok = new StringTokenizer(source, "=");
		if (epTok.countTokens() == 2)
		{
			// got it
			StringTokenizer fTok = new StringTokenizer(epTok.nextToken(), " ");
			storeVariableType = fTok.nextToken();
			storeVariableName = fTok.nextToken();
			String tmp = epTok.nextToken();
			requestParamName = tmp.substring(tmp.indexOf("(\"")+2, tmp.length()-4);
			this.fileName = filename;
			this.lineNo = lineNo;
		} else
		{
			// request.getParameter not assigned to a variable?
			throw new OrizonParseException("weird entry point format found in source code (" +source + ")");
		}
	}
	
	/**
	 * Creates a new attack pattern entry point  
	 * @param reqParName
	 * @param storeVarName
	 */
	
	public CodeEntryPoint(String reqParName, String storeVarName) {
		this.requestParamName = reqParName;
		this.storeVariableName = storeVarName;
	}
	
	public String getStoreVariableName() {
		return storeVariableName;
	}
	public String getRequestParamName() {
		return requestParamName;
	}
	
	public void setStoreVariableType(String o) {
		this.storeVariableType = o;
	}
	public String getStoreVariableType() {
		return storeVariableType;
	}
	public final String toString() {
		return "[ " + storeVariableName + " first found in " +fileName +"@"+lineNo+"]";
	}
}
