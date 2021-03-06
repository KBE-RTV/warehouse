## CSV Component import
### File Location
target/classes/ or src/main/resources
### Configuration
Auto import at startup via importCSVAtStartUp method in warehouseService. The path is hardcoded.
### Behavior
If an CSV entry is already in the database, the amount value of the existing entry will be adjusted.  
If an CSV entry is not in the database a new entry will be made.
### CSV-Component-Header
id, name, amount, price, type, orbital, radius, volume, mass, gravity, rotationVelocity, orbitalVelocity, surfaceTemperature
### Component Datatypes
id: String  
name: String  
type: choose one of {gasGiant, iceGiant, meteor, asteroid, moon, dwarfPlanet, planet, sun}  
orbital, amount: int  
other: float
### Component Units
radius: km  
volume: km^3  
mass: kg  
gravity: m/s^2  
rotationVelocity: km/s  
orbitalVelocity: km/s  
surfaceTemperature: K

## CSV Product import
### File Location
target/classes/ or src/main/resources
### Configuration
Auto import at startup via importCSVAtStartUp method in warehouseService. The path is hardcoded.
### Behavior
If the ID already exists in the DB, the corresponding entry will be overwritten.  
If the ID does not exist in the DB a new Entry will be made.
### CSV-Component-Header
id, name, celestialBodies
### Component Datatypes
id: String  
name: String  
celestialBodies: ArrayList
#### ArrayList Syntax
Entries are seperated by "|".  
Values are seperated by ".".  
e.g: 1+Sun+1+1+sun+0+1+1+1+1+1+1+1|1+Sun+1+1+sun+0+1+1+1+1+1+1+1

## warehouse Database
The application will automatically connect to an MongoDB with the connection details given by the application.property file.  
Components will be stored in the collection "components".