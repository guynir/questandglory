grammar Language;

options {
    language = 'Java';
    caseInsensitive = true;
}

@header {
    import com.questandglory.language.compiler.CompilerHelper;
}

@members {
   private CompilerHelper helper;

   public void setCompilerHelper(CompilerHelper helper) {
       this.helper = helper;
   }
}

// Lexer rules
BOOLEAN_UNARY_OPERATIONS:   'NOT';
BOOLEAN_BINARY_OPERATIONS:  'AND' | 'OR' | 'XOR';
VAR:                        'VAR';
TRUE:                       ('TRUE' | 'True' | 'true');
FALSE:                      ('FALSE' | 'False' | 'false');
BOOLEAN_LITERAL:            (TRUE | FALSE);
NEWLINE:                    ('\r\n' | '\n\r' | '\n');
WS:                         [ \t]+ -> channel(HIDDEN) ;
STRING_LITERAL:             '"' ( ~[\r\n"\\] | '\\"')* '"' ;
COMMENT:                    ('//' | '%') ~('\r' | '\n')* NEWLINE -> skip ;
NUMERIC_OPERATIONS:         '+' | '-' | '*' | '/';
COMPARE_OPERATIONS:          '==' | '!=' | '<' | '>' | '<=' | '>=';
INT:                        [0-9]+ ;
IDENTIFIER:                 [A-Z_]+;
INTEGER_TYPE                : 'Integer';
STRING_TYPE                 : 'String';
BOOLEAN_TYPE                : 'Boolean';
CHAT_TYPE                   : 'Chat';

program                     : statements EOF;

statements                  :  (statement NEWLINE*)*;

statement                   : messageStatement ';'
                            | inputStatement ';'
                            | ifConditionStatement
                            | whileConditionStatement
                            | defineVariableStatement ';'
                            | assignmentStatement ';'
                            | setChatSystemMessageStatement ';'
                            | askStatement ';'
                            | translateStatement ';'
                            ;

ifConditionStatement            : 'IF' booleanExpression 'THEN' NEWLINE*
                                        (trueStatements=statements)
                                  ('ELSE' NEWLINE* (elseStatements=statements) NEWLINE*)?
                                  'END' 'IF';

whileConditionStatement         : 'WHILE' (condition=booleanExpression) 'DO' NEWLINE*
                                        (statements)
                                  'END' 'WHILE';

messageStatement                : 'MESSAGE' '(' message=stringExpression ')';

inputStatement                  : identifier '=' 'INPUT' '(' ')';

defineVariableStatement         : VAR variableName=variable ':' type=variableType ('=' initialValue=expression)?;

assignmentStatement      : identifier '=' (integerExpression | stringExpression | booleanExpression);

stringExpression         : (stringLiteral | identifier {helper.assertVariableType($identifier.ctx, String.class)}?) ('+' (stringLiteral | stringExpression | integerLiteral | identifier | integerExpression))*
                         ;

integerExpression        : integerLiteral | identifier {helper.assertVariableType($identifier.ctx, Integer.class)}?
                         | left=integerExpression op=numericOperation right=integerExpression
                         | '(' integerExpression ')';
numericOperation         : '+' | '-' | '*' | '/';

booleanExpression        : (booleanLiteral)
                         | identifier {helper.assertVariableType($identifier.ctx, Boolean.class)}?
                         | booleanUnaryExpression
                         | left=booleanExpression op=('AND' | 'OR' | 'XOR' | '==' | '!=') right=booleanExpression
                         | stringBinaryExpression
                         | integerBinaryExpression
                         | '(' booleanExpression ')'
                         ;

booleanUnaryExpression   : 'NOT' booleanExpression;
stringBinaryExpression   : left=stringExpression op=('==' | '!=') right=stringExpression;
integerBinaryExpression  : left=integerExpression op=COMPARE_OPERATIONS right=integerExpression;

// Chat operations
setChatSystemMessageStatement   : 'SETSYSTEMMESSAGE' '(' (chatId=stringLiteral ',')? message=stringExpression ')';
askStatement                    : target=identifier '=' 'ASK' '(' (chatId=stringLiteral ',')? message=stringExpression')';

// Translation
translateStatement       : target=variable '=' 'TRANSLATE' '(' targetLanguage=stringLiteral ',' string=stringExpression ')';

expression               : (stringExpression | integerExpression | booleanExpression);
stringLiteral            : STRING_LITERAL;
booleanLiteral           : ('true' | 'false');
integerLiteral           : INT;
variable                 : IDENTIFIER;
identifier               : IDENTIFIER;
variableType             : IDENTIFIER;
