package classes;
import java.lang.reflect.*;
import classes.arvore.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArvoreCompactadora extends Arvore<Informacao>
{
   

    protected String[] cods; 
    
    
    public void insere(Informacao x)
    {
        if(this.raiz==null)
            this.raiz = new No(x);
    }    

    public ArvoreCompactadora (Informacao x)
    {
        this.raiz = new No(x);
    }
    
    public Informacao getRaiz()
    {
        if (this.raiz instanceof Cloneable)
            return super.meuCloneDeX (raiz.getInfo());
        else
            return this.raiz.getInfo();
    }
    
    /**
     *
     * @param outra
     * @throws Exception
     */
    public void junteSe(ArvoreCompactadora outra) throws Exception//funciona
    {
        if (outra==null)
            throw new Exception ("Arvore ausente");

        long freq = this.raiz.getInfo().getFreq()+outra.raiz.getInfo().getFreq();

        Informacao info = new Informacao(freq);
        
        No novaRaiz = new No(this.raiz, info, outra.raiz);
        
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
                cods[atual.getInfo().getCod()]=c;
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
    
  public void  descompacta( RandomAccessFile arq, String txtComp)throws Exception
    {
        if (this.raiz==null)
            throw new Exception ("Arvore ausente");
        
       No atual = this.raiz;
        
       while(!"".equals(txtComp))
       {
           
           if(atual.getInfo().getCod()!=null)
            {
                arq.write(atual.getInfo().getCod());                              
                atual = this.raiz;
            }
            else
            {
                if(txtComp.charAt(0)=='0')
                {
                    txtComp = txtComp.substring(1, txtComp.length());
                    atual = atual.getEsq();              
                }
                else
                {
                    if(txtComp.charAt(0)=='1')
                    {
                        txtComp = txtComp.substring(1, txtComp.length());
                       atual = atual.getDir();
                    }
                    else
                        throw new Exception("Codigo não esta na arvore");
                }
            }
       }
        
       
        arq.close();
    }
    
    
    //metodo utilizando recursao e Random acess file como atriubuto da classe
    /*
    public void  descompacta(String nomeArq, String txtComp)throws Exception
    {
        if (this.raiz==null)
            throw new Exception ("Arvore ausente");
        
       escrevArq = new RandomAccessFile(nomeArq,"rw");
        
       auxDescomp(this.raiz,txtComp);
        
       escrevArq.close();
    }
    
    private void auxDescomp(No atual, String str)
    {
        
        if(!"".equals(str))
        {    
            if(atual.getInfo().getCod()!=null)
            {
                try {
                    escrevArq.write(atual.getInfo().getCod());
                } catch (IOException ex) {
                    Logger.getLogger(ArvoreCompactadora.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                auxDescomp(this.raiz, str);
            }
            else
            {
                if(str.charAt(0)=='0')
                {
                    str = str.substring(1, str.length());
                    auxDescomp(atual.getEsq(), str);              
                }
                else//str.charAt(0)=='1'
                {
                    str = str.substring(1, str.length());
                    auxDescomp(atual.getDir(), str);
                }
            }
        }                
    }*/
     
    
    //metodos obrigatorios
    public ArvoreCompactadora (ArvoreCompactadora modelo)throws Exception
    {
        if(modelo==null)
            throw new Exception ("Modelo ausente");
        
        auxConstrutor(this.raiz, modelo.raiz);
    }
    
    private void auxConstrutor(No atualThis, No atualModelo)
    {
        if(atualModelo!= null)
        {
            atualThis = new No(this.meuCloneDeX(atualModelo.getInfo()));
            
            auxConstrutor(atualThis.getEsq(), atualModelo.getEsq());
            auxConstrutor(atualThis.getDir(), atualModelo.getDir());
        }
    }
    
    public Object clone ()
    {
       ArvoreCompactadora ret=null;

        try
        {
            ret = new ArvoreCompactadora(this);
        }
        catch (Exception erro)
        {} // ignoro porque o construtor de copia so lanca excecao quando o modelo for null e modelo é o this que nunca é null

        return ret;
    }
    
}


