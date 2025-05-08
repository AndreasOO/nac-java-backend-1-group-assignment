## Setup develop environment
Download Docker Desktop and follow installation and setup instructions. 
Start the Docker Desktop app. 

Make sure Docker is installed and works by running ```docker version```. You should see information on both client and server.

Download maven.

Make sure maven is installed by running ```mvn --version```

Navigate to the root folder in the project (same folder as pom.xml) and run ```mvn clean install```

To start the application run ```docker compose up --build``` from the root folder 
(note: sometimes the database container takes up to 45 seconds to start).

Navigate the browser to localhost:8080. 

To stop the application run ```docker compose down -v```. This will stop and remove containers and anonymous volumes

If you make changes in the src folder, you have to run  ```mvn clean install``` again before starting compose again. 