#!/bin/sh

# *--------------------------------------------------------------*

RELATIVE=`dirname "$0"`/..
GENERATOR_HOME="`cd \"$RELATIVE\" 2>/dev/null && pwd || echo \"$RELATIVE\"`"

# *--------------------------------------------------------------*

if [ "x$JAVA_HOME" = "x" ];then
        JAVA=java
else
    	JAVA=$JAVA_HOME/bin/java
fi

# *--------------------------------------------------------------*

CPATH="$GENERATOR_HOME/lib/*:"

# *--------------------------------------------------------------*

COPTS=-Dgenerator.home=$GENERATOR_HOME
GENERATOR_CLASS=gdg.toulouse.command.Main

exec $JAVA $COPTS -cp $CPATH $GENERATOR_CLASS $@
