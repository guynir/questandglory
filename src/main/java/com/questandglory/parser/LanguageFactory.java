package com.questandglory.parser;

import com.questandglory.engine.constructs.Identifier;
import com.questandglory.engine.expressions.Expression;
import com.questandglory.engine.expressions.bool.BooleanExpression;
import com.questandglory.engine.expressions.integer.IntegerExpression;
import com.questandglory.engine.expressions.string.LiteralStringValueExpression;
import com.questandglory.engine.expressions.string.StringExpression;
import com.questandglory.engine.statements.*;
import com.questandglory.parser.antlr.LanguageParser;
import com.questandglory.parser.antlr.visitors.TranslateStatement;
import org.antlr.v4.runtime.tree.ParseTree;

public interface LanguageFactory {

    Statement allStatementsDelegator(LanguageParser.StatementContext ctx);

    Identifier identifier(LanguageParser.IdentifierContext ctx);

    Expression<?> anyExpression(ParseTree ctx);

    StringExpression stringExpression(LanguageParser.StringExpressionContext ctx);

    LiteralStringValueExpression literalStringExpression(LanguageParser.StringLiteralContext ctx);

    BooleanExpression booleanExpression(ParseTree ctx);

    IntegerExpression integerExpression(ParseTree ctx);

    Identifier existingVariable(LanguageParser.VariableContext ctx);

    AssignmentStatement assignmentStatement(ParseTree ctx);

    Statements statements(LanguageParser.StatementsContext ctx);

    MessageStatement messageStatement(LanguageParser.MessageStatementContext ctx);

    InputStatement inputStatement(LanguageParser.InputStatementContext ctx);

    IfThenStatement ifThenCondition(LanguageParser.IfConditionStatementContext ctx);

    WhileConditionStatement whileConditionStatement(LanguageParser.WhileConditionStatementContext ctx);

    ProgramStatement programStatement(LanguageParser.ProgramContext ctx);

    DefineVariableStatement defineVariable(LanguageParser.DefineVariableStatementContext ctx);

    SetSystemMessageStatement setSystemMessageStatement(LanguageParser.SetChatSystemMessageStatementContext ctx);

    AskStatement askStatement(LanguageParser.AskStatementContext ctx);

    TranslateStatement translateStatement(LanguageParser.TranslateStatementContext ctx);

}
