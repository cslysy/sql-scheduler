package io.github.cslysy.sql.scheduler.sql;

import io.github.cslysy.sql.scheduler.ApplicationConfig;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Serialize {@link ResultSet} into plain file.
 *
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class DefaultResultSetSerializer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VelocityEngine velocityEngine;
    private final ApplicationConfig config;

    @Autowired
    public DefaultResultSetSerializer(final VelocityEngine velocityEngine,
        final ApplicationConfig config) {
        this.velocityEngine = velocityEngine;
        this.config = config;
    }

    /**
     * Serializes given ResultSet into file.
     *
     * @param resultSet result set to be serialized
     * @param fileName name of the file where result set will be serialized
     * @throws Exception
     */
    public void serialize(final ResultSet resultSet, final String fileName) throws Exception {
        createQueryLogsDirectory();
        final String fileNameWithTimestamp = buildFileName(fileName);
        logger.info("Serializing query result into : {}", fileNameWithTimestamp);
       
        try (FileWriter fileWriter = new FileWriter(fileNameWithTimestamp)) {
            final Map<String, Object> model = new HashMap<>();
            model.put("query", resultSet.getStatement().toString());
            model.put("rows", resultSetToList(resultSet));
            model.put("rowsAffected", resultSet.getStatement().getUpdateCount());

            VelocityEngineUtils.mergeTemplate(
                velocityEngine, "sql-result.vm",
                StandardCharsets.UTF_8.toString(),
                model, fileWriter
            );
        }
    }

    private List<List<String>> resultSetToList(final ResultSet resultset) throws SQLException {
        final int columnCount = resultset.getMetaData().getColumnCount();
        final List<List<String>> result = new ArrayList<>();
        while (resultset.next()) {
            final List<String> row = new ArrayList<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultset.getString(i));
            }
            result.add(row);
        }
        return result;
    }

    private String buildFileName(final String fileName) {   
        return String.format(
            "%s/%s-%s", 
            config.getQueryLogsDirectory(),
            DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
            fileName
        );
    }

    private void createQueryLogsDirectory() throws IOException {
        new File(config.getQueryLogsDirectory()).mkdirs();
    }
}
