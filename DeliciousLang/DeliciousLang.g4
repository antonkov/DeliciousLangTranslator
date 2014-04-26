grammar DeliciousLang;

prog : listStat;

stat : assign
     | expr
     | whilestat
     | ifstat
     | funcdef
     ;

expr : ID '(' listExpr? ')'
     | ID
     | exprNum
     | exprStr
     ;

exprNum  : ID '(' listExpr? ')'                     #Funccall
         | exprNum '^' exprNum                      #AriphmPr0
         | exprNum op=('div'|'mod'|'*'|'/') exprNum #AriphmPr1
         | exprNum op=('+'|'-') exprNum             #AriphmPr2
         | '(' exprNum ')'                          #Paren
         | exprNum 'if' cond 'else' exprNum         #ShortIf
         | INT                                      #Int
         | ID                                       #Var
         ;

exprStr  : STR
         | exprStr 'if' cond 'else' exprStr
         ;

funcdef : rettype=('fun'|'proc') ID '(' listID? ')'
          (  'begin' NEWLINE? listStat NEWLINE? 'end'
          |  NEWLINE? stat
          )
        ;

assign : listID '=' listExpr;

ifstat : 'if'   cond 'then' NEWLINE? listStat NEWLINE?
        ('elif' cond 'then' NEWLINE? listStat NEWLINE? )*
        ('else'             NEWLINE? listStat NEWLINE? )?
        'end';

whilestat : 'while' cond 'do' NEWLINE? listStat NEWLINE? 'end';

listStat : stat (NEWLINE stat)*;

listID : ID (',' ID)*;

listExpr : expr (',' expr)*;

cond : 'not' cond                                #UnarBool
     | cond 'and' cond                           #AndBool
     | cond op=('or'|'xor') cond                 #OrXorBool
     | exprNum op=('>'|'>='|'<'|'<='|'='|'!=') exprNum #BinCond
     | '(' cond ')'                              #ParenCond
     ;

STR     : '"' [a-zA-Z]* '"';
ID      : [a-zA-Z] [a-zA-Z0-9]*;
NEWLINE : '\r'? '\n';
INT     : [0-9]+;
WS      : [ \t] -> skip;