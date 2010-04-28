
			/*
			 * This file is generated automatically by Owasp Orizon ant build.xml file
			 * 
			 */ 
			package org.owasp.orizon.core;
			public class About {
				public static final int ORIZON_MAJOR = 1;
				public static final int ORIZON_MINOR = 39;
				public static final String ORIZON_RELEASE = "0";
				public static final String ORIZON_BUILD = "36";
			    public static final String MIRAGE_BUILD = "36";
				public static final String DUSK_BUILD = "${dusk.build}";
				public static final String TWILIGHT_BUILD = "${twilight.build}";
				public static final String OSH_BUILD = "${osh.build}";
				public static final String LIBRARY_BUILD = "${library.build}";
				public static final String TORNADO_BUILD = "${tornado.build}";
			
				public static final String ORIZON_CODENAME = "mint";
			    public static final String ORIZON_JAR_NAME = "orizon-1.390.jar";
			
				public About() {
				}
				public final static String shortVersion() {
					return new String("v"+ORIZON_MAJOR+"."+ORIZON_MINOR);
				}
				public final static String build() {
					return new String("[build: " + ORIZON_BUILD + "]");
				}
				public final static String version() {
					if ("".equals(ORIZON_RELEASE)) 
						return new String("v"+ORIZON_MAJOR+"."+ORIZON_MINOR);
					return new String("v"+ORIZON_MAJOR+"."+ORIZON_MINOR+"."+ORIZON_RELEASE);
				}
				public final static String about() {
					return new String("Orizon " + version() + " ("+ORIZON_CODENAME+") [build: " +ORIZON_BUILD +"] (C) 2006-2010 thesp0nge@owasp.org ");
				}
			}
		