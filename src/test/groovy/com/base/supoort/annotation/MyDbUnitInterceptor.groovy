package com.base.supoort.annotation

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester
import org.dbunit.database.IDatabaseConnection;
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.ExtensionException
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.FieldInfo
import com.base.supoort.MapperUtil;
import com.base.supoort.MyDataSetProvider;
import com.base.supoort.MyDataSourceProvider;

/**
 * interceptor for setup, feature and cleanup method for Dbunit*/
public class MyDbUnitInterceptor extends AbstractMethodInterceptor {

    private IDatabaseTester tester;
    private MyDbUnit dbUnitAnnotation;

    private MyDataSourceProvider dataSourceProvider;
    private MyDataSetProvider dataSetProvider;


    MyDbUnitInterceptor(FieldInfo dataFieldInfo, MyDbUnit dbUnitAnnotation) {
        assert dataFieldInfo
        assert dbUnitAnnotation
        this.dbUnitAnnotation = dbUnitAnnotation
        this.dataSetProvider = new MyDataSetProvider(dbUnitAnnotation, dataFieldInfo)
        this.dataSourceProvider = new MyDataSourceProvider(dbUnitAnnotation)
    }

    MyDbUnitInterceptor(FeatureInfo featureInfo, MyDbUnit dbunitAnnotation) {
        assert featureInfo
        assert dbunitAnnotation
        this.dbUnitAnnotation = dbunitAnnotation
        this.dataSetProvider = new MyDataSetProvider(dbunitAnnotation, null)
        this.dataSourceProvider = new MyDataSourceProvider(dbunitAnnotation)
    }

    @Override
    void interceptSetupSpecMethod(IMethodInvocation invocation) {
        invocation.proceed()
        dataSourceProvider.withSetupSpecInvocation(invocation)
    }

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) {
        invocation.proceed()
        dataSourceProvider.withSetupInvocation(invocation)
    }

    volatile IDatabaseConnection currentConnection = null


    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) throws Throwable {
        // after setup to allow datasource setup
        def dataSource = MapperUtil.DataSourceHolder.DATA_SOURCE
        if (!dataSource) {
            throw new ExtensionException("Failed to find a javax.sql.DataSource. Specify one as a field or provide one using @DbUnit.datasourceProvider")
        }

        def dataSet = dataSetProvider.findDataSet(invocation.instance)
        if (!dataSet) {
            throw new ExtensionException("Failed to find a dataset. Specify one as a DbUnit-annotated field or provide one using @DbUnit.content")
        }

        tester = new DataSourceDatabaseTester(dataSource, "" ?: null) {
            @Override
            IDatabaseConnection getConnection() throws Exception {
                if (!currentConnection || currentConnection.connection.isClosed()) {
                    currentConnection = super.connection
                }
                return currentConnection
            }

            @Override
            void closeConnection(IDatabaseConnection connection) throws Exception {
                super.closeConnection(connection)
                currentConnection = null
            }
        }

        tester.dataSet = dataSet
        tester.onSetup()

        invocation.proceed()
    }

    @Override
    void interceptCleanupMethod(IMethodInvocation invocation) throws Throwable {
        tester?.onTearDown()
        invocation.proceed()
    }
}
