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
        
        for(int i; i<tam; i++)
        {
            atual=atual.getProx();
            
        }
        
        return ret;
    }
    
}
