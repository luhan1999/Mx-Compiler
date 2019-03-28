grammar Mx;

program:   programSection* EOF;

programSection
    :   functionDeclaration
    |   classDeclaration
    |   variableDeclaration
    ;

// ------- Declaration --------
functionDeclaration
    :   typeORvoid? Identifier '(' parameterDeclList? ')' body
    ;

classDeclaration
    :   Class Identifier '{' memberDecl* '}'
    ;

memberDecl:   functionDeclaration | variableDeclaration;

variableDeclaration
    :   type variableList ';'
    ;

variableList
    :   variable (',' variable)*
    ;

variable
    :   Identifier ('=' expression)?
    ;

parameterDeclList
    :   parameter (',' parameter)*
    ;

parameter
    :   type Identifier
    ;

typeORvoid
    :   type
    |   Void
    ;

type
    :   type '[' ']'        #arraytype
    |   nonArraytype        #nonArrayTypetype
    ;

nonArraytype
    :   Int
    |   Bool
    |   String
    |   Identifier
    ;

// -------Statement-------
statement
    :   body                       #bodyStmt
    |   expression';'              #exprStmt
    |   conditionStatement         #condStmt
    |   loopStatement              #loopStmt
    |   jumpStatement              #jumpStmt
    |   ';'                        #blankStmt
    ;

body
    : '{' bodyStatement* '}'
    ;

bodyStatement
    :   statement                  #stmt
    |   variableDeclaration        #varDeclStmt
    ;

conditionStatement
    :   If  '(' expression ')' thenStmt=statement (Else elseStmt=statement)?
    ;

loopStatement
    :   While '(' expression ')' statement          #whileStmt
    |   For '(' init=expression? ';'
                cond=expression? ';'
                step=expression? ')' statement      #forStmt
    ;

jumpStatement
    :   Continue ';'                                #continueStmt
    |   Break ';'                                   #breakStmt
    |   Return expression? ';'                      #returnStmt
    ;

// -------Expression-------
expression
    :   expression op=('++' | '--')                             # suffixExpr
    |   expression '.' Identifier                               # memberAccessExpr

    |   arr=expression '[' sub=expression ']'                   # subscriptExpr
    |   expression '(' parameterList? ')'                       # funcCallExpr

    |   <assoc=right> op=('++'|'--') expression                 # prefixExpr
    |   <assoc=right> op=('+' | '-') expression                 # prefixExpr
    |   <assoc=right> op=('!' | '~') expression                 # prefixExpr

    |   <assoc=right> New creator                               # newExpr

    |   lhs=expression op=('*' | '/' | '%') rhs=expression      # binaryExpr
    |   lhs=expression op=('+' | '-') rhs=expression            # binaryExpr
    |   lhs=expression op=('<<'|'>>') rhs=expression            # binaryExpr
    |   lhs=expression op=('<' | '>') rhs=expression            # binaryExpr
    |   lhs=expression op=('<='|'>=') rhs=expression            # binaryExpr
    |   lhs=expression op=('=='|'!=') rhs=expression            # binaryExpr
    |   lhs=expression op='&' rhs=expression                    # binaryExpr
    |   lhs=expression op='^' rhs=expression                    # binaryExpr
    |   lhs=expression op='|' rhs=expression                    # binaryExpr
    |   lhs=expression op='&&' rhs=expression                   # binaryExpr
    |   lhs=expression op='||' rhs=expression                   # binaryExpr

    |   <assoc=right> lhs=expression op='=' rhs=expression      # assignExpr

    |   primaryExpression                                       # primaryExpr
    ;

primaryExpression
    :   Identifier                                              # identifierExpr
    |   This                                                    # thisExpr
    |   constant                                                # constExpr
    |  '(' expression ')'                                       # subExpr
    ;

constant
    :   IntConstant       #intConst
    |   StringConstant    #stringConst
    |   Null              #nullConst
    |   BoolConstant      #boolConst
    ;

creator
    :   nonArraytype ('[' expression ']')+ ('[' ']')+ ('['expression']')+       #errorCreator
    |   nonArraytype ('[' expression ']')+ ('[' ']')*                           #arrayCreator
    |   nonArraytype                                                            #nonArrayCreater
    ;

parameterList
    :   expression (',' expression)*
    ;

// ------ Define -------
Class   :'class';
Void    :'void';
Int     :'int';
Bool    :'bool';
String  :'string';
If      :'if';
Else    :'else';
For     :'for';
While   :'while';
Break   :'break';
Continue :'continue';
Return  :'return';
This    :'this';
New     :'new';
Null    :'null';

// ---- Constant ----
fragment StringCharacter:       ~["\\\r\n]   |   '\\' ["n\\];
IntConstant:                    '0'  |   [1-9] [0-9]*;
StringConstant:                 '"' StringCharacter* '"';
BoolConstant:                   'true'  |   'false';


// ---- Identifier ----
fragment Letter:    [a-zA-Z];
fragment NoDigit:   [a-zA-Z_];
fragment Digit:     [0-9];

Identifier:    Letter (NoDigit | Digit)*;


// ---- Skip ----
WhiteSpace:     [ \t]+ -> skip;
NewLine:        '\r'? '\n' -> skip;
LineComment:    '//' ~[\r\n]* -> skip;
BlockComment:   '/*' .*? '*/' -> skip;


