package org.owasp.orizon;

import org.owasp.orizon.utils.Orizon;

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.*;
/**
 * Hello world!
 *
 * java -Dlog4j.configurationFile=./log4j2.xml -jar target/owasp-orizon-1.0-SNAPSHOT.jar
 */
public class App
{

  private static final Logger logger = LogManager.getLogger(App.class);

  public static void main( String[] args )
  {
    Orizon o=new Orizon();
    logger.info("Hello this is Owasp Orizon v " + o.getVersion());
    JarFile jarFile = null;
    try
    {
      jarFile = new JarFile(args[0]);
      JarFinder.findReferences(args[0], jarFile);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
    }
    finally
    {
      if (jarFile != null)
      {
        try
        {
          jarFile.close();
        }
        catch (IOException e)
        {
          logger.error(e.getMessage());
        }
      }
    }
  }

}
