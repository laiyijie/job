#!/bin/sh
set -x
SCRIPT_PATH=$(dirname $0)
cd $SCRIPT_PATH
cd ..
git submodule update --remote 
cd libs/job-swagger-api/spring
mvn clean install
