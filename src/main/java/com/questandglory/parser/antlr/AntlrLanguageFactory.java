package com.questandglory.parser.antlr;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.string.LiteralStringValueExpression;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.*;
import com.questandglory.language.variables.VariablesDefinition;
import com.questandglory.parser.LanguageFactory;
import com.questandglory.parser.antlr.visitors.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class AntlrLanguageFactory implements LanguageFactory {

    private final VariablesDefinition variables;

    public AntlrLanguageFactory(VariablesDefinition variables) {
        this.variables = variables;
    }

    public Identifier identifier(LanguageParser.IdentifierContext ctx) {
        return setupAndVisit(new IdentifierVisitor(), ctx);
    }

    @Override
    public Expression<?> anyExpression(ParseTree ctx) {
        return switch (ctx) {
            case LanguageParser.BooleanExpressionContext c -> booleanExpression(c);
            case LanguageParser.IntegerExpressionContext c -> integerExpression(c);
            case LanguageParser.StringExpressionContext c -> stringExpression(c);
            default -> throw new IllegalStateException("Unexpected token: " + ctx);
        };
    }

    @Override
    public StringExpression stringExpression(LanguageParser.StringExpressionContext ctx) {
        return setupAndVisit(new StringExpressionParserVisitor(), ctx);
    }

    @Override
    public LiteralStringValueExpression literalStringExpression(LanguageParser.StringLiteralContext ctx) {
        return setupAndVisit(new StringLiteralVisitor(), ctx);
    }


    @Override
    public BooleanExpression booleanExpression(ParseTree ctx) {
        return setupAndVisit(new BooleanExpressionParserVisitor(), ctx);
    }

    @Override
    public IntegerExpression integerExpression(ParseTree ctx) {
        return setupAndVisit(new IntegerExpressionVisitor(), ctx);
    }

    @Override
    public Identifier existingVariable(LanguageParser.VariableContext ctx) {
        return setupAndVisit(new ExistingVariableVisitor(), ctx);
    }

    @Override
    public AssignmentStatement assignmentStatement(ParseTree ctx) {
        return (AssignmentStatement) setupAndVisit(new AssignmentParserVisitor(), ctx);
    }

    @Override
    public Statements statements(LanguageParser.StatementsContext ctx) {
        return setupAndVisit(new StatementsVisitor(), ctx);
    }

    @Override
    public MessageStatement messageStatement(LanguageParser.MessageStatementContext ctx) {
        return setupAndVisit(new MessageStatementVisitor(), ctx);
    }

    @Override
    public InputStatement inputStatement(LanguageParser.InputStatementContext ctx) {
        return setupAndVisit(new InputStatementVisitor(), ctx);
    }

    @Override
    public IfThenStatement ifThenCondition(LanguageParser.IfConditionStatementContext ctx) {
        return setupAndVisit(new IfConditionVisitor(), ctx);
    }

    @Override
    public WhileConditionStatement whileConditionStatement(LanguageParser.WhileConditionStatementContext ctx) {
        return setupAndVisit(new WhileConditionVisitor(), ctx);
    }

    @Override
    public Statement allStatementsDelegator(LanguageParser.StatementContext ctx) {
        return setupAndVisit(new VisitorDelegator(), ctx);
    }

    @Override
    public ProgramStatement programStatement(LanguageParser.ProgramContext ctx) {
        return setupAndVisit(new ProgramVisitor(), ctx);
    }

    @Override
    public DefineVariableStatement defineVariable(LanguageParser.DefineVariableStatementContext ctx) {
        return setupAndVisit(new DefineVariableVisitor(), ctx);
    }

    public SetSystemMessageStatement setSystemMessageStatement(LanguageParser.SetChatSystemMessageStatementContext ctx) {
        return setupAndVisit(new SetSystemMessageVisitor(), ctx);
    }

    @Override
    public AskStatement askStatement(LanguageParser.AskStatementContext ctx) {
        return setupAndVisit(new AskVisitor(), ctx);
    }

    @Override
    public TranslateStatement translateStatement(LanguageParser.TranslateStatementContext ctx) {
        return setupAndVisit(new TranslationVisitor(), ctx);
    }

    protected <T extends AbstractLanguageVisitor<?>> T setup(T visitor) {
        visitor.setVariables(variables);
        visitor.setFactory(this);
        return visitor;
    }

    protected <R, T extends AbstractLanguageVisitor<R>> R setupAndVisit(T visitor, ParseTree ctx) {
        return setup(visitor).visit(ctx);
    }
}
