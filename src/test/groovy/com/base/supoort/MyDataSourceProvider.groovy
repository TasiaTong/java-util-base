package com.base.supoort;

import org.spockframework.runtime.extension.IMethodInvocation
import com.base.supoort.annotation.MyDbUnit

class MyDataSourceProvider {

    private final MyDbUnit dbunitAnnotation
    private IMethodInvocation setupSpecInvocation
    private IMethodInvocation setupInvocation

    MyDataSourceProvider(MyDbUnit dbunitAnnotation) {
        this.dbunitAnnotation = dbunitAnnotation;
    }

    def withSetupSpecInvocation(IMethodInvocation invocation) {
        this.setupSpecInvocation = invocation
    }

    def withSetupInvocation(IMethodInvocation invocation) {
        this.setupInvocation = invocation
    }
}
