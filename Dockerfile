# inspired by https://spring.io/guides/gs/spring-boot-docker/
FROM jensfischerhh/docker-java:8u45-jre

VOLUME /tmp

ADD build/libs/demo-app-DEV-SNAPSHOT.jar /app.jar
RUN /bin/bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=UTC","-jar","/app.jar"]
