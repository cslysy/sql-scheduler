package io.github.cslysy.sql.scheduler.sql;

import io.github.cslysy.sql.scheduler.core.ApplicationConfig;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class SqlExecutor {

    private final JdbcTemplate jdbcTemplate;
    private final ApplicationConfig config;
    private final DefaultPreparedStatementCallback callback;

    @Autowired
    public SqlExecutor(final JdbcTemplate jdbcTemplate, final ApplicationConfig config,
        final DefaultPreparedStatementCallback callback) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
        this.callback = callback;
    }

    @Transactional
    public void execute() throws Exception {
        for (ApplicationConfig.Query query : config.getQueries()) {
            jdbcTemplate.execute(query.getStatement(), callback);
        }
    }

}
