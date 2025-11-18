FROM amazoncorretto:17

# 빌드 아티팩트 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} item-service.jar

# 엔트리포인트 설정
ENTRYPOINT ["java","-jar","/item-service.jar"]