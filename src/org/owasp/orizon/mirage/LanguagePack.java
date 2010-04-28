package org.owasp.orizon.mirage;

import org.owasp.orizon.core.About;

public class LanguagePack {
	private int lp_MAJOR = About.ORIZON_MAJOR;
	private int lp_MINOR = About.ORIZON_MINOR;
	private String lp_RELEASE = About.ORIZON_RELEASE;

	private String lp_BUILD;
	private String lp_NAME;
	public LanguagePack(String name, String build) {
		lp_NAME = name;
		lp_BUILD = build;
	}
	
	public final String shortVersion() {
		return new String("v"+lp_MAJOR+"."+lp_MINOR);
	}
	public final String version() {
		if ("".equals(lp_RELEASE)) 
			return new String("v"+lp_MAJOR+"."+lp_MINOR);
		return new String("v"+lp_MAJOR+"."+lp_MINOR+"."+lp_RELEASE);
	}
	public final String build() {
		return new String("[build: " +lp_BUILD + "]");
	}
	public final String about() {
		return new String(lp_NAME + " language pack " + version() + " [build: " +lp_BUILD + "]");
	}
	
}
