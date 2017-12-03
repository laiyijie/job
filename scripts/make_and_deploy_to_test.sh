#!/bin/sh
set -x
SCRIPT_PATH=$(dirname $0)
cd $SCRIPT_PATH/..
mvn clean install
sudo mkdir -p /opt/job-admin
sudo mkdir -p /opt/job-executor
sudo cp job-admin/target/job-admin-1.0-SNAPSHOT.jar /opt/job-admin
sudo cp job-executor/target/job-executor-1.0-SNAPSHOT.jar /opt/job-executor
sudo ln -sf /opt/job-admin/job-admin-1.0-SNAPSHOT.jar /etc/init.d/job-admin
sudo ln -sf /opt/job-executor/job-executor-1.0-SNAPSHOT.jar /etc/init.d/job-executor
sudo /etc/init.d/job-admin restart
sudo /etc/init.d/job-executor restart
