package spock.supoort.annotation;

import org.dbunit.IDatabaseTester;
import org.spockframework.runtime.extension.AbstractMethodInterceptor;
import spock.supoort.MyDataSetProvider;
import spock.supoort.MyDataSourceProvider;

public class MyDbUnitInterceptor extends AbstractMethodInterceptor {

    private IDatabaseTester tester;
    private MyDbUnit dbUnitAnnotation;

    private MyDataSourceProvider dataSourceProvider;
    private MyDataSetProvider dataSetProvider;
}
