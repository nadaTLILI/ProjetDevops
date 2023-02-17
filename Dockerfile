FROM openjdk:11
WORKDIR /app
COPY ./target/tpAchatProject-1.0.jar tpAchatProject-1.0.jar
CMD ["java","-jar","tpAchatProject-1.0.jar"]