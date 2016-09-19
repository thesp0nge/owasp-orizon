package org.owasp.orizon.kb;

public class KnowledgeBase {
  private final String url =  "https://raw.githubusercontent.com/thesp0nge/owasp-orizon/master/owasp-orizon-kb.json";
  private String fileName;

  public KnowledgeBase(String fileName) {
    this.fileName = fileName;
  }

}
