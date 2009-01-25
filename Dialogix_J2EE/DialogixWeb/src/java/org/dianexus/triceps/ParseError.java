package org.dianexus.triceps;

/*import java.lang.*;*/
/*import java.util.*;*/
/*import java.io.*;*/
final class ParseError implements VersionIF {

    Node node = null;
    String dependenciesErrors = null;
    String actionErrors = null;
    String answerChoicesErrors = null;
    String readbackErrors = null;
    String nodeParseErrors = null;
    String nodeNamingErrors = null;

    ParseError(Node node,
               String dependenciesErrors,
               String actionErrors,
               String answerChoicesErrors,
               String readbackErrors,
               String nodeParseErrors,
               String nodeNamingErrors) {
        this.node = node;
        this.dependenciesErrors = dependenciesErrors;
        this.actionErrors = actionErrors;
        this.answerChoicesErrors = answerChoicesErrors;
        this.readbackErrors = readbackErrors;
        this.nodeParseErrors = nodeParseErrors;
        this.nodeNamingErrors = nodeNamingErrors;

    }

    String getDependenciesErrors() {
        return dependenciesErrors;
    }

    String getQuestionOrEvalErrors() {
        return actionErrors;
    }

    String getAnswerChoicesErrors() {
        return answerChoicesErrors;
    }

    String getReadbackErrors() {
        return readbackErrors;
    }

    String getNodeParseErrors() {
        return nodeParseErrors;
    }

    String getNodeNamingErrors() {
        return nodeNamingErrors;
    }

    Node getNode() {
        return node;
    }

    boolean hasDependenciesErrors() {
        return (dependenciesErrors != null && dependenciesErrors.length() > 0);
    }

    boolean hasQuestionOrEvalErrors() {
        return (actionErrors != null && actionErrors.length() > 0);
    }

    boolean hasAnswerChoicesErrors() {
        return (answerChoicesErrors != null && answerChoicesErrors.length() > 0);
    }

    boolean hasReadbackErrors() {
        return (readbackErrors != null && readbackErrors.length() > 0);
    }

    boolean hasNodeParseErrors() {
        return (nodeParseErrors != null && nodeParseErrors.length() > 0);
    }

    boolean hasNodeNamingErrors() {
        return (nodeNamingErrors != null && nodeNamingErrors.length() > 0);
    }
}
