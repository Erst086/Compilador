package fes.aragon.compilador;

import jflex.exceptions.SilentExit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuteJFlex {
    public static void main(String omega[]) {
        String lexerFile = System.getProperty("user.dir") + "/src/fes/aragon/compilador/Lexer.flex",
                lexerFileColor = System.getProperty("user.dir") + "/src/fes/aragon/compilador/LexerColor.flex";
        try {
            jflex.Main.generate(new String[]{lexerFile, lexerFileColor});
        } catch (SilentExit ex) {
            Logger.getLogger(ExecuteJFlex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
