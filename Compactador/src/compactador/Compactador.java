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
        
        System.out.println("1 - Compactar"); 
        System.out.println("2 - Descompactar");
        String str = d.readLine();
        
        
        
        classes.Compactador c;
        Descompactador desc;        
        if(str.equals("1"))
        {
            System.out.println("Escreva o caminho do arquivo a ser compactado");        
            String nomeArq = d.readLine();
            c = new classes.Compactador(nomeArq);
        }
        else
        {
            System.out.println("Escreva o caminho do arquivo a ser descompactado");
            String nomeArq = d.readLine();
            if(nomeArq.contains(".comp"))
                desc = new Descompactador(nomeArq);
            else
                System.out.println("Esse arquivo não esta compactado");
        }
       
        p("Acabou");
        
        
        }
        catch(Exception erro)
        {
            erro.printStackTrace();
        }
    }
    
    public static void p(String str)
    {
        System.out.println(str);
    }
    
}
