package fes.aragon.compilador;

import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Compilador extends JFrame {
// se declaran los array list para guardar datos mas facilmente
    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd;
    private HashMap<String, String> identificadores;
    private boolean codeHasBeenCompiled = false;

    public Compilador() {
        //iniciador de los componentes de la interfaz
        initComponents();
        init();
    }

    private void init() {
        //declaracion de dimensiones tipo de texto que se guarda y titulo el compilador
        title = "Compilador";
        setLocationRelativeTo(null);
        setTitle(title);
        directorio = new Directory(this, jtpCode, title, ".txt");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(jtpCode);
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();
            colorAnalysis();
        });
        Functions.insertAsteriskInName(this, jtpCode, () -> {
            timerKeyReleased.restart();
        });
        //iniciacion de todas las arraylist
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>();
        identificadores = new HashMap<>();
        Functions.setAutocompleterJTextComponent(new String[]{}, jtpCode, () -> {
            timerKeyReleased.restart();
        });
    }

    @SuppressWarnings("unchecked")
    //iniciar componentes, agrupar ventanas, paneles y botones
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new JPanel();
        buttonsFilePanel = new JPanel();
        btnAbrir = new JButton();
        btnNuevo = new JButton();
        btnGuardar = new JButton();
        btnGuardarC = new JButton();
        jScrollPane1 = new JScrollPane();
        jtpCode = new JTextPane();
        panelButtonCompilerExecute = new JPanel();
        btnCompilar = new JButton();
        btnEjecutar = new JButton();
        jScrollPane2 = new JScrollPane();
        jtaOutputConsole = new JTextArea();
        jScrollPane3 = new JScrollPane();
        tblTokens = new JTable();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarC.setText("Guardar como");
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });

        GroupLayout buttonsFilePanelLayout = new GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
                buttonsFilePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNuevo)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAbrir)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarC)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsFilePanelLayout.setVerticalGroup(
                buttonsFilePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(buttonsFilePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAbrir)
                                        .addComponent(btnNuevo)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnGuardarC))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jtpCode);

        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        GroupLayout panelButtonCompilerExecuteLayout = new GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
                panelButtonCompilerExecuteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCompilar)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEjecutar)
                                .addContainerGap())
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
                panelButtonCompilerExecuteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelButtonCompilerExecuteLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCompilar)
                                        .addComponent(btnEjecutar))
                                .addContainerGap(7, Short.MAX_VALUE))
        );

        jtaOutputConsole.setEditable(false);
        jtaOutputConsole.setBackground(new Color(255, 255, 255));
        jtaOutputConsole.setColumns(20);
        jtaOutputConsole.setRows(5);
        jScrollPane2.setViewportView(jtaOutputConsole);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Componente léxico", "Lexema", "[Línea, Columna]"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTokens.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblTokens);

        GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(GroupLayout.Alignment.LEADING, rootPanelLayout.createSequentialGroup()
                                                .addComponent(buttonsFilePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(panelButtonCompilerExecute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 693, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 693, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
        );
        rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rootPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonsFilePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panelButtonCompilerExecute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(rootPanelLayout.createSequentialGroup()
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap(8, Short.MAX_VALUE))
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    //boton para nuevo archivo
    private void btnNuevoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        clearFields();
    }//GEN-LAST:event_btnNuevoActionPerformed


    //abrir
    private void btnAbrirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearFields();
        }
    }//GEN-LAST:event_btnAbrirActionPerformed
//guardar
    private void btnGuardarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
//guardar como
    private void btnGuardarCActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (directorio.SaveAs()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed

    private void btnCompilarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        }
        else {
            compile();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed
//ejecutar
    private void btnEjecutarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        btnCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No se puede ejecutar el código ya que se encontró uno o más errores");
            }
            else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                System.out.println(blocksOfCode);
                executeCode(blocksOfCode, 1);


            }
        }
    }//GEN-LAST:event_btnEjecutarActionPerformed
//metodo para escribir lo que interpreta
    private void executeCode(ArrayList<String> blocksOfCode, int repeats) {
        for (int j=1; j<=repeats; j++){
            int repeatCode = -1;
            for (int i=0; i<blocksOfCode.size(); i++){
                String blockOfCode = blocksOfCode.get(i);
                if(repeatCode!=-1){
                    int[] posicionMarcador = CodeBlock.getPositionOfBothMarkers(blocksOfCode, blockOfCode);
                    executeCode(new ArrayList<>(blocksOfCode.subList(posicionMarcador[0], posicionMarcador[1])), repeatCode);
                    repeatCode = -1;
                    i= posicionMarcador[1];

                }else{
                    String[] sentences = blockOfCode.split(";");
                    for (String sentence: sentences){
                        sentence = sentence.trim();
                        if (sentence.contains("izquierda")) {
                            System.out.println("moviendose a la izquierda");
                        }
                        else if (sentence.contains("derecha")) {
                            System.out.println("moviendose a la derecha");
                        }
                        else if (sentence.contains("arriba")) {
                            System.out.println("moviendose hacia arriba");
                        }
                        else if (sentence.contains("abajo")) {
                            System.out.println("Moviendose hacia abajo");
                        }
                        else if (sentence.startsWith("iniciar")) {
                            System.out.println("Iniciando");
                        }
                        else if (sentence.endsWith("fin")) {
                            System.out.println("finalizando");
                        }
                    }
                }
            }
        }
    }

    private void compile() {
        //metodo para ejecutartodo el analisis
        clearFields();
        lexicalAnalysis();
        colorAnalysis();
        fillTableTokens();
        syntacticAnalysis();
        printConsole();
        codeHasBeenCompiled = true;
    }

    private void lexicalAnalysis() {
        //analisis lexico, primero escribe en un archivo fuente lo que se ingresa en el panel, este luego se ocupa en el juego
        Lexer lexer;
        try {
            File codigo = new File("fuente.txt");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
    }

    private void syntacticAnalysis() {
        //analisis sintactico basado en grupos gramaticos justo como cup
        Grammar grammar= new Grammar(tokens, errors);
//variables
        grammar.delete(new String[]{"ERROR", "ERROR_01", "ERROR_02"}, 1);
        grammar.group("VALOR", "(NUMERO)", true);
        grammar.group("ACCION_MOVER", "MOVER MOVIMIENTO VALOR", true, identProd);
        grammar.group("ACCION_MOVER_I", "MOVER MOVIMIENTO", true, 7, "Error: {}, no puedes dejar el movimiento sin cantidad: [#, %]");
        grammar.group("ACCION_MOVER_I", "MOVER VALOR", true, 8, "Error: {}, instrucción de movimiento no valida: [#, %]");
        grammar.group("ACCION_MOVER_I", "MOVIMIENTO", true, 8, "Error: {}, instrucción de movimiento no valida: [#, %]");

        grammar.group("ACCION_MOVER_I", "MOVER", true, 9, "Error: {}, no puedes dejar vacia la instruccion: [#, %]");

        grammar.group("INICIAR", "INICIO PARENTESIS_A PARENTESIS_C PUNTO_COMA", true, identProd);
        grammar.group("INICIAR_I", "INICIO PARENTESIS_A PUNTO_COMA", true, 4, "Error: {}, falta el parentesis de cierre en el inicio: [#, %]");
        grammar.group("INICIAR_I", "INICIO PARENTESIS_C PUNTO_COMA", true, 5, "Error: {}, falta el parentesis de apertura en el inicio: [#, %]");
        grammar.group("INICIAR_I", "INICIO PARENTESIS_A PARENTESIS_C", true, 6, "Error: {}, falta el punto y coma en el inicio: [#, %]");

        grammar.group("FINALIZAR", "FIN PARENTESIS_A PARENTESIS C PUNTO_COMA", true, identProd);
        grammar.group("FINALIZAR_I", "FIN PARENTESIS_A PUNTO_COMA", true, 4, "Error: {}, falta el parentesis de cierre en el inicio: [#, %]");
        grammar.group("FINALIZAR_I", "FIN PARENTESIS_C PUNTO_COMA", true, 5, "Error: {}, falta el parentesis de apertura en el inicio: [#, %]");
        grammar.group("FINALIZAR_I", "FIN PARENTESIS_A PARENTESIS_C", true, 6, "Error: {}, falta el punto y coma en el inicio: [#, %]");
        grammar.group("FUNCION_COMPLETA", "INICIAR (ACCIONMOVER)+ FINALIZAR", true);

        grammar.group("FUNCION_INC", "VALOR", true, 1, "Error: {}, no puedes poner un numero a solas [#, %]");
        grammar.group("FUNCION_INC", "ACCIONMOVER", true, 2, "Error: {}, no puedes poner un movimiento fuera de la estructura [#, %]");
        grammar.group("FUNCION_INC", "INICIAR FINALIZAR", true, 3, "Error: {}, no puedes dejar vacía la estructura [#, %]");
        grammar.finalLineColumn();
        grammar.initialLineColumn();
        grammar.show();
    }


    private void colorAnalysis() {
        //pintar tokens de colores
        textsColor.clear();
        LexerColor lexerColor;
        try {
            File codigo = new File("Colores.txt");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textsColor, jtpCode, new Color(40, 40, 40));
    }

    private void fillTableTokens() {
        //llenar la tabla de los tokens encontrados y sus posiciones
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(tblTokens, data);
        });
    }

    private void printConsole() {
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            jtaOutputConsole.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {
            jtaOutputConsole.setText("Compilación terminada...");
        }
        jtaOutputConsole.setCaretPosition(0);
    }

    private void clearFields() {
        Functions.clearDataInTable(tblTokens);
        jtaOutputConsole.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
            new si().setVisible(true);
            //muestra las 2 interfaces al mismo tiempo
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAbrir;
    private JButton btnCompilar;
    private JButton btnEjecutar;
    private JButton btnGuardar;
    private JButton btnGuardarC;
    private JButton btnNuevo;
    private JPanel buttonsFilePanel;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTextArea jtaOutputConsole;
    private JTextPane jtpCode;
    private JPanel panelButtonCompilerExecute;
    private JPanel rootPanel;
    private JTable tblTokens;
    // End of variables declaration//GEN-END:variables
}
