package io.github.cslysy.sql.scheduler.schedule;

import io.github.cslysy.sql.scheduler.ApplicationConfig;
import io.github.cslysy.sql.scheduler.sql.SqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@link SqlExecutor} wrapper - required by Spring.
 * 
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class SqlTask implements Runnable {
    
    private final SqlExecutor sqlExecutor;
    private final ApplicationConfig config;

    @Autowired
    public SqlTask(final SqlExecutor sqlExecutor, final ApplicationConfig config) {
        this.sqlExecutor = sqlExecutor;
        this.config = config;
    }
    
    @Override
    public void run() {
        try {
            sqlExecutor.execute(config.getQueries());
        } catch (Exception ex) {
            throw new RuntimeException("Could not execute SQL queries", ex);
        }
    }
}
