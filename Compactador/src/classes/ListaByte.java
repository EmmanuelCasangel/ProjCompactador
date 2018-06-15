package classes;
import java.lang.reflect.*;
import classes.lista.*;

public class ListaByte extends Lista<Byte>
{
    
    public byte [] listaToByteArray()
    {
        int tam = this.tamanho();
        
        byte[]ret = new byte[tam];
        
        No atual= this.prim;
        
        for(int i=0; i<tam; i++)
        {
            ret[i] = atual.getInfo();
            atual=atual.getProx();
            
        }
        
        return ret;
    }
    
}
