package io.github.cslysy.sql.scheduler.sql;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

/**
 * Default implementation 
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class DefaultPreparedStatementCallback implements PreparedStatementCallback<ResultSet> {
    
    @Autowired
    private DefaultResultSetSerializer resultSetSerializer;

    @Override
    public ResultSet doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        ps.execute();

        try {
            resultSetSerializer.serialize(ps.getResultSet(), "/Users/jakubsprega/dev/gitrepo/sql-scheduler/data/result.txt");
        } catch (IOException ex) {
            Logger.getLogger(DefaultPreparedStatementCallback.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ps.getResultSet();
    }
}
