FROM frolvlad/alpine-java:jdk8-slim
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/America/Santiago /etc/localtime
RUN date
RUN mkdir -p /api-sidep-asistencia/lib/
RUN mkdir -p /api-sidep-asistencia/config/
RUN mkdir -p /api-sidep-asistencia/logs/
ADD bootstrap.yml /api-sidep-asistencia/config
ADD logback.xml /api-sidep-asistencia/config
ADD application-api-sidep-asistencia-messages.properties /api-sidep-asistencia/config
WORKDIR /api-sidep-asistencia
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","lib/app.jar"]
EXPOSE 9310
HEALTHCHECK --interval=2s --retries=15 --timeout=2s --start-period=8s CMD curl --connect-timeout 1 -f http://127.0.0.1:9310/api-sidep-asistencia
ADD api-sidep-asistencia.jar /api-sidep-asistencia/lib/app.jar
