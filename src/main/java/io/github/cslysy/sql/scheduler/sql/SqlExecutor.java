package io.github.cslysy.sql.scheduler.sql;

import io.github.cslysy.sql.scheduler.ApplicationConfig.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private final DefaultResultSetSerializer serializer;

    @Autowired
    public SqlExecutor(final JdbcTemplate jdbcTemplate, 
        final DefaultResultSetSerializer serializer) {
        this.jdbcTemplate = jdbcTemplate;
        this.serializer = serializer;
    }

    /**
     * Executes configured SQL statements within single transaction.
     *
     * @param queries queries to be executed
     * @throws Exception
     */
    @Transactional
    public void execute(final List<Query> queries) throws Exception {
        queries.forEach(query -> execute(query));
    }

    private void execute(final Query query) throws DataAccessException {
        jdbcTemplate.execute(query.getStatement(),
            (PreparedStatement ps) -> {
                logger.info("Executing query: {}", query.getStatement());
                ps.execute();
                final ResultSet resultSet = ps.getResultSet();
                if (query.getResultFile().isPresent()) {
                    serializeQueryResult(resultSet, query.getResultFile().get());
                }
                return resultSet;
            }
        );
    }

    private void serializeQueryResult(final ResultSet resultSet, final String resultFile) {
        try {
            serializer.serialize(resultSet, resultFile);
        } catch (Exception ex) {
            throw new RuntimeException("Could not serialize query result", ex);
        }
    }
}
