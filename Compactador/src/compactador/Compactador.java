/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compactador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.in;
import classes.*;

/**
 *
 * @author u17172
 */
public class Compactador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try
        {
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        System.out.println("Escreve o caminho ddo arquivo a ser comactado"); 
        String nomeArq = d.readLine();
        System.out.println("Escreve o caminho ddo arquivo a ser comactado"); 
        String nomeArqNovo =  d.readLine();
        classes.Compactador c = new classes.Compactador(nomeArq, nomeArqNovo);
        c.Compactar();
        
        }
        catch(Exception erro)
        {
            
        }
    }
    
}
