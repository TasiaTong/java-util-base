package com.base

import spock.lang.Specification
import com.base.supoort.MapperUtil
import com.base.supoort.MySpockUtil

class BaseSpec extends Specification {

    protected void executeSqlScriptFile(String... sqlFile) {
        MySpockUtil.executeSqlScript(MapperUtil.DataSourceHolder.DATA_SOURCE, sqlFile);
    }

    protected void executeSqlScriptCommand(String... sqlCommand) {
        MySpockUtil.executeSqlScript(MapperUtil.DataSourceHolder.DATA_SOURCE, sqlCommand);
    }

    protected void dropTables(String... tableNames) {
        MySpockUtil.dropTables(MapperUtil.DataSourceHolder.DATA_SOURCE,tableNames);
    }
}
