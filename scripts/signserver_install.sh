#!/bin/bash

#Gets current working path
DIR=${PWD}

#Signserver environment variables
export APPSRV_HOME=$JBOSS_HOME
export ANT_OPTS="-Xmx512m -XX:MaxPermSize=128m"
export SIGNSERVER_HOME="$DIR/signserver-3.5.0"
export SIGNSERVER_NODEID="node1"

#Configures JBOSS
echo 'Configure JBOSS for SignServer'
sh ${JBOSS_HOME}/bin/jboss-cli.sh --connect --file=jboss_config.cli

#Download SignServer, extract, configure, build and deploy
echo 'Download SignServer 3.5.0'
wget 'http://downloads.sourceforge.net/project/signserver/signserver/3.5/signserver-3.5.0.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fsignserver%2Ffiles%2Fsignserver%2F3.5%2F&ts=1399656975&use_mirror=garr' -O signserver-3.5.0.zip

echo 'Extract SignServer'
unzip signserver-3.5.0.zip

echo 'Replace configuration files'
cp resources/signserver/signserver_build.properties signserver-3.5.0/conf
mkdir signserver-3.5.0/database
mkdir signserver-3.5.0/keys
cp resources/signserver/signserver.p12 signserver-3.5.0/keys

echo 'Replace SignServer keystore location'
python scripts/replace.py $DIR

echo "Build and Deploy SignServer"
"${SIGNSERVER_HOME}/bin/ant" clean build deploy

echo "Sleep for 5 seconds"
sleep 5

${SIGNSERVER_HOME}/bin/signserver setproperties ${SIGNSERVER_HOME}/doc/sample-configs/qs_pdfsigner_configuration.properties
echo "Sleep for 5 seconds"
sleep 5
${SIGNSERVER_HOME}/bin/signserver reload PDFSigner

