package com.buschmais.jqassistant.scm.common;

import com.buschmais.jqassistant.core.analysis.api.Result;
import com.buschmais.jqassistant.core.analysis.api.rule.*;
import com.buschmais.jqassistant.core.report.impl.InMemoryReportWriter;
import com.buschmais.jqassistant.core.store.api.descriptor.FullQualifiedNameDescriptor;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Provides common functionality for analysis implementations.
 */
public class AnalysisHelper {

    public static final String LOG_LINE_PREFIX = "  \"";

    /**
	 * Constructor.
	 * 
	 * @param console
	 *            The console to use for printing messages.
	 */
	public AnalysisHelper(Console console) {
		this.console = console;
	}

	private Console console;

    /**
     * Logs the given {@link com.buschmais.jqassistant.core.analysis.api.rule.RuleSet} on level info.
     *
     * @param ruleSet The {@link com.buschmais.jqassistant.core.analysis.api.rule.RuleSet}.
     */
    public void printRuleSet(RuleSet ruleSet) throws RemoteException {
        console.info("Groups [" + ruleSet.getGroups().size() + "]");
        for (Group group : ruleSet.getGroups().values()) {
            console.info(LOG_LINE_PREFIX + group.getId() + "\"");
        }
        console.info("Constraints [" + ruleSet.getConstraints().size() + "]");
        for (Constraint constraint : ruleSet.getConstraints().values()) {
            console.info(LOG_LINE_PREFIX + constraint.getId() + "\" - " + constraint.getDescription());
        }
        console.info("Concepts [" + ruleSet.getConcepts().size() + "]");
        for (Concept concept : ruleSet.getConcepts().values()) {
            console.info(LOG_LINE_PREFIX + concept.getId() + "\" - " + concept.getDescription());
        }
        if (!ruleSet.getMissingConcepts().isEmpty()) {
            console.info("Missing concepts [" + ruleSet.getMissingConcepts().size() + "]");
            for (String missingConcept : ruleSet.getMissingConcepts()) {
                console.warn(LOG_LINE_PREFIX + missingConcept);
            }
        }
        if (!ruleSet.getMissingConstraints().isEmpty()) {
            console.info("Missing constraints [" + ruleSet.getMissingConstraints().size() + "]");
            for (String missingConstraint : ruleSet.getMissingConstraints()) {
                console.warn(LOG_LINE_PREFIX + missingConstraint);
            }
        }
        if (!ruleSet.getMissingGroups().isEmpty()) {
            console.info("Missing groups [" + ruleSet.getMissingGroups().size() + "]");
            for (String missingGroup : ruleSet.getMissingGroups()) {
                console.warn(LOG_LINE_PREFIX + missingGroup);
            }
        }
    }
	/**
	 * Verifies the concept results returned by the
	 * {@link com.buschmais.jqassistant.core.report.impl.InMemoryReportWriter} .
	 * <p>
	 * A warning is logged for each concept which did not return a result (i.e.
	 * has not been applied).
	 * </p>
	 * 
	 * @param inMemoryReportWriter
	 *            The
	 *            {@link com.buschmais.jqassistant.core.report.impl.InMemoryReportWriter}
	 *            .
	 */
	public void verifyConceptResults(InMemoryReportWriter inMemoryReportWriter) {
		List<Result<Concept>> conceptResults = inMemoryReportWriter.getConceptResults();
		for (Result<Concept> conceptResult : conceptResults) {
			if (conceptResult.getRows().isEmpty()) {
				console.warn("Concept '" + conceptResult.getExecutable().getId() + "' returned an empty result.");
			}
		}
	}

	/**
	 * Verifies the constraint violations returned by the
	 * {@link InMemoryReportWriter}.
	 * 
	 * @param inMemoryReportWriter
	 *            The {@link InMemoryReportWriter}.
	 */
	public int verifyConstraintViolations(InMemoryReportWriter inMemoryReportWriter) {
		List<Result<Constraint>> constraintViolations = inMemoryReportWriter.getConstraintViolations();
		int violations = 0;
		for (Result<Constraint> constraintViolation : constraintViolations) {
			if (!constraintViolation.isEmpty()) {
				AbstractExecutable constraint = constraintViolation.getExecutable();
				console.error(constraint.getId() + ": " + constraint.getDescription());
				for (Map<String, Object> columns : constraintViolation.getRows()) {
					StringBuilder message = new StringBuilder();
					for (Map.Entry<String, Object> entry : columns.entrySet()) {
						if (message.length() > 0) {
							message.append(", ");
						}
						message.append(entry.getKey());
						message.append('=');
						Object value = entry.getValue();
						message.append(value instanceof FullQualifiedNameDescriptor ? ((FullQualifiedNameDescriptor) value)
								.getFullQualifiedName() : value.toString());
					}
					console.error("  " + message.toString());
				}
				violations++;
			}
		}
		return violations;
	}
}