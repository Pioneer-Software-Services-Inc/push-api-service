#!/bin/bash
################################################################################
#
#  Script Name: startDaemonJob.sh
#  Description: This script is used to start the daemon thread task
#  Author: cgordon@acme.com
#  Usage: ./startDaemonJob.sh
#
################################################################################

# import common function library
. ./commons.sh

# starting daemon background  process
PROCESS_LABEL=PUSH_DAEMON
LOCAL_DIR="$PUSH_SERVICE_HOME/scripts"
cd $LOCAL_DIR

# verify if daemon task is already running
isRunning "$PROCESS_LABEL"
flag=$?

# set fetch email notification list constants
get_property email_list "notify.list"

# check for duplicate daemon startup
if [ $flag -gt 0 ]
then
    echo "Push daemon process attempted a duplicate start at `date +%m/%d/%Y::%H:%M` Exiting the process as its already running" | mail -s "Push daemon startup error(s)" $email_list
    exit
fi

# starting daemon background  process
cd $LOCAL_DIR

get_property pending_dir "pending.dir"
nohup ./daemonTask.sh $pending_dir $PROCESS_LABEL >/dev/null 2>&1 &

echo "PEND: $pending_dir"

# send successful startup notification
echo "Push daemon process started successfully at `date +%m/%d/%Y::%H:%M`." | mail -s "Push daemon started successfully." $email_list
