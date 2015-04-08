#!/bin/bash

DIR=${PWD}

cd $DIR/java/STORKTADSUtils

echo Compile STORKTADSUtils
mvn clean install

echo Copy jar to $DIR/java
cp target/STORKTADSUtils-1.4.2.jar .. 