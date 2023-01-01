package spock.supoort.annotation

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.extension.ExtensionException
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo;

class MyDbUnitExtension extends AbstractAnnotationDrivenExtension<MyDbUnit> {

    private MyDbUnitInterceptor fieldInterceptor;

    @Override
    void visitFieldAnnotation(MyDbUnit annotation, FieldInfo field) {
        if (fieldInterceptor) {
            throw new ExtensionException("expected maximum one filed annotation with @DbUnit")
        }
        if (annotation.content() != Object) {
            throw new ExtensionException("Specifying the content of database is only supported for annotation on a feature")
        }
        fieldInterceptor = new MyDbUnitInterceptor(field, annotation)
    }

    @Override
    void visitFeatureAnnotation(MyDbUnit annotation, FeatureInfo feature) {

        def interceptor = new MyDbUnitInterceptor(feature, annotation)
        feature.spec.addSetupInterceptor(interceptor)
        feature.spec.addSetupSpecInterceptor(interceptor)
        feature.featureMethod.addInterceptor(interceptor)
        feature.spec.addCleanupInterceptor(interceptor)
    }

    @Override
    void visitSpec(SpecInfo spec) {
        // Note: Spring integration works because the SpringExtension is a global extension and is executed before this one
        if (fieldInterceptor) {
            spec.addSetupSpecInterceptor(fieldInterceptor)
            spec.addSetupInterceptor(fieldInterceptor)
            spec.features.findAll { f -> !f.featureMethod.reflection.annotations*.annotationType().contains(DbUnit) }
                    .each { f -> f.featureMethod.addInterceptor(fieldInterceptor) }
            spec.addCleanupInterceptor(fieldInterceptor)
        }
    }
}
