mvn clean package  && docker build -t payara5jsr325 . && docker run -p 8080:8080 payara5jsr325
