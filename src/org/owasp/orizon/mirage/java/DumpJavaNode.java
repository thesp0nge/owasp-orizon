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

import org.apache.log4j.Logger;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.mirage.DumpNode;
import org.owasp.orizon.mirage.java.parser.Node;

public class DumpJavaNode extends DumpNode{

	private Node n;
	private static Logger log = Logger.getLogger(DumpJavaNode.class);
	
	public DumpJavaNode(Node n) {
		this.n = n;
	}
	
	@Override
	public void dumpChild() {
		for (int z=0; z<n.getChildCount();z++)
			log.debug(n.getChild(z).getClass().toString());
	}
	
	public static void dumpChild(Node nn) {
		if (Orizon.isInDebugMode()) {
			DumpJavaNode j = new DumpJavaNode(nn);
			j.dumpChild();
		}
	}

}
