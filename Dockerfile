FROM openjdk:11

LABEL serbuitrago="Sergiostivesbb@ufps.edu.co"

EXPOSE 8090

ARG JAR_FILE=target/enterprise-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} enterprise.jar

ENTRYPOINT [ "java", "-Djava.security-egd=file:/dev/./urandom", "-jar", "/enterprise.jar" ]