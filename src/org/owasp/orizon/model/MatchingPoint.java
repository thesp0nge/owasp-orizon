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

/**
 * This class is about a match found between an entry point in a dynamic page
 * and an output point.
 * 
 * TODO: Investigate further if this package is the logically best location for
 * such a class
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class MatchingPoint {
	private 	EntryPoint	in;
	private 	ExitPoint 	out;

	private		String		affectedFile;
	private		int			sinkInLine;
	private		int			sinkOutLine;
	
	public MatchingPoint(EntryPoint in, ExitPoint out) {
		this.in = in;
		this.out = out;
		affectedFile = out.fileName;
		sinkInLine = in.getLineNo();
		sinkOutLine = out.lineNo;
	}
	
	public String toString() {
		return in.getStoreVariableName() + " found in " +affectedFile +"@"+sinkOutLine+" output HTML.\nSince an attacker can control this value, you may want to filter it to avoid some well known vulns.";	
	}
}
