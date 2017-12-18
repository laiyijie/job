#!/bin/sh
set -x
SCRIPT_PATH=$(dirname $0)
cd $SCRIPT_PATH/..
mvn clean install
cp job-executor/target/job-executor-1.0-SNAPSHOT.jar win_wrapper/job-executor.jar

