[![Build Status](https://travis-ci.org/cslysy/sql-scheduler.svg)](https://travis-ci.org/cslysy/sql-scheduler)

# sql-scheduler
Sql-scheduler runs predefined SQL queries according to the configured schedule.

## How to run

### Environment requirements

  * Java 8+ installed

### Build and running

    git clone https://github.com/cslysy/sql-scheduler.git
    cd sql-scheduler

Running on Unix-like platforms such as Linux and Mac OS X:

    ./gradlew bootRun

or Windows using the gradlew.bat batch file:

    gradlew bootRun

## Configuration

Application is configured via `application.yml` file.

### Database settings
By default application use in-memory H2 database. In case of other database, appropriate jdbc-driver is required in application classpath and connection details must be explicitly defined.

MySQL example:
```yaml
spring:
    datasource:
        username: dbuser
        password: dbpass
        driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost/test
```

### Triggers
Triggers are responsible for scheduling SQL statements execution. Application use UNIX [cron expressions][cron] to schedule defined queries.

```yaml
sql-scheduler:

    triggers:
      #Every 10 seconds
      - '*/10 * * * * *'
      #Every day at 19:54
      - '0 54 19 * * ?'
      #On the hour nine-to-five weekdays
      - "0 0 9-17 * * MON-FRI"
```

### Queries
It is possible to define SELECT, UPDATE, INSERT and DELETE queries. In addition, result of each query can be saved into the file within defined query logs directory. All defied queries are executed within single transaction.

```yaml
sql-scheduler:
    queryLogsDirectory: query-logs

queries:          
  - statement: "select * from customers"
    resultFile: "all-customers.txt"

  - statement: "select 2"

  - statement: "select * select customers"

  - statement: "insert into customers (name, surname) values ('Tony', 'Stark')"
```

### Complete configuration

```yaml
spring:
    datasource:
        username: dbuser
        password: dbpass
        driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost/test

sql-scheduler:
    queryLogsDirectory: query-logs

    triggers:
      #Every 10 seconds
      - '*/10 * * * * *'
      #Every day at 19:54
      - '0 54 19 * * ?'
      #On the hour nine-to-five weekdays
      - "0 0 9-17 * * MON-FRI"

    queries:          
      - statement: "select * from customers"
        resultFile: "all-customers.txt"

      - statement: "select 2"

      - statement: "select * select customers"

      - statement: "insert into customers (name, surname) values ('Tony', 'Stark')"
```

[cron]:https://en.wikipedia.org/wiki/Cron
