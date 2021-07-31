#!/bin/bash

if [ $# -lt 1 ] ;  then
  echo '배포 환경을 작성해주세요 (ex) sudo sh deploy.sh prod)'
  exit 1
fi

sudo bash /home/ubuntu/deploy/script/stop.sh
sudo bash /home/ubuntu/deploy/script/stopCheck.sh
sudo bash /home/ubuntu/deploy/script/start.sh "$1"
sudo bash /home/ubuntu/deploy/script/health.sh