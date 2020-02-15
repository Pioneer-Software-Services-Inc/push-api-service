#!/bin/bash
############################################################################
#
#  Script Name: start_Push_TriggeredDelivery_Job.sh
#  Description: This script is used to start the Push triggered delivery
#  Author: cgordon@acme.com
#  Usage: ./start_Push_TriggeredDelivery_Job.sh
#
############################################################################
echo "setting up environment variables"
. ./commons.sh

# set environment variables
echo service: $PUSH_SERVICE_HOME
JAVA_HOME=/usr/bin
PATH=$JAVA_HOME:$PATH:
CLASSPATH=$CLASSPATH:$PUSH_SERVICE_HOME/resources/log4j.xml
CLASSPATH=$CLASSPATH:$PUSH_SERVICE_HOME/dependency-jars/*
CLASSPATH=$CLASSPATH:$PUSH_SERVICE_HOME/app/PushAPI-0.0.1-SNAPSHOT.jar

export JAVA_HOME PUSH_SERVICE_HOME PATH CLASSPATH

# set procvess label for easy in=dentify of process to kill -9 [id]
PROCESS_LABEL=PUSH_TRIGGERED
echo "setting up environment variables complete"

# fetch properties file information
echo "PROPS $PROPERTY_FILE"

# fetch pending directory
get_property search_dir "pending.dir"
echo "search dir: ${search_dir}"
process_files=`ls $search_dir`
echo "list of files: ${process_files}"

# fetch email notification list
get_property email_list "notify.list"
echo "email notify list: ${email_list}"

# fetch email notification enabled flag
get_property email_enabled "service.email.enabled"
echo "email notify flag: ${email_enabled}"

$JAVA_HOME/java -version

# check to see the process is already running
isRunning "$PROCESS_LABEL"
flag=$?

if [ $flag -gt 0 -a $email_enabled='true' ]
then
   echo -e "Push triggered delivery process attempted a duplicate execution at `date +%m/%d/%Y::%H:%M` \n\t Exiting the process as its already running" | mail -s "Push job completed with error(s)" $email_list
   exit
fi

PROP_DIR=`dirname $PROPERTY_FILE`
echo prop_file dir: $PROP_DIR
if [ $PROP_DIR = '.' ];then
   PROP_DIR=`pwd`
fi
cd $PROP_DIR

# call java process
echo " starting process"
$JAVA_HOME/java -Xmx512M -cp $CLASSPATH com.acme.services.push.Process "${PROPERTY_FILE}"

# Read log instance for populate notification email
get_property log_folder "log.dir"
STATUS_DETAILS="$log_folder/instance.log"

# send process complete notification email
sstatus="`cat $STATUS_DETAILS | grep -c 'success:false'`"
echo "FEED STATUS=$sstatus"

if [ $sstatus -gt 0 ] && [ "$email_enabled" = "true" ]
then
    echo -e "Push triggered delivery process ended at `date +%Y/%m/%d::%H:%M`.\n\n Files: \n$process_files \n\n Status:\n\n `cat $STATUS_DETAILS` " | mail -s "Push process notification" $email_list
fi

echo "script execution completed"
exit 0
