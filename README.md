# The Owasp Orizon project

[Owasp Orizon](http://www.owasp.org/index.php/Category:OWASP_Orizon_Project) is
a source code static analyzer tool designed to spot security issues in Java
applications.

## The history

It was a dark and stormy night in Milan, Italy. It was 2006 and I felt the need
of something helping me in reviewing other people java source code. So Owasp
Orizon born and grew up as security tool trying to parse Java source code,
building an Abstract Syntax Tree and spot for unsafe calls in the code.

In the very beginning Owasp Orizon was a sort of enhanced grep tool. In 2008, I
started supporting PHP programming language but the initial boost disappeared.
After being in love with other programming languages and technolgies, eight
years later, in 2016 I kickstarted the project again from scratch.

## The typo

## The mission

Source code contains bugs and vulnerabilities. Owasp Orizon will help either
application security specialists or developersto spot vulnerabilities in their
code and to create security patches.

Owasp Orizon mission is to provide people an opensource tool, helping them in
reviewing:

* single Java classes
* java standalone tools packed in JAR files
* web applications packed in EAR / WAR files
* Android APK applications

## An overall introduction

When you launch Owasp Orizon it will start unpkacing the target file if not a
standalone .class file.

First security analysis stage is about vulnerabilities from third party
libraries. Owasp Orizon will try to understand target package dependencies and
than look for known security issues.

As knowledge base for third party library vulnerabilities, Owasp Orizon will
support:

* [vFeed.io database](https://vfeed.io). Please note that we don't redistribute
  the database. You must go on [vFeed website](https://vfeed.io) and purchase
  the license that best fits your tool usage
* CVE archive from [NVD](https://nvd.nist.gov)

After this stage, Owasp Orizon will perform a walkthrough on Owasp TOP 10
security risks, using Apache BCEL library to disassemble java bytecode.

## Usage

_More a reminder than a real doc here_

java -Dlog4j.configurationFile=./log4j2.xml -jar target/owasp-orizon-1.0-SNAPSHOT.jar

## The overall design

_To be written_
