package spock.supoort;

import org.spockframework.runtime.extension.IMethodInvocation
import spock.supoort.annotation.MyDbUnit

class MyDataSourceProvider {

    private final MyDbUnit dbunitAnnotation
    private IMethodInvocation setupSpecInvocation
    private IMethodInvocation setupInvocation

    public MyDataSourceProvider(MyDbUnit dbunitAnnotation) {
        this.dbunitAnnotation = dbunitAnnotation;
    }

    def withSetupSpecInvocation(IMethodInvocation invocation) {
        this.setupSpecInvocation = invocation
    }

    def withSetupInvocation(IMethodInvocation invocation) {
        this.setupInvocation = invocation
    }
}
