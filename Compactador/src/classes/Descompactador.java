package classes;

import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Descompactador
{
    
    public Descompactador(String nArq)
    {
        try 
	{       
            
            RandomAccessFile arq = new RandomAccessFile(nArq,"rw");
            
            int tam =(int)(long) arq.length();
            byte[] arqEmBytes= new byte[tam];
            
            arqEmBytes = arq.readByte();
        }
        catch (Exception e) 
	{
            e.printStackTrace();
	}
    }
    
    
    
}