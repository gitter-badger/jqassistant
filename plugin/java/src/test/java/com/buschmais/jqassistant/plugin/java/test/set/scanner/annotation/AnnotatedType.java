package com.buschmais.jqassistant.plugin.java.test.set.scanner.annotation;

/**
 * An annotated type.
 */
@Annotation(value = "class", arrayValue = { "a", "b" }, classValue = Number.class, enumerationValue = Enumeration.NON_DEFAULT, nestedAnnotationValue = @NestedAnnotation("nestedClass"), nestedAnnotationValues = @NestedAnnotation("nestedClasses"))
public class AnnotatedType {

    @Annotation(value = "field")
    private String annotatedField;

    @Annotation("method")
    public void annotatedMethod(@Annotation("parameter") String parameter) {
        @SuppressWarnings("unused")
        @Annotation("localField")
        String annotatedLocalField = "value";
    }

}
