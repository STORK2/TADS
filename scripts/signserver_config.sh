#!/bin/bash

DIR=${PWD}

export APPSRV_HOME=$JBOSS_HOME
export ANT_OPTS="-Xmx512m -XX:MaxPermSize=128m"
export SIGNSERVER_HOME="$DIR/signserver-3.5.0"
export SIGNSERVER_NODEID="node1"

OUT=`$SIGNSERVER_HOME/bin/signserver setproperties $SIGNSERVER_HOME/doc/sample-configs/qs_pdfsigner_configuration.properties`
echo "$OUT"
WORKERID=`echo "$OUT" | tail -n 1 | grep -o '[0-9]\+'`
echo "--------------------------------------"
$SIGNSERVER_HOME/bin/signserver reload $WORKERID

