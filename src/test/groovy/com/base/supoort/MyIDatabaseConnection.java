package com.base.supoort;

import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.database.DatabaseConfig;
import com.base.supoort.MapperUtil.DataSourceHolder;

public class MyIDatabaseConnection {

    private volatile static MyIDatabaseConnection instance = null;

    private volatile static IDatabaseConnection conn;

    public synchronized static MyIDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new MyIDatabaseConnection();
        }
        return instance;
    }

    // 通过dbUnit创建数据库连接
    public IDatabaseConnection getConnection()
            throws ClassNotFoundException, SQLException, DatabaseUnitException {
        if (conn == null) {
            Class.forName("org.h2.Driver");
            conn = new MySqlConnection(DataSourceHolder.DATA_SOURCE.getConnection(), "");
            conn.getConfig().setFeature(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
        }
        return conn;
    }
}
