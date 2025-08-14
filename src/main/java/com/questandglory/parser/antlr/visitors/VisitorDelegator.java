package com.questandglory.parser.antlr.visitors;

import com.questandglory.engine.statements.Statement;
import com.questandglory.parser.antlr.LanguageParser;

public class VisitorDelegator extends AbstractLanguageVisitor<Statement> {

    @Override
    public Statement visitAssignmentStatement(LanguageParser.AssignmentStatementContext ctx) {
        return factory.assignmentStatement(ctx);
    }

    @Override
    public Statement visitMessageStatement(LanguageParser.MessageStatementContext ctx) {
        return factory.messageStatement(ctx);
    }

    @Override
    public Statement visitInputStatement(LanguageParser.InputStatementContext ctx) {
        return factory.inputStatement(ctx);
    }

    @Override
    public Statement visitIfConditionStatement(LanguageParser.IfConditionStatementContext ctx) {
        return factory.ifThenCondition(ctx);
    }

    @Override
    public Statement visitWhileConditionStatement(LanguageParser.WhileConditionStatementContext ctx) {
        return factory.whileConditionStatement(ctx);
    }

    @Override
    public Statement visitDefineVariableStatement(LanguageParser.DefineVariableStatementContext ctx) {
        return factory.defineVariable(ctx);
    }

    @Override
    public Statement visitSetChatSystemMessageStatement(LanguageParser.SetChatSystemMessageStatementContext ctx) {
        return factory.setSystemMessageStatement(ctx);
    }

    @Override
    public Statement visitAskStatement(LanguageParser.AskStatementContext ctx) {
        return factory.askStatement(ctx);
    }

    @Override
    public Statement visitTranslateStatement(LanguageParser.TranslateStatementContext ctx) {
        return factory.translateStatement(ctx);
    }
}
