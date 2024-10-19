package fes.aragon.compilador;
import compilerTools.Token;
import java_cup.runtime.Symbol;

%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%


/* Comentarios o espacios en blanco */
{espacio} { /*Ignorar*/ }
/*Número*/
{D} {return new Symbol(sym.NUMERO, yychar, yyline, yytext());}
"inicio" {return new Symbol(sym.INICIO, yychar, yyline, yytext());}
fin {return new Symbol(sym.FINAL, yychar, yyline, yytext());}

/*operadores de agrupacion*/
"(" {return new Symbol(sym.PARENTESIS_A, yychar, yyline, yytext());}
")" {return new Symbol(sym.PARENTESIS_C, yychar, yyline, yytext());}

/*Signos de puntuacion*/
";" {return new Symbol(sym.PUNTO_COMA, yychar, yyline, yytext());}
"mover" {return new Symbol(sym.MOVER, yychar, yyline, yytext());}
/*Mover*/
abajo |
arriba |
izquierda |
derecha {return new Symbol(sym.MOVIMIENTO, yychar, yyline, yytext());}

. {return new Symbol(sym.ERROR, yychar, yyline, yytext());}