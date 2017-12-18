#!/bin/sh
set -x
SCRIPT_PATH=$(dirname $0)
cd $SCRIPT_PATH/..
mvn clean install
sudo mkdir -p /opt/job-admin
sudo cp job-admin/target/job-admin-1.0-SNAPSHOT.jar /opt/job-admin
sudo ln -sf /opt/job-admin/job-admin-1.0-SNAPSHOT.jar /etc/init.d/job-admin
sudo /etc/init.d/job-admin restart
