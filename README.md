# sql-scheduler
Sql-scheduler is an application that runs predefined SQL queries according to the configured schedule.

## How to run

### Environment requirements

  * Java 8+ installed

### Step 1 - clone repository
    git clone https://github.com/cslysy/sql-scheduler.git

### Step 2 - Build and run
    ./gradlew bootRun

## Configuration

Application is configured via `application.yml` file.

### Database settings
By default application use in-memory H2 database. In case of other database appropriate jdbc-driver is required in application classpath and connection details must be explicitly defined.

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
Triggers are responsible for scheduling SQL statements execution. Application use UNIX [cron expression][cron] to schedule defined queries.

Example configuration:
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
It is possible to define SELECT, UPDATE, INSERT and DELETE queries. In addition result of each query can be saved into the file within defined query logs directory.

Example configuration:
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

[cron]:https://en.wikipedia.org/wiki/Cron
