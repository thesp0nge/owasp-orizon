#!/bin/sh
# This is Orizon 1.31 (C) 2006-2010, Paolo Perego <thesp0nge@owasp.org>

JAVA=`which java`
# JAVA_PARAMS="-XX:+PrintGCDetails -Xms32m -Xmx512m"
MYROOT=`dirname $0`
JAVA_PARAMS="-Xms32m -Xmx512m -Dlog4jconfname=$MYROOT/conf/log4j.conf"
RESET=`which reset`

# Uncomment the following line if you want Orizon being very verbose in printing
# messages.
# JAVA_PARAMS=$JAVA_PARAMS" -Dorizon_be_verbose=true"

# Uncomment the following line if you want Orizon to run in debug mode.
# A lot of other stuff and information messages are printed out this way.
# JAVA_PARAMS=$JAVA_PARAMS" -Dorizon_be_debug=true"

# Make sure we're in the root dirctory of the source code
# by trimming off /bin if its present
# Thanks to Matt Tesauro for patching this.
# <mtesauro@gmail.com>

WORKING_DIR=`pwd`
TRIMMED_DIR=${WORKING_DIR%/bin}
cd $TRIMMED_DIR

VERSION="1.31"

if [ ! -r ./lib/log4j-1.2.15.jar ]; then
	echo "Warning... log4j-1.2.15.jar is missing, link your log4j binary library to ./lib/log4j-1.2.15.jar"
	exit 1;
fi

# if [ ! -r ./lib/jline.jar ]; then
#	echo "Warning... jline.jar is missing, link your jline binary library to ./lib/jline.jar"
#	exit 1;
#fi

if [ -r ./lib/orizon-core-$VERSION.jar ]; then
	$JAVA $JAVA_PARAMS -jar ./lib/orizon-core-$VERSION.jar
	$RESET
	exit 0;
fi
