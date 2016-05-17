package io.github.cslysy.sql.scheduler.schedule;

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
    
    @Autowired
    SqlExecutor sqlExecutor;
    
    @Override
    public void run() {
        try {
            sqlExecutor.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Could not execute SQL queries", ex);
        }
    }
}
