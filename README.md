# A reference implementation of SpringBoot with spring-cloud-aws and localstack
## To run:
* Run "docker-compose up -d" in project root to start localstack
* Start application
* post a message to /message endpoint: curl -X POST http://localhost:8080/messages --data "send another message"
* There should be messages sent to the queues and picked up by listener.
* Checkout test cases for more details.



