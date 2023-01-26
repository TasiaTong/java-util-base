package com.base.supoort;

import java.util.Collections;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class MapperUtil {

    public static final String DEFAULT_XML_MAPPER_LOCATION = "classpath*:mybatis/mapper/*Mapper.xml";
    public static final String DEFAULT_MYBATIS_CONFIG = "mybatis/mybatis-config.xml";
    public static final String DEFAULT_MAPPER_PACKAGE = "com.base";

    public static class SqlSessionFactoryHolder {

        public static SqlSessionFactory SQL_SESSION_FACTORY = initSqlSession();
    }

    public static class DataSourceHolder {

        public static DataSource DATA_SOURCE = initDataSource();
    }

    public static <T> T getMapper(Class<T> clazz) {
        SqlSession sqlSession = SqlSessionFactoryHolder.SQL_SESSION_FACTORY.openSession(true);
        return sqlSession.getMapper(clazz);
    }

    private static DataSource initDataSource() {
        // H2 dataSource setting: http://www.h2database.com/javadoc/org/h2/engine/DbSettings.html
        // https://www.jianshu.com/p/4a613dcf182c
        // TRACE_LEVEL_SYSTEM_OUT=2
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .setName("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL;")
                .build();
    }

    private static SqlSessionFactory initSqlSession() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSourceHolder.DATA_SOURCE);
        sqlSessionFactoryBean.setMapperLocations(loadXmlMappers(DEFAULT_XML_MAPPER_LOCATION));
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory,
                DataSourceHolder.DATA_SOURCE);
        Configuration configuration = new Configuration(environment);
        configuration.addLoadedResource(DEFAULT_MYBATIS_CONFIG);
        configuration.addMappers(DEFAULT_MAPPER_PACKAGE);
        sqlSessionFactoryBean.setConfiguration(configuration);

        try {
            return sqlSessionFactoryBean.getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("sqlSession 初始化失败", ex);
        }
    }

    /**
     * 通过单个路径后去多个resource资源
     *
     * @param xmlMapperLocation
     * @return
     */
    private static Resource[] loadXmlMappers(String xmlMapperLocation) {
        try {
            SortedResourcesFactoryBean factoryBean = new SortedResourcesFactoryBean(
                    new ClassPathXmlApplicationContext(),
                    Collections.singletonList(xmlMapperLocation));
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("加载xml mapper文件失败" + xmlMapperLocation, ex);
        }
    }

}
