#!/bin/bash
################################################################################
#
#  Script Name: startPushTest.sh
#  Description: This script is used to populate test data and execute the push
#  Author: cgordon@acme.com
#  Usage: ./startPushTest.sh
#
################################################################################
echo "setting up test data"

# import common function library
. ./commons.sh

# starting daemon background  process
LOCAL_DIR="$PUSH_SERVICE_HOME/indata"

TEMPLATE_INPUT_OR_DATA="$LOCAL_DIR/template/inputdata_order_template.xml"
TEMPLATE_INPUT_TR_DATA="$LOCAL_DIR/template/inputdata_track_template.xml"

for i in `seq 1 3`;
do
  cp $TEMPLATE_INPUT_TR_DATA "/$LOCAL_DIR/pending/inputdata_track_${i}.xml"
done

for i in `seq 1 2`;
do
  cp $TEMPLATE_INPUT_OR_DATA "/$LOCAL_DIR/pending/inputdata_order_${i}.xml"
done

echo "setting up test data complete"
