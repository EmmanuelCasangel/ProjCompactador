package classes;
import java.lang.reflect.*;

public class Lista<X>
{
    protected class No
    {
        protected X  info;
        protected No prox;

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        }

        public void setInfo (X x)
        {
            this.info=x;
        }

        public void setProx (No n)
        {
            this.prox=n;
        }

        public No (X x, No n)
        {
            this.info=x;
            this.prox=n;
        }

        public No (X x)
        {
            this (x,null);
        }
    }

    protected No prim;


    public Lista()
    {
        this.prim = null;
    }
    
   public void insiraNoInicio (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;

        this.prim = new No (info,this.prim);
    }



    public void insiraNoFim (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

         X info;
        if (x instanceof Cloneable)
            info = meuCloneDeX(x);
        else
            info = x;

        if(this.prim.prox==null)
            this.prim = new No(info);
        else
        {
            No atual= this.prim;
           
            while(atual.getProx()!=null)
                atual=atual.getProx();

            atual.setProx(new No (info)); 
        }
    }

    public X getPrim()throws Exception
    {
        if(this.prim==null)
            throw new Exception("lista esta vazia");

        if(this.prim instanceof Cloneable)
            return meuCloneDeX(this.prim.info);
        return this.prim.info;
    }

    public X getUltimo()throws Exception
    {   

        if(this.prim==null)
            throw new Exception("lista esta vazia");


        if(this.prim.prox==null)
            if(this.prim instanceof Cloneable)
                return meuCloneDeX(this.prim.info);
            else
                return this.prim.info;

        else
        {
            No atual= this.prim;
           
            while(atual.getProx()!=null)
                atual=atual.getProx();
            
                if(atual instanceof Cloneable)
                    return meuCloneDeX(atual.info);
                else
                    return atual.info;
        }
    }



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
        catch (Exception erro)
        {}
        

        return ret;
    }

   
    

    public void jogueForaPrimeiro()throws Exception
    {
        if(this.prim==null)
            throw new Exception("A lista esta vazia");
        this.prim = this.prim.getProx();
    }

    public void jogueForaUltimo()throws Exception
    {
        if(this.prim==null)
            throw new Exception("A lista esta vazia");
        
        if(this.prim.getProx()!=null)
        {
            No atual = this.prim;

            while(atual.getProx().getProx()!= null)
                atual = atual.getProx();

            atual.setProx(null);
        }
        else
            this.prim = null;    


    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// os  metodos obrigatorios

	    public String toString ()
    {
		String ret   = "{";
		No     atual = this.prim;

		while (atual!=null)
		{
			ret += atual.getInfo();

			if (atual.getProx()!=null) // se atual nao é o ultimo
			    ret += ",";

			atual = atual.getProx();
		}

		ret += "}";

		return ret;
	}


	public int hashCode()
	{
        int ret   = 666;
        No     atual = this.prim;

        while (atual!=null)
        {
            ret =ret*7 + atual.getInfo().hashCode();
            atual = atual.getProx();
        }

        return ret;
	}

    public boolean equals(Object obj)
    {      
        if(this==obj)
            return true;
        if(obj==null)
            return false;  
        if(!(obj instanceof Lista))
            return false;

        Lista<X> lista = (Lista<X>)obj;

        No atual = this.prim;
        No aux  = lista.prim;
        while(atual!= null)
        {
            
            if(aux==null)
                return false;
            
            if(atual!= aux)
                return false;
            
            atual= atual.getProx();
            aux  = aux.getProx();
        }

        return true;


    }

    public Object Clone()
    {
        Lista<X> ret=null;
        try
        {
            ret = new Lista(this);
        }
        catch(Exception erro)
        {}
        
        return ret;
    }

    public Lista(Lista<X> modelo)throws Exception
    {
        if(modelo==null)
            throw new Exception("Modelo Ausente");

        No atual = modelo.prim;
        No aux;

        if(atual!= null)
        {
            this.prim = new No(atual.getInfo());
            atual = atual.getProx();
            aux = this.prim;
        


            while(atual!=null)
            {
                aux.setProx(new No(atual.getInfo()));
                
                aux = aux.getProx();
                atual   = atual.getProx();
            }

        }


    }

    
}
