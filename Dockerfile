FROM maven:3.8-openjdk-11 AS builder
WORKDIR /opt/inside-builder/
COPY . .
RUN mvn package -Dmaven.test.skip

FROM openjdk:11-jre-slim
WORKDIR /opt/inside/
COPY --from=builder /opt/inside-builder/target/ /opt/inside/
ENTRYPOINT ["java","-jar","insidetask-0.0.1-SNAPSHOT.jar"]
