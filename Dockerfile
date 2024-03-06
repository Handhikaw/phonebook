ARG BASE_IMAGE_SPRING
FROM $BASE_IMAGE_SPRING
ENV java_opts=""
ENV java_args=""
WORKDIR /app
COPY target/phonebook*.jar /app/app.jar
ENTRYPOINT exec java $java_opts -jar app.jar $java_args
EXPOSE 8080
