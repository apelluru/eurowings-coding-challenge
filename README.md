# eurowings-coding-challenge
Newsletter Subscription Service


# Frameworks 
- Spring Boot
- Spring Data
- H2 DB
- JPA
- Lombok
- Docker, docker-compose
- Junit


# To start newsletter service

Step 1# Create docker image

	docker-compose -f docker_compose.yml build

Step 2# Run docker image
	
	docker-compose -f docker_compose.yml up -d

Step 3# Run postman api calls for testing 
    
   you can find the file name: "Eurowings - Code challenge.postman_collection.json"

	
Stop docker image
	
	docker-compose -f docker_compose.yml down

# Tests
To run unit tests
    
    mvn test