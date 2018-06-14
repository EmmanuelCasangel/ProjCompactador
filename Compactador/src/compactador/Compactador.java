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
        System.out.println("Escreve o caminho do arquivo a ser descompactado"); 
        String nomeArq = d.readLine();
        
        classes.Compactador c = new classes.Compactador(nomeArq);
        String[] cods = c.Compactar();
        
        //Descompactador desc = new Descompactador(nomeArq);
        String cod;
       for(int i = 0; i< cods.length; i++)
       {   
           cod = cods[i];
           
           if(cod!=null)
           {
               p(""+i);
               p(cod);
           }
       }
        
        
        }
        catch(Exception erro)
        {
            
        }
    }
    
    public static void p(String str)
    {
        System.out.println(str);
    }
    
}
