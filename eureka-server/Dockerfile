FROM amazoncorretto:17

# 빌드 아티팩트 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} eureka-server.jar

# 엔트리포인트 설정
ENTRYPOINT ["java","-jar","/eureka-server.jar"]