#!/usr/bin/env bash

echo  "Creating folder for logs"

mkdir -p ../logs

echo  "Folder created"

echo  "Starting executing scripts:"

function send_command {
	CMD=$1
	JSON="{\"attributes\":{\"data\":\"${CMD}\"},\"deviceId\": 1,\"type\": \"custom\",\"textChannel\":false}"
	curl -s -d "$JSON" -H "Accept: application/json" -H "Content-Type: application/json" --user admin:admin -X POST http://localhost:8082/api/commands/send
	echo ""
}

WAIT=3s
send_command 'RAPIDDEC,ON,0,3#'
sleep $WAIT
send_command 'RAPIDACC,ON,0,3#'
sleep $WAIT
send_command 'MOVING,ON,100'
sleep $WAIT
send_command 'RAPIDTURN,ON,0,3'
# sleep $WAIT
# send_command 'RAPIDTURN,ON#'
sleep $WAIT
send_command 'SPEED,ON,10,30'

# send_command 'VERSION'
# send_command 'PARAM'
# send_command 'STATUS'
sleep $WAIT
send_command 'SENALM,ON,0,3'

echo  "Scripts executed"
