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
 * @author thesp0nge 
 * @since 
 */
package org.owasp.orizon.test.mirage;

import java.io.File;
import java.io.IOException;

import org.owasp.orizon.mirage.Identify;

public class IdentificationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: java IdentificationTest directory");
			System.exit(-1);
		}
		Identify i;
		try {
			i = new Identify(new File(args[0]).getCanonicalPath());
			System.out.println(i.tell() + " - (" + i.getErrNo()+ ")");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ;
	}

}
