FROM openjdk:17

COPY target/ServletJDBC-1.0.war /servlet-jdbc.war

CMD [ "java", "-war",  "/servlet-jdbc.war" ]