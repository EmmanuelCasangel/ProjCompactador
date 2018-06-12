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
            
            
            int qtdLixo = arq.read();
            int qtdCods = arq.read();
            
            int tam =(int)(long) arq.length();
            byte[] arqEmBytes= new byte[tam];
            
            
            
            String str  = Integer.toBinaryString(byte);
            
            if(str.length()<8)
                str = completaString(str);
            
            if(str.length()>8)
                str = str.substring(str.length()-8, str.length());
        }
        catch (Exception e) 
	{
            e.printStackTrace();
	}
    }
    
    private String completaString(String str)
    {
        while(str.length()%8!=0)
        {
            str = "0"+str;
        }
        return str;
    }
    
    
    
}