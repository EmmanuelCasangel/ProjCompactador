
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Programa;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.System.in;
import classes.*;

/**
 *
 * @author u17172
 */

public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        String nomeArq = d.readLine();
        Compactador c = new Compactador(nomeArq);
        }
        catch(Exception erro)
        {
            
        }
    }
    
}
