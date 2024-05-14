FROM openjdk:17
EXPOSE 8080
ADD target/edmsdocumentfile-3.2.5.jar edmsdocumentfile-3.2.5.jar
ENTRYPOINT ["java","-jar","/edmsdocumentfile-3.2.5.jar"]