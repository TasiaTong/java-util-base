package com.base.supoort;

import groovy.sql.Sql;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class MySpockUtil {

    public static void executeSqlScript(DataSource dataSource, String... files)
            throws SQLException {
        Connection connection = dataSource.getConnection();
        for (String file : files) {
            Resource resource = new ClassPathResource(file);
            EncodedResource resourceDelegate = new EncodedResource(resource);
            ScriptUtils.executeSqlScript(connection, resourceDelegate);
            connection.commit();
        }
    }

    public static void dropTables(DataSource dataSource, String... tables) throws SQLException {
        for (String table : tables) {
            new Sql(dataSource).execute("drop table " + table);
            dataSource.getConnection().commit();
        }
    }
}
