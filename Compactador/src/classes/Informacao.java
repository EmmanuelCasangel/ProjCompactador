package classes;

public class Informacao
{
    protected Integer cod;
    protected int frequencia;

    public Integer getCod()
    {
        return this.cod;
    }

    public int getFreq()
    {
        return this.frequencia;
    }

    public void setCod(Integer c)
    {
        this.cod = c;
    }

    public void setFreq(int f)
    {
         this.frequencia = f;
    }

    public Informacao(Integer c, int freq)
    {
        this.cod = c;
        this.frequencia = freq;
    }

    public Informacao(int freq)
    {
        this(null, freq);
    }
        
        
    public String toString ()
    {
       String ret = "{";
       ret += this.cod + ","+this.frequencia;
       ret += "}";
       return ret;              
    }    

    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;
        if(obj==null)
            return false;
        
        if(!(obj instanceof Informacao))
            return false;
        
        Informacao outra = (Informacao)obj;
        
        if(this.cod != outra.cod)
            return false;
        if(this.frequencia != outra.frequencia)
            return false;
        
        return true;
    }

    public int hashCode ()
    {
        int ret = 666;
        
        ret += ret*5 + new Integer(this.cod).hashCode();
        ret += ret*5 + new Integer(this.frequencia).hashCode();
        
        return ret;        
    }

    public Informacao (Informacao modelo)throws Exception
    {
        if(modelo==null)
            throw new Exception ("Modelo ausente");
        
        this.cod = modelo.cod;
        this.frequencia = modelo.frequencia;            
    }

    public Object clone ()
    {       
        Informacao ret=null;

        try
        {
            ret = new Informacao (this);
        }
        catch (Exception erro)
        {} // ignoro porque o construtor de copia so lanca excecao quando o modelo for null e modelo é o this que nunca é null

        return ret;
    }
    
}