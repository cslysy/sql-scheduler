package io.github.cslysy.sql.scheduler.core;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration.
 *
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Configuration
@ConfigurationProperties(prefix = "sql-scheduler")
public class ApplicationConfig {

    private String queryLogsDirectory;
    private List<String> triggers;
    private List<Query> queries;

    public String getQueryLogsDirectory() {
        return queryLogsDirectory;
    }

    public void setQueryLogsDirectory(String queryLogsDirectory) {
        this.queryLogsDirectory = queryLogsDirectory;
    }

    public List<String> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<String> triggers) {
        this.triggers = triggers;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public static class Query {

        private String statement;
        private Optional<String> resultFile = Optional.empty();

        public String getStatement() {
            return statement;
        }

        public void setStatement(String statement) {
            this.statement = statement;
        }

        public Optional<String> getResultFile() {
            return resultFile;
        }

        public void setResultFile(String resultFile) {
            this.resultFile = Optional.ofNullable(resultFile);
        }
    }
}
