#######################################################################
#
#  Script Name: commons.sh
#  Description: This is a library of common useful push scripts
#  Author: cgordon@acme.com
#  Usage: source . ./commons.sh (include)
#
#######################################################################

BASEDIR=$(readlink -f $0 | xargs dirname)
PUSH_SERVICE_HOME="$(dirname "$BASEDIR")"
PROPERTY_FILE="$PUSH_SERVICE_HOME/resources/config.properties"
TRUE=1
FALSE=0

#######################################################################
# This function retrieves the requested property from the configured
# properties file. The return value is stored in the result variable
# as shown below:
# Usage: get_property result "$key"
#######################################################################
get_property(){

    local result=$1
    local key=$2
    local value=`cat ${PROPERTY_FILE} | grep ${key} | cut -d'=' -f2`
    value=${value//[^(a-zA-Z\/,;@.0-9)]/} #removes carriage return (DOS)
    eval $result=\$value
}

#######################################################################
# This function verifies if a given process is altready running.
# Usage: isRunning PROCESS_LABEL
#######################################################################
isRunning(){

   # check to see the process is already running
   label=`ps -ef | grep $1 | grep -v grep | wc -l`

   if [ $label -gt  0 ]
   then
      return $TRUE
   fi
   return $FALSE
}
