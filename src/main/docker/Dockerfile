FROM fabric8/java-alpine-openjdk8-jdk

ENV PROFILES dev
ENV JAVA_OPTS '-Xmx128m -Xms128m'

VOLUME /tmp

ADD sample-scala-camel.jar /app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT exec java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILES} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 8080