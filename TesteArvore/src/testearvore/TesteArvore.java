/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testearvore;
import classes.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author u17172
 */
public class TesteArvore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Informacao info = new Informacao(122,3);
        Informacao info2 = new Informacao(231,1);
        Informacao info3 = new Informacao(248 ,5);
        
        ArvoreCompactadora arv = new ArvoreCompactadora(info); 
        ArvoreCompactadora arv2 = new ArvoreCompactadora(info2);
        ArvoreCompactadora arv3 = new ArvoreCompactadora(info3);
        
        p(arv.toString());
        p(arv2.toString());
        p(arv3.toString());
        
        try {
            arv.junteSe(arv2);
            arv.junteSe(arv3);
        } catch (Exception ex) {
            Logger.getLogger(TesteArvore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        p(arv.toString());
        
        String[] cods = arv.novosCodigos();
        
        for(String cod : cods)
        {
            if(cod!=null)
                p(cod);
        }
        
    }
    
    public static void p(String str)
    {
        System.out.println(str);
    }
}
