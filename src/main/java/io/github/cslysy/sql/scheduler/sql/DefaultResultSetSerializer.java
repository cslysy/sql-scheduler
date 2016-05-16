package io.github.cslysy.sql.scheduler.sql;

import com.google.common.collect.ImmutableMap;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Serialize {@link ResultSet} to coma separated CSV file
 *
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class DefaultResultSetSerializer {

    @Autowired
    private VelocityEngine velocityEngine;

    public void serialize(ResultSet resultSet, String fileLocation) throws IOException, SQLException {
        try (FileWriter fw = new FileWriter(fileLocation)) {
            Map<String, Object> model = ImmutableMap.<String, Object>builder()
                .put("query", resultSet.getStatement())
                .put("result", resultSetToMap(resultSet))
                .put("metaInfo", resultSet.toString())
                .build();
                
            VelocityEngineUtils.mergeTemplate(
                velocityEngine, "sql-result.vm",
                StandardCharsets.UTF_8.toString(), model, fw
            );
        }
    }

    private Map<String, List<Object>> resultSetToMap(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        Map<String, List<Object>> map = new HashMap<>(columns);
        for (int i = 1; i <= columns; ++i) {
            map.put(md.getColumnName(i), new ArrayList<>());
        }
        while (rs.next()) {
            for (int i = 1; i <= columns; ++i) {
                map.get(md.getColumnName(i)).add(rs.getObject(i));
            }
        }
        return map;
    }

}
