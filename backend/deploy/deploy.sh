#!/bin/bash
#!/bin/bash in the top of your scripts then you are telling your system to use bash as a default shell.

REPOSITORY=/home/ubuntu/2021-cvi/ # 경로 설정
PROJECT_NAME=backend
PROCESS_NAME=cvi

cd $REPOSITORY/$PROJECT_NAME/ # 경로로 접근

echo "> git reset --hard origin/HEAD"

 git reset --hard origin/HEAD # 깃허브 초기화

echo "> git pull origin develop"

git pull origin develop # pull 땡겨오기

echo "> 프로젝트 Build 시작"

./gradlew clean build # 빌드

echo "> Build 파일 경로 복사"

JAR_LOCATION=$(find ./* -name "*jar" | grep cvi-0.0.1-SNAPSHOT.jar)

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROCESS_NAME}[-A-z0-9.]*.jar$) # 실행시켜져있>는 jar pid 받기


if [ -z "$CURRENT_PID" ]; then # -z 플래그는 null인것을 체크함, PID가 null인 경우 if절 안으로 들어감
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> 현재 구동중인 애플리케이션 종료(pid : $CURRENT_PID)"
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"
echo "> JAR Location: $JAR_LOCATION" 해당 jar파일 실행

#nohup java -jar 실행
nohup java -jar -Dspring.config.location=/home/ubuntu/deploy/application-prod.yml -Dspring.profiles.active=prod ${JAR_LOCATION} 1> log-prod.out 2>&1  &
