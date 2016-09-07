FROM fabric8/java-alpine-openjdk8-jdk

ENV PROFILES dev
ENV INSTANCE_HOST=192.168.99.100
ENV INSTANCE_PORT=8080
ENV EUREKA_URI='http://192.168.99.100:8761/eureka'
ENV JAVA_OPTS '-Xmx128m -Xms128m'

VOLUME /tmp

ADD sample-scala-camel.jar /app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT exec java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILES} -DEUREKA_URI=${EUREKA_URI} -DINSTANCE_HOSTNAME=${INSTANCE_HOST} -DINSTANCE_PORT=${INSTANCE_PORT} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

EXPOSE 8080