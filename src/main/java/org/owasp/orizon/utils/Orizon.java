package org.owasp.orizon.utils;

import java.util.Properties;
import java.io.InputStream;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.util.jar.Manifest;
import java.util.jar.JarInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Orizon {
  private static final Logger logger = LogManager.getLogger(Orizon.class.getName());

  public static HashMap<String, String> getVersion(String jarName) {
    HashMap<String, String> ret = new HashMap<String, String>();

    try {

      JarInputStream jarStream = new JarInputStream(new FileInputStream(new File(jarName)));
      Manifest mf = jarStream.getManifest();

      ret.put("version", mf.getMainAttributes().getValue("Implementation-Version"));
    } catch (java.io.FileNotFoundException e) {
      logger.error(e.getMessage());
      ret.put("version", "0.0.0");
    } catch (java.io.IOException e) {
      logger.error(e.getMessage());
      ret.put("version", "0.0.0");
    }
    return ret;
  }

  public static HashMap<String, String> getVersion() {
    HashMap<String, String> ret = new HashMap<String, String>();

    Package aPackage = Orizon.class.getPackage();

    if (aPackage != null) {
      ret.put("build", aPackage.getImplementationVersion());
      ret.put("version", aPackage.getSpecificationVersion());
    } else {
      ret.put("build", "none");
      ret.put("version", "0.0.0");
    }

    return ret;
  }

}
