package io.github.cslysy.sql.scheduler.sql;

import io.github.cslysy.sql.scheduler.core.ApplicationConfig;
import io.github.cslysy.sql.scheduler.core.ApplicationConfig.Query;
import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SQL statements executor.
 * 
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class SqlExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;
    private final ApplicationConfig config;
    private final DefaultResultSetSerializer serializer;

    @Autowired
    public SqlExecutor(final JdbcTemplate jdbcTemplate, final ApplicationConfig config,
        final DefaultResultSetSerializer serializer) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
        this.serializer = serializer;
    }

    /**
     * Executes configured SQL statements within transaction.
     * 
     * @throws Exception
     */
    @Transactional
    public void execute() throws Exception {
        for (final Query query : config.getQueries()) {
            jdbcTemplate.execute(
                query.getStatement(),
                (PreparedStatement ps) -> {
                    logger.info("Executing query: {}", query.getStatement());
                    ps.execute();
                    if (query.getResultFile().isPresent()) {
                        try {
                            serializer.serialize(ps.getResultSet(), query.getResultFile().get());
                        } catch (Exception ex) {
                            throw new RuntimeException("Could not serialize query result", ex);
                        }
                    }
                    return ps.getResultSet();
                }
            );
        }
    }
}
