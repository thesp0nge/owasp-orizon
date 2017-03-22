package org.owasp.orizon;

import org.owasp.orizon.utils.Orizon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.List;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.*;

import org.apache.commons.cli.*;

/**
 * Hello world!
 */
public class App
{

  private static final Logger logger = LogManager.getLogger(App.class.getName());

  public static void main( String[] args )
  {
    CommandLineParser parser = new DefaultParser();
    try {
      // parse the command line arguments
      CommandLine line = parser.parse( org.owasp.orizon.cli.Opts.create(), args );

      // validate that block-size has been set
      if( line.hasOption( "block-size" ) ) {
        // print the value of block-size
        System.out.println( line.getOptionValue( "block-size" ) );
      }
    }
    catch( ParseException exp ) {
      System.out.println( "Unexpected exception:" + exp.getMessage() );
    }

    HashMap<String, String> v = Orizon.getVersion();
    logger.info("Hello this is Owasp Orizon v" + v.get("version") + " (build: " +v.get("build")+")");
    JarFile jarFile = null;
    try
    {
      jarFile = new JarFile(args[0]);
      Path tempDir = Files.createTempDirectory("OwaspOrizon_");
      tempDir.toFile().deleteOnExit();
      logger.debug("Temp directory created: " + tempDir.getFileName());

      // JarFinder.findReferences(args[0], jarFile);
      List<String> jars = JarFinder.collectJars(jarFile, true, tempDir);
      for (String jF: jars) {
        logger.info(jF + " version " + Orizon.getVersion(tempDir + java.io.File.separator + jF).get("version"));
      }
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
