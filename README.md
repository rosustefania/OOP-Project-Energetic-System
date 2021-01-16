# Rosu Adriana-Stefania, 324CD 
# Project: Stage 2
# Object Oriented Programming, 2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

## Packages and classes
 Input package:
     - Input, InputLoader and UpdateData classes help reading (readData method) 
     the json objects; input is stored in an Input object; UpdateData objects 
     help storing the updates details;
           
 Output package: 
    - ConsumerOut, DistributorOut and ProducerOut classes contain only the 
    fields that are written to the output json object;
    - Result class creates an object that contains consumers', distributors' and 
    producers' lists;
    - FileWrite class has a method that writes the Result object to the json 
    file;
    - MonthlyStat class helps writing the statistics;
                 
 Simulation package: 
    - Contract class helps creating contract objects that store information 
    about a contract;
    - Simulation class creates an object that does the initial round and the 
    following rounds;
                    
 Singletonfactoryobserver package:
     - PowerGrid is an interface implemented by Consumer, Distributor and 
     Producer classes, classes that store details about the entities;
     - EntityFactory class implements both Factory and Singleton Patterns and is
     used when reading data from the json objects to create entity objects
     (consumer/ distributor); there is only one EntityFactory instance because 
     of the Singleton Pattern;
     - Observer is an interface used for implementing the observer pattern;
     
 Strategies package:
    - ProducerStrategy is an interface used for implementing the strategy 
    pattern;
    - GreenStrategy, PriceStrategy and QuantityStrategy are classes that 
    implement the given strategies;
    - Context class is used for executing strategies;
    
   
 
### Flow
   - Initial round:
        - every distributor choose his producers;
        - production costs are calculated;
        - contracts' prices are calculated and every consumer receives his 
        salary, choose a contract, and pays its price;
        - distributors pay the monthly costs;
   - Following rounds:
        - distributors' data is updated and contracts' prices are calculated 
        again;
        - adds the new consumers, after that all consumers receive their 
        salary, choose a contract, and pays its price;
        - distributors pay the monthly costs;
        - producers' data is updated and distributors choose their producers 
        again;
        - new production costs are calculated;
        - monthly stats are obtained;
        
### Design patterns
   - Singleton Factory Pattern: used for creating the entities: Consumer, 
   Distributor and Producer;
   - Strategy Pattern: used for implementing the strategy which distributors use 
   to choose their producers;
   - Observer Pattern: used when a producer change his amount of energy for 
   distributors to notify all of his distributors to choose again; in this case,
   the distributors are the observers (Distributor class implements the update 
   method) and the producers are the observables (Producer class implements the
   notifyDistributors method);


