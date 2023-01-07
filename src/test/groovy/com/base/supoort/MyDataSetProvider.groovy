package com.base.supoort

import groovy.xml.MarkupBuilder
import org.dbunit.dataset.DataSetException
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.ReplacementDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.spockframework.runtime.extension.ExtensionException
import org.spockframework.runtime.model.FieldInfo
import com.base.supoort.annotation.MyDbUnit

class MyDataSetProvider {

    private final MyDbUnit dbUnitAnnotation
    private final FieldInfo dataFieldInfo

    MyDataSetProvider(MyDbUnit dbUnitAnnotation, FieldInfo fieldInfo) {
        this.dbUnitAnnotation = dbUnitAnnotation
        this.dataFieldInfo = fieldInfo
    }

    IDataSet findDataSet(Object target) {
        String dataSetAsString = null
        // xml content 集合
        if (Closure.class != dbUnitAnnotation.content()) {
            def dataSetClosure = dbUnitAnnotation.content().newInstance(target, target)
            dataSetAsString = writeXmlDataSet(dataSetClosure as Closure)
        }
        // xml 文件地址
        if (!dataSetAsString && !dbUnitAnnotation.xmlLocation().isEmpty()) {
            return replacementDataSet(MyDbUnitUtil.loadXml(target.getClass(), dbUnitAnnotation.xmlLocation()))
        }
        // csv 文件地址
        if (!dataSetAsString && !dbUnitAnnotation.csvLocation().isEmpty()) {
            return replacementDataSet(MyDbUnitUtil.loadCsv(target.getClass(), dbUnitAnnotation.csvLocation()));
        }

        if (!dataSetAsString) {
            throw new ExtensionException("failed to find a data set." + " Specify one as DbUnit-annotated field or provide one using @DbUnit.content")
        }
        return replacementDataSet(new StringReader(dataSetAsString));
    }

    private static String writeXmlDataSet(Closure dataSetClosure) {
        def xmlWriter = new StringWriter()
        def builder = new MarkupBuilder(xmlWriter)
        builder.dataset(dataSetClosure)
        return xmlWriter as String
    }

    private ReplacementDataSet replacementDataSet(Reader input) throws DataSetException {
        def flatXmlDataSet = new FlatXmlDataSetBuilder().build(input)

        def replacementDataSet = new ReplacementDataSet(flatXmlDataSet)
        replacementDataSet.addReplacementObject("[NULL]", null)
        replacementDataSet.addReplacementObject("[NOW]", new Date())
        return replacementDataSet
    }

    private ReplacementDataSet replacementDataSet(IDataSet dataSet) {
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet)

        replacementDataSet.addReplacementObject("[NULL]", null)
        replacementDataSet.addReplacementObject("[NOW]", new Date())
        return replacementDataSet
    }
}
