/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.cslysy.sql.scheduler.core;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Configuration
@ConfigurationProperties(prefix = "sql-scheduler")
public class ApplicationConfig {

    private List<String> triggers;

    private List<Query> queries;

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

    @Override
    public String toString() {
        return "ApplicationConfig{" + "triggers=" + triggers + ", queries=" + queries + '}';
    }

    public static class Query {

        private String statement;
        private String resultFile;

        public String getStatement() {
            return statement;
        }

        public void setStatement(String statement) {
            this.statement = statement;
        }

        public String getResultFile() {
            return resultFile;
        }

        public void setResultFile(String resultFile) {
            this.resultFile = resultFile;
        }

        @Override
        public String toString() {
            return "Query{" + "statement=" + statement + ", resultFile=" + resultFile + '}';
        }

    }

}
