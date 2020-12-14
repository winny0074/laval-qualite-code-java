
# Get Started
Project generated with [Maven](https://maven.apache.org/) version 3.6.1.

## Compile
 Run ```mvn clean install``` to compile the project in a clean slate. 
 
## Execute
 Run ```mvn exec:java -pl application``` to execute the program. 
 
## Navigate
Navigate to http://localhost:8181/heartbeat?token=unique_token for working example

## Running unit tests
Run mvn test to execute unit tests
We are using the Test-Driven-Development philosophy to ensure efficient, functional and clean code. 
In order to do that, we use Junit 5.
 
## Linter
 Execute the linter by running ```mvn git-code-format:validate-code-format```.
 
## License
Copyright (c) 2020 GLO4002 - Qualité et métriques du logiciel

Licensed under the MIT license.

## Imposed technologies
- Maven
- Java >= 11
- JUnit >= 5
- Mockito >= 3

## Project Description
The project covers the interactions between a restaurant and their clients by
allowing them to make a reservation and other interactions using this API.

## Menu modification
The menu can be found in the ca/ulaval/glo4002/reservation/domain/Restriction file which enumerates each ingredient present for a specific restriction. To modify a menu which is already present, simply change the names and quantities of the ingredients for each meal. To add a new restriction, use the template from another restriction and modify the values in each fields.
 
## Project status
Delivery 0 is complete.

Delivery 1 is due Friday, Octobre 09 2020 23:59:59.

## Project submission 0
POST /reservations to make a reservation

GET /reservations/# to look at reservation with id #

## Project submission 1
/reports/ingredients GET request to generate a report
of necessary ingredients during a period of time

Queries: 
- startDate - String containing the first date of the report
- endDate - String containing the last date of the report
- type - String containing the type of report (unit/total)

## Project submission 2
/configuration POST request set up reservation period and Hoppening dates

/reports/chefs GET request to generate a report of necessary ingredients during a period of time        
Queries: 
- startDate - String containing the first date of the report
- endDate - String containing the last date of the report



## Membre de l'équipe

- [Alexandre Pineault](https://github.com/WinterSolstices)
- [Maxime Corriveau-Faucher](https://github.com/Ender0Storm)
- [Debonnaire Ngatchui](https://github.com/Ngatchui)
- [Naye Gueye](https://github.com/nayegueye)
