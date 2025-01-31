FROM frolvlad/alpine-java:jdk8-slim
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/America/Santiago /etc/localtime
RUN date
RUN mkdir -p /api-sidep-sostenedor/lib/
RUN mkdir -p /api-sidep-sostenedor/config/
RUN mkdir -p /api-sidep-sostenedor/logs/
ADD api-sidep-sostenedor.jar /api-sidep-sostenedor/lib/app.jar
ADD bootstrap.yml /api-sidep-sostenedor/config
ADD logback.xml /api-sidep-sostenedor/config
ADD application-api-sidep-sostenedor-messages.properties /api-sidep-sostenedor/config
WORKDIR /api-sidep-sostenedor
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","lib/app.jar"]
EXPOSE 9301
HEALTHCHECK --interval=2s --retries=15 --timeout=2s --start-period=8s
CMD curl --connect-timeout 1 -f http://127.0.0.1:9221/api-sidep-sostenedor