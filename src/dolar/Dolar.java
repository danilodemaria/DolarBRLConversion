/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dolar;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 *
 * @author Administração
 */
public class Dolar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchFieldException, FileNotFoundException, ParseException {
        // TODO code application logic here
        Tela a = new Tela();
        a.setVisible(true);
        AplicaNimbusLookAndFeel.pegaNimbus();
    }
    
}
