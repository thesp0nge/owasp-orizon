package org.owasp.orizon.cli;

import org.apache.commons.cli.*;

public class Opts {
  public final static Options create() {
    Options options = new Options();
    options.addOption("v", "version", false, "Display version information");
    options.addOption("h", "help", false, "Display help messages");
    options.addOption("D", "debug", false, "Display debug information");


    return options;
  }

}
