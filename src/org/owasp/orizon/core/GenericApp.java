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

import java.util.Vector;

import org.owasp.orizon.model.EntryPoint;

public abstract class GenericApp {
	private String name;
	private String webroot;
	private int kind;
	
	protected boolean modeled;
	protected Vector<SourceFile> bobjs;
	protected Vector<SourceFile> pages;
	protected Vector<ParseFailure>	pageErrors;
	protected Vector<ParseFailure> bobjErrors;
	protected Stats		stat;
	
	/**
	 * Creates a generic application object
	 * @param webroot
	 */
	public GenericApp(String webroot) {
		this.webroot = webroot;
		bobjs = new Vector<SourceFile>();
		pages = new Vector<SourceFile>();
		pageErrors = new Vector<ParseFailure>();
		bobjErrors = new Vector<ParseFailure>();
		stat = new Stats();
		modeled = false;
	}
	
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getWebroot() {
		return webroot;
	}
	public final void setWebroot(String webroot) {
		this.webroot = webroot;
	}
	public final int getKind() {
		return kind;
	}
	public final void setKind(int kind) {
		this.kind = kind;
	}
	// DEBUG METHODS
	/**
	 * Dumps out some information about the generic application just modeled.
	 */
	public abstract void dump();
	
	// SERVICE METHODS 
	/**
	 * Creates a model of the given generic application.
	 * 
	 * @return <code>true</code> if the model can be built, or
	 *         <code>false</code> otherwise.
	 */
	public abstract boolean model();

	/**
	 * Get application detected entry points an attacker can use to inject
	 * attack patterns.
	 * 
	 * @return the list of application entry points
	 */
	public abstract Vector<EntryPoint> getEntryPoints();
	
}
