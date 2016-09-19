package org.owasp.orizon.cli;

import org.apache.cli.*;

public class Cli {
  public final static create() {
    Options options = new Options();
    options.addOption("v", "version", false, "Display version information");
    options.addOption("h", "help", false, "Display help messages");
    options.addOption("D", "debug", false, "Display debug information");


    return options;
  }

}
