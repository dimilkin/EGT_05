# EGT GATEWAY TASK

### Run the project:
1.Navigate to the project root directory and run the following command:
```shell
docker-compose up
```
...that's it - the project should be running on localhost:8080;

### Test the project:
1. Postman: In the root directory there is included a Postman collections file that can be imported in Postman.
It has the manin requests for the project.
2. Apache JMeter: The root directory contains a JMeter test plan file - egt_gateway.jmx.
It can be imported in JMeter and run from the GUI or executed through the start_jmeter.sh script from the command line.

### Components:

### Rates Collector

A service which asynhronously collects exchange rates from the external API (https://fixer.io/) and stores them in a
database.

### External Service 1

An API endpoint according to the provided requirements which returns the exchange rates for a given time period using
XML.
JAXB is used for marshalling and unmarshalling XML.
The XML response has the following structure:

```xml

<rates>
    <base>EUR</base>
    <date>2024-05-29</date>
    <rates>
        <FJD>2.447386</FJD>
        <MXN>18.330671</MXN>
        <STD>22424.560006</STD>
        . . .
    </rates>
</rates>        
``` 
Or a list of rates for a given time period:


### External Service 2

An API endpoint according to the provided requirements which returns the exchange rates for a given time period using
JSON.
The JSON response has the following structure:

```json
{
  "timestamp": 1716989944215,
  "base": "EUR",
  "date": "2024-05-29",
  "rates": {
    "FJD": 2.447386,
    "MXN": 18.330671,
    "STD": 22424.560006,
    . . .
  }
}
```
or a list of these objects for a give time frame

### Statistics Collector Service

Collects statistical data for the received requests and provides it through an API endpoint.
The JSON response has the following structure:

```json
    {
    "serviceName": "test",
    "numberOfRequests": 100
    }
```

### Notes: 
* The main data entity - ExchangeRatesDao is indexed by base currency given that we expect high requests volume. 
* RabbitMQ is implemented in RatesFetchingServiceImpl
* Data caching needs to be implemented for further preformance optimization
* Virtual threads can be used for calling the external API when requesting data for multiple base currencies