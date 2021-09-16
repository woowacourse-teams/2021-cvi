#!/bin/bash

if [ $# -lt 1 ] ;  then
  echo '배포 환경을 작성해주세요 (ex) sudo sh start.sh prod)'
  exit 1
fi


ABSPATH=$(readlink -f -- "$0")
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

echo "> Build 파일 경로 복사"
JAR_LOCATION=$(find /home/ubuntu/deploy/* -name "*.jar" | grep SNAPSHOT.jar)

echo "> 새 애플리케이션 배포"
echo "> JAR Location: $JAR_LOCATION" 해당 jar파일 실행

echo "> $JAR_LOCATION 에 실행권한 추가"
chmod +x $JAR_LOCATION

echo "> $JAR_LOCATION 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_LOCATION 를 profile=$IDLE_PROFILE 로 실행합니다."

case $1 in
  "dev")
    nohup java -jar \
        -Duser.timezone=Asia/Seoul \
        -Dspring.config.location=classpath:/application.yml,/home/ubuntu/deploy/application-db.yml,/home/ubuntu/deploy/application-jwt.yml,/home/ubuntu/deploy/application-auth.yml,/home/ubuntu/deploy/application-publicdata-secret.yml,/home/ubuntu/deploy/application-aws-s3.yml \
        -Dspring.profiles.active=$IDLE_PROFILE,"$1" \
        $JAR_LOCATION > ~/nohup.out 2>&1 &
    ;;
  "prod")
    nohup java -jar \
        -Duser.timezone=Asia/Seoul \
        -Dspring.config.location=classpath:/application.yml,/home/ubuntu/deploy/application-db.yml,/home/ubuntu/deploy/application-jwt.yml,/home/ubuntu/deploy/application-auth.yml,/home/ubuntu/deploy/application-publicdata-secret.yml,/home/ubuntu/deploy/application-aws-s3.yml \
        -Dspring.profiles.active=$IDLE_PROFILE,"$1" \
        $JAR_LOCATION > ~/nohup.out 2>&1 &
    ;;
  *)
    echo "올바르지 않은 배포 환경입니다. 입력된 배포 환경 : $1"
    exit 1
esac
