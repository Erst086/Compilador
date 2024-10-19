package fes.aragon.compilador;
import static fes.aragon.compilador.tokens;
%%
%class Lexer
%type Tokens
espacio=[ ,\t,\r]+
%{
    public String lexeme;
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}
/* Salto de linea */
( "\n" ) {return LINEA;}
( "inicio" ) {lexeme = yytext(); return INICIO;}
( "fin" ) {lexeme = yytext(); return FINAL;}

/*operadores de agrupacion*/
( "(" ) {lexeme = yytext(); return PARENTESIS_A;}
( ")" ) {lexeme = yytext(); return PARENTESIS_C;}

/*Signos de puntuacion*/
( ";" ) {lexeme = yytext(); return PUNTO_COMA;}
( "mover" ) {lexeme = yytext(); return MOVER;}
/*Mover*/
( "abajo" |
"arriba" |
"izquierda" |
"derecha" ) {lexeme = yytext(); return MOVIMIENTO;}

. {return ERROR;}