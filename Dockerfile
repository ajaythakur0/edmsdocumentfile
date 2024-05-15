FROM openjdk:17
EXPOSE 8080
ADD target/enterprisedocumentmanagementsoftware-3.2.5.jar enterprisedocumentmanagementsoftware-3.2.5.jar
ENTRYPOINT ["java","-jar","/enterprisedocumentmanagementsoftware-3.2.5.jar"]
