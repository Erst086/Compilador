package fes.aragon.compilador;
import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }
%}
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Número */
Numero = 0 | [1-9][0-9]*
%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }
/*Número*/
{Numero} { return token(yytext(), "NUMERO", yyline, yycolumn); }
"inicio" { return token(yytext(), "INICIO", yyline, yycolumn); }
fin { return token(yytext(), "FINAL", yyline, yycolumn); }

/*operadores de agrupacion*/
"(" { return token(yytext(), "PARENTESIS_A", yyline, yycolumn); }
")" { return token(yytext(), "PARENTESIS_C", yyline, yycolumn); }

/*Signos de puntuacion*/
";" { return token(yytext(), "PUNTO_COMA", yyline, yycolumn); }
"mover" { return token(yytext(), "MOVER", yyline, yycolumn); }
/*Mover*/
abajo |
arriba |
izquierda |
derecha { return token(yytext(), "MOVIMIENTO", yyline, yycolumn); }

. { return token(yytext(), "ERROR", yyline, yycolumn); }