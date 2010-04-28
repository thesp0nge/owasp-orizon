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

public class Stat {
	private int lines;
	private int lineOfCode;
	private int lineOfComment;
	private int cyclomaticComplexityIndex;

	public Stat() {
		lines = 0;
		lineOfCode = 0;
		lineOfComment = 0;
		cyclomaticComplexityIndex = 0;
	}

	public int addLines(int lines) {
		this.lines += lines;
		return this.lines;
	}
	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int addLineOfCode(int lines) {
		lineOfCode += lines;
		return lineOfCode;
	}
	public int getLineOfCode() {
		return lineOfCode;
	}

	public void setLineOfCode(int lineOfCode) {
		this.lineOfCode = lineOfCode;
	}

	public int addLineOfComment(int lines) {
		lineOfComment += lines;
		return lineOfComment;
	}
	public int getLineOfComment() {
		return lineOfComment;
	}

	public void setLineOfComment(int lineOfComment) {
		this.lineOfComment = lineOfComment;
	}

	public int getCyclomaticComplexityIndex() {
		return cyclomaticComplexityIndex;
	}

	public void setCyclomaticComplexityIndex(int cyclomaticComplexityIndex) {
		this.cyclomaticComplexityIndex = cyclomaticComplexityIndex;
	}
	public int addCyclomaticComplexityIndex(int cyclomaticComplexityIndex) {
		this.cyclomaticComplexityIndex += cyclomaticComplexityIndex;
		return this.cyclomaticComplexityIndex;
	}
	public String toString() {
		return getLines() + " " + getLineOfCode() + " " + getLineOfComment() + " " + getCyclomaticComplexityIndex();
	}
	
	protected String hr() {
		String ret="";
		for (int i=0;i<79;i++)
			ret +="-";
		ret +="\n";
		return ret;
	}
}
