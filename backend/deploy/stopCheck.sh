#!/bin/bash

ABSPATH=$(readlink -f -- "$0")
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> Stop Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "

for RETRY_COUNT in {1..20}
do
  IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

  if [ -z ${IDLE_PID} ]
  then
      echo "> ${IDLE_PORT} port의 애플리케이션 종료 성공!!"
      break
  else
      echo "> 아직 ${IDLE_PORT} port의 애플리케이션이 실행중 입니다."
      sleep 2
  fi

  if [ ${RETRY_COUNT} -eq 20 ]
  then
    echo "> Stop check 실패. "
    echo "> 새로운 애플리케이션을 실행하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Stop check 실패. 재시도..."
done