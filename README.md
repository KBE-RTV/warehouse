# Warehouse
This project provides the functionality to access and store inventory information to a warehouse.
It was written for the Course "Component based development" at HTW Berlin SoSe22 as part of the final project.
For the other Services see https://github.com/KBE-RTV.

## Functionality 
This Service runs as a Docker container, the corresponding docker compose file is found in the projects root folder. If started the Docker Container provides its functionality on Port 8081.
For more information on the provided endpoints see the Swagger documentation.  

On startup the warehouse loads default data into its database via an CSV import.  
Use ```docker-compose up``` to start the container, ```docker-compose up --build``` to rebuild before start.
## CSV Importer
### Configuration
Auto import at startup via importCSVAtStartUp method in warehouseService. The path is hardcoded.
### Behavior
If an CSV entry is already in the database, the amount value of the existing entry will be adjusted.  
If an CSV entry is not in the database a new entry will be made.
### CSV Celestial Body import
#### Path to CSV
target/classes/celestialBodies.csv or src/main/resources/celestialBodies.csv
#### CSV-Celestial Body-Header
id, name, amount, price, type, orbital, radius, volume, mass, gravity, rotationVelocity, orbitalVelocity, surfaceTemperature  
#### Celestial Body Datatype
id: UUID
name, type: String  
orbital, amount: int  
other: float
#### Celestial Body Units
radius: km  
volume: km^3  
mass: kg  
gravity: m/s^2  
rotationVelocity: km/s  
orbitalVelocity: km/s  
surfaceTemperature: K

### CSV Planetary Systems import
#### File Location
target/classes/planetarySystems.csv or src/main/resources/planetarySystems.csv
#### CSV-CSV Planetary Systems-Header
id, name, owner,celestialBodies
#### CSV Planetary Systems Datatypes
id: UUID
name, owner: String   
celestialBodies: ArrayList of CelestialBodies
##### Custom ArrayList Syntax
Entries are seperated by "|".  
Values are seperated by "&".  
e.g: 51cafbb6-e909-4851-8f14-d1994fdcc500&Sun&1&1&sun&1&1&1&1&1&1&1|51cafbb6-e909-4851-8f14-d1994fdcc500&Sun&1&1&sun&1&1&1&1&1&1&1

## warehouse Database
The application will automatically connect to an MongoDB with the connection details given by the application.property file.

## OpenApi / Swagger
* view OpenApi documentation at http://localhost:8081/open-api-docs
* view Swagger UI at http://localhost:8081/swagger-ui