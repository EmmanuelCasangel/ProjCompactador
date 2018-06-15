package classes;
import java.lang.reflect.*;
import classes.arvore.*;

public class ArvoreCompactadora<X> extends Arvore<X>
{
   

    protected String[] cods; 
    
    public void insere(X x)
    {
        if(this.raiz==null)
            this.raiz = new No(x);
    }    

    public ArvoreCompactadora (X x)
    {
        this.raiz = new No(x);
    }
    
    public X getRaiz()
    {
        if (this.raiz instanceof Cloneable)
            return super.meuCloneDeX (raiz.getInfo());
        else
            return this.raiz.getInfo();
    }

    public void junteSe(ArvoreCompactadora<X> outra) throws Exception//funciona
    {
        if (outra==null)
            throw new Exception ("Arvore ausente");

        long freq = ((Informacao)(this.raiz.getInfo())).getFreq()+((Informacao)(outra.raiz.getInfo())).getFreq();

        Informacao info = new Informacao(freq);
        
        No novaRaiz = new No(this.raiz, (X)info, outra.raiz);
        
        this.raiz = novaRaiz;
    }

    public String[] novosCodigos()//tem que arrumar
    {
        //por enquanto deixarei codigo como string

        this.cods  = new String[256];
        
        String cod = "";
        novosCodigos(this.raiz, cod);
        
        return cods; 
    }
 
    private void novosCodigos(No atual, String c)
    {
        if(atual!=null)
            {

            if(((Informacao)atual.getInfo()).getCod() != null)
            {
                cods[((Informacao)atual.getInfo()).getCod()]=c;
            }
            else
            {
                c += "0";
                 novosCodigos(atual.getEsq(), c);
                c = c.substring(0, c.length()-1);
                c += "1";
                novosCodigos(atual.getDir(), c);
                c = c.substring(0, c.length()-1);
            }
        }

    }
    
    public Object clone ()
    {
       ArvoreCompactadora<X> ret=null;

        try
        {
            ret = new ArvoreCompactadora(this);
        }
        catch (Exception erro)
        {} // ignoro porque o construtor de copia so lanca excecao quando o modelo for null e modelo é o this que nunca é null

        return ret;
    }
    
}


