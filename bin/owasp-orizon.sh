#!/usr/bin/env bash

JAVA=`which java`
LOG4J2_CONF="target/log4j2.properties"
JVM_ARGS="-Dlog4j.configurationFile=$LOG4J2_CONF"
JAR="target/owasp-orizon-1.99.jar"

$JAVA $JVM_ARGS -jar $JAR ~/Documents/SkyMobile.war
