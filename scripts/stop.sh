#!/usr/bin/env bash

ABSPATH=$(readlink -f $0) # stop.sh 위치
ABSDIR=$(dirname $ABSPATH) # stop.sh 위치의 dirname 가져온다. -> import profile.sh 목적을 위해
source ${ABSDIR}/profile.sh #import profile.sh

IDLE_PORT=$(find_idle_port) #import profile.sh을 통해 사용 할 수 있게 됨

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT}) # IDLE_PORT에 사용되고 있는 PID가 있나  확인

# IDLE_PORT에 PID 종료 확인
if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi