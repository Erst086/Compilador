package fes.aragon.compilador;
import compilerTools.TextColor;
import java.awt.Color;
%%
%class LexerColor
%type TextColor
%char
%{
private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
    }
%}
/* Variables básicas de comentarios y espacios */
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
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }

{Numero} { return textColor(yychar, yylength(), new Color(0, 255, 50)); }

"(" |
")" { return textColor(yychar, yylength(), new Color(169, 155, 179)); }
/*Signos de puntuacion*/
"inicio" { return textColor(yychar, yylength(), new Color(87, 35, 120)); }
"fin" { return textColor(yychar, yylength(), new Color(87, 35, 120)); }

";" { return textColor(yychar, yylength(), new Color(169, 155, 179)); }
mover { return textColor(yychar, yylength(), new Color(255, 204, 0)); }
/*Mover*/
arriba |
abajo |
izquierda |
derecha { return textColor(yychar, yylength(), new Color(17, 94, 153)); }


. { /* Ignorar */ }