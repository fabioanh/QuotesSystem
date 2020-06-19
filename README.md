# QuotesSystem
Application developed to solve the TradeRepublic coding challenge. The given solution is developed in Java using Gradle as the build system and is structured with the following components:

## Implemented Functionality

* *Connector to the Partners Websocket streams*: The clients package contain the code in charge of parsing the frames and handling them to be stored in the database.
* *Local Database*: In order to keep track of the information MongoDB was used to store the relevant objects. The database was run in a local docker container. In order to develop the following command can be used to quickly start it: docker run --rm --name mongodb -p 27017:27017 mongo:4.2.8
The database was always started as a fresh instance in order to avoid ISIN possible problems.
* *Rest endpoints to consume the required data*: Two endpoints are enabled to consume the required data used by the application. To access the updated prices for the Instruments a GET request to the endpoint **/instruments** will give the information. In order to access the candlesticks for each instrument the endpoint **/instruments/{ISIN}/candlesticks** is enabled.

## Future Work

Unfortunately not all the requirements were implemented. The last point about the exposed websocket stream is still pending. For this an in memory solution was planned. The idea is to keep an updated data structure containing all the active instruments. This structure will keep the values in a queue structure discarding any value older than 10 minutes. The updates for each instrument will be executed on every new frame received from the Partners quotes service. As observed the instrument list is always small enough to keep this solution viable. In case the number of instruments grow dramatically this solution wouldn't be viable anymore as the data structure could be difficult to handle in memory directly in the application.

## Discussion

* How would you change the system to provide scaling capabilities to 50.000 (or more) available `instruments`, each streaming `quotes` between once per second and every few seconds? The current database could handle these requirements, further tuning could be required. In addition to this the system could be scaled horizontally given that the tasks can be executed independently by many independent workers. Further details are given in the answer to the next question.

* How could this system be build in a way that supports failover capabilities so that multiple instances of the system could run simultaneously? The data layer is already able to failover by its design as it can support replicas and be clusterised in order to keep the data provided coming. Furthermore the application could be split into different independent components making each of them able to scale independently. In order to guarantee that the messages coming from the Partners are not handled more than once, a queue component could exist in the middle to make sure that the messages are handled properly. The messages could be handled by a firs component which would persist the information as required. A second component exposing the REST endpoints could exist and be scaled horizontally given the stateless features of the already implemented endpoints. Finally in order to scale the last websocket service, the component mentioned before could be used to make this possible.
