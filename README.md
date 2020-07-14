# HackerApi

This is a Spring boot application which is responsible for reading HackerN API. It fetched the top 10 stories every 10 minutes and the relevant comments and stores them into MongoDB.
This also uses kafka as a messaging queue. It has been used so that the complexity and time efficiency can be improved. 
# Prerequisites

This application requires MongoDB and kafka to be in place before starting the application.

To run mongodb server first download : https://docs.mongodb.com/manual/administration/install-community/

Then Start server: `./mongod`

To run Kafka : https://docs.confluent.io/current/quickstart/ce-docker-quickstart.html

# Getting Started

To Start the application locate HackerapiApplication.java. Right click and run as a java  application. 

## Docker

To run in docker, go to the root of the project. first make the image 

`docker build -t hackerapi:latest .`

Then once the image is created in, run the container using

`docker run -p 9000:9000 --network=host hackerapi`
