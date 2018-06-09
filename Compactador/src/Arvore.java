
import java.lang.reflect.*;

public abstract class Arvore<X>
{
    protected class No
    {        

        protected No esq;
        protected No dir;
        protected X  info;
        

        public No getEsq ()
        {
            return this.esq;
        }

        public No getDir ()
        {
            return this.dir;
        }

        public X getInfo ()
        {
            return this.info;
        }        

        public void setEsq (No e)
        {
            this.esq=e;
        }

        public void setDir (No d)
        {
            this.dir=d;
        }

        public void setInfo (X x)
        {
            this.info=x;
        }          

        public No (No e, X x, No d)
        {
            this.esq =e;            
            this.dir =d;
            this.info = x;
        }

        public No (X x)
        {
            this (null, x,null);
        }
    }

    protected No raiz;
    protected String[] cods; 

    public Arvore ()
    {
        this.raiz = null;
    }
    
    public abstract void insere(X x);

    protected X meuCloneDeX (X x)
    {
        X ret = null;

        try
        {
            Class<?> classe = x.getClass();
            Class<?>[] tipoDoParametroFormal = null; // pq clone tem 0 parametros
            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
            Object[] parametroReal = null;// pq clone tem 0 parametros
            ret = ((X)metodo.invoke (x, parametroReal));
        }
        catch (NoSuchMethodException erro)
        {}
        catch (InvocationTargetException erro)
        {}
        catch (IllegalAccessException erro)
        {}

        return ret;
    }


    public String toString ()
    {
        // faça, para a árvore abaixo...
        //         7
        //        / \
        //       3   9
        //      / \ / \
        //     1  5 8 10
         //retornar "(((()1())3(()5()))7((()8())9(()10())))"
      
        return paraString(this.raiz);
    }

    private String paraString(No atual)
    {
       String r = "(";        
       if(!(atual.getEsq()==null))       
           r += paraString(atual.getEsq());
       r += ")";
       
       r += (atual.getInfo()).toString();
       
       r += "(";        
       if(!(atual.getDir()==null))       
           r += paraString(atual.getDir());
       r += ")";
           
       return r;       
    }

    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;
        if(obj==null)
            return false;
        
        if (!(obj instanceof Arvore)) 
            return false;
         Arvore<X>outra = (Arvore)obj;
         
        return auxEquals(this.raiz, outra.raiz);

    }
    
    private boolean auxEquals(No atualThis, No atualOutra)
    {
       if(!(atualThis==null)||!(atualOutra==null)) 
       {
           if(!((atualThis==null)||(atualOutra==null)))
           {
               if(!(atualThis.getInfo().equals(atualOutra.getInfo())))
                   return false;                   
               if(!(auxEquals(atualThis.getEsq(), atualOutra.getEsq())))
                   return false;
               if(!(auxEquals(atualThis.getDir(), atualOutra.getDir())))
                   return false;         
           }
           else
               return false;
       }
       return true;
    }

    public int hashCode ()
    {
        int ret = 666;
                       
        return auxHashCode(this.raiz, ret);        
    }
    
    private int auxHashCode(No atual, int ret)
    {
        ret += ret*5 + atual.getInfo().hashCode();
        
        if(atual.getEsq()!=null)
            ret += auxHashCode(atual.getEsq(),ret);
        
        if(atual.getDir()!=null)
            ret += auxHashCode(atual.getDir(), ret);
        
        return ret;        
    }

    public Arvore (Arvore modelo)throws Exception
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
    

    public abstract Object clone ();
    
}