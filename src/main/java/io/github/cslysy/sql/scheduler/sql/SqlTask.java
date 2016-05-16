package io.github.cslysy.sql.scheduler.sql;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Runs SQL statements.
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
            Logger.getLogger(SqlTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
