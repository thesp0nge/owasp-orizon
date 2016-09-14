package org.owasp.orizon.utils;

import java.util.Properties;
import java.io.InputStream;

public class Orizon {

  public synchronized String getVersion() {
    String version = null;

    // try to load from maven properties first
    try {
      Properties p = new Properties();
      InputStream is = getClass().getResourceAsStream("/META-INF/maven/org.owasp.orizon/owasp-orizon/pom.properties");
      if (is != null) {
        p.load(is);
        version = p.getProperty("version", "");
      }
    } catch (Exception e) {
      // ignore
    }

    // fallback to using Java API
    if (version == null) {
      Package aPackage = getClass().getPackage();
      if (aPackage != null) {
        version = aPackage.getImplementationVersion();
        if (version == null) {
          version = aPackage.getSpecificationVersion();
        }
      }
    }

    if (version == null) {
      // we could not compute the version so use a blank
      version = "";
    }

    return version;
  }

}
