package com.buschmais.jqassistant.plugin.java.impl.scanner.visitor;

import com.buschmais.jqassistant.plugin.java.api.model.MethodDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.ValueDescriptor;

/**
 * Visitor for default values of annotation methods.
 * <p>
 * Creates dependencies of the method to the type of the default value.
 * </p>
 */
public class AnnotationDefaultVisitor extends AbstractAnnotationVisitor<MethodDescriptor> {

    /**
     * Constructor.
     * 
     * @param visitorHelper
     *            The
     *            {@link com.buschmais.jqassistant.plugin.java.impl.scanner.visitor.VisitorHelper}
     *            .
     */
    protected AnnotationDefaultVisitor(MethodDescriptor descriptor, VisitorHelper visitorHelper) {
        super(descriptor, visitorHelper);
    }

    @Override
    protected void setValue(MethodDescriptor descriptor, ValueDescriptor<?> value) {
        descriptor.setHasDefault(value);
    }
}
