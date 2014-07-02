package com.buschmais.jqassistant.plugin.cdi.test;

import static com.buschmais.jqassistant.plugin.java.api.scanner.JavaScope.CLASSPATH;
import static com.buschmais.jqassistant.plugin.java.test.matcher.FieldDescriptorMatcher.fieldDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.MethodDescriptorMatcher.methodDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.buschmais.jqassistant.core.analysis.api.AnalysisException;
import com.buschmais.jqassistant.plugin.cdi.api.type.BeansDescriptor;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.alternative.AlternativeBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.decorator.DecoratorBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.ApplicationScopedBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.ConversationScopedBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.DependentBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.RequestScopedBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.SessionScopedBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.specializes.SpecializesBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.stereotype.CustomStereotype;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

/**
 * Tests for the CDI concepts.
 */
public class CdiIT extends AbstractJavaPluginIT {

    /**
     * Verifies the concept "cdi:Dependent".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void dependent() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(DependentBean.class);
        applyConcept("cdi:Dependent");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Dependent) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(DependentBean.class)));
        assertThat(column, hasItem(methodDescriptor(DependentBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(DependentBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:RequestScoped".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void requestScoped() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(RequestScopedBean.class);
        applyConcept("cdi:RequestScoped");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:RequestScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(RequestScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(RequestScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(RequestScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:SessionScoped".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void sessionScoped() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(SessionScopedBean.class);
        applyConcept("cdi:SessionScoped");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:SessionScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(SessionScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(SessionScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(SessionScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:ConversationScoped".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void conversationScoped() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(ConversationScopedBean.class);
        applyConcept("cdi:ConversationScoped");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:ConversationScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(ConversationScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(ConversationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ConversationScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:ApplicationScoped".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void applicationScoped() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(ApplicationScopedBean.class);
        applyConcept("cdi:ApplicationScoped");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:ApplicationScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(ApplicationScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(ApplicationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ApplicationScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Stereotype".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void stereotype() throws IOException, AnalysisException {
        scanClasses(CustomStereotype.class);
        applyConcept("cdi:Stereotype");
        store.beginTransaction();
        List<Object> column = query("MATCH (s:Cdi:Stereotype) RETURN s").getColumn("s");
        assertThat(column, hasItem(typeDescriptor(CustomStereotype.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Alternative".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void alternative() throws IOException, AnalysisException {
        scanClasses(AlternativeBean.class);
        applyConcept("cdi:Alternative");
        store.beginTransaction();
        List<Object> column = query("MATCH (a:Cdi:Alternative) RETURN a").getColumn("a");
        assertThat(column, hasItem(typeDescriptor(AlternativeBean.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Specializes".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void specializes() throws IOException, AnalysisException, NoSuchMethodException {
        scanClasses(SpecializesBean.class);
        applyConcept("cdi:Specializes");
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Specializes) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(SpecializesBean.class)));
        assertThat(column, hasItem(methodDescriptor(SpecializesBean.class, "doSomething")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Decorator".
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void decorator() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanClasses(DecoratorBean.class);
        applyConcept("cdi:Decorator");
        store.beginTransaction();
        assertThat(query("MATCH (e:Cdi:Decorator) RETURN e").getColumn("e"), hasItem(typeDescriptor(DecoratorBean.class)));
        assertThat(query("MATCH (e:Cdi:Field:Delegate) RETURN e").getColumn("e"), hasItem(fieldDescriptor(DecoratorBean.class, "delegate")));
        store.commitTransaction();
    }

    /**
     * Verifies scanning of the beans descriptor.
     * 
     * @throws java.io.IOException
     *             If the test fails.
     * @throws com.buschmais.jqassistant.core.analysis.api.AnalysisException
     *             If the test fails.
     */
    @Test
    public void beanDescriptor() throws IOException, AnalysisException, NoSuchMethodException, NoSuchFieldException {
        scanDirectory(CLASSPATH, getClassesDirectory(CdiIT.class));
        store.beginTransaction();
        List<Object> column = query("MATCH (beans:Cdi:Beans:File) RETURN beans").getColumn("beans");
        assertThat(column.size(), equalTo(1));
        BeansDescriptor beansDescriptor = (BeansDescriptor) column.get(0);
        assertThat(beansDescriptor.getFileName(), equalTo("/META-INF/beans.xml"));
        store.commitTransaction();
    }
}