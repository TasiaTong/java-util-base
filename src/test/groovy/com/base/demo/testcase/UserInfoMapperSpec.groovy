package com.base.demo.testcase

import com.base.BaseSpec
import com.base.demo.UserInfoPOMapper
import com.base.supoort.MapperUtil
import com.base.supoort.MyDbUnitUtil
import com.base.supoort.MyIDatabaseConnection
import com.base.supoort.annotation.MyDbUnit
import groovy.sql.Sql
import org.dbunit.dataset.IDataSet
import org.dbunit.operation.DatabaseOperation

class UserInfoMapperSpec extends BaseSpec {

    private UserInfoPOMapper userInfoPOMapper = MapperUtil.getMapper(UserInfoPOMapper.class)

    def setup() {
        executeSqlScriptFile("h2/demo/userinfo/schema.sql")
    }

    def cleanup() {
        dropTables("user_info")
    }

    @MyDbUnit(content = {
        user_info(id: 1, name: "one", age: 10)
        user_info(id: 2, name: "two", age: 20)
        user_info(id: 3, name: "three", age: 30)
    })
    def "demo Closure"() {

        when:
        int beforeCount = userInfoPOMapper.count()
        def result = new Sql(MapperUtil.DataSourceHolder.DATA_SOURCE).firstRow("select * from `user_info`")
        int deleteCount = userInfoPOMapper.deleteById(1L)
        int afterCount = userInfoPOMapper.count()

        then:
        beforeCount == 3
        result.name == "one"
        deleteCount == 1
        afterCount == 2
    }

    @MyDbUnit(csvLocation = "h2/demo/userinfo")
    def "demo csvLocation"() {
        when:
        def userInfoPOS = userInfoPOMapper.selectAll();
        then:
        println(userInfoPOS)
    }

    @MyDbUnit(xmlLocation = "h2/demo/userinfo/user_info.xml")
    def "demo xmlLocation"() {
        when:
        def userInfoPOS = userInfoPOMapper.selectAll();
        then:
        println(userInfoPOS)
    }

    def "demo dbunit api"() {
        IDataSet dataSet = MyDbUnitUtil.loadCsv("h2/demo/userinfo")
        DatabaseOperation.CLEAN_INSERT.execute(MyIDatabaseConnection.getInstance().getConnection(), dataSet)

        when:
        def userInfoPOS = userInfoPOMapper.selectAll()

        then:
        println(userInfoPOS)
    }
}