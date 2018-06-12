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
            
            RandomAccessFile arq = new RandomAccessFile(nArq,"r");
            
            
            int qtdLixo = arq.readInt();
            int qtdCods = arq.readInt();
            
            int tamCabecalho = 2;
            
            
            Object[][] cods = new Object[qtdCods][3];
            
            for(int i=0; i<qtdCods;i++)
            {
                int tamCod   = arq.readInt();
                int tamVet = (int) Math.ceil(tamCod/8.);  
                byte[] cod = new byte[tamVet];
                
                for(i=0; i<tamVet; i++)
                {
                    cod[i] = arq.readByte();
                    tamCabecalho++;
                }
                cods[i][0] = tamCod;
                tamCabecalho += 4;
                cods[i][1]   = cod;
                cods[i][2]   = arq.readChar();
                tamCabecalho += 2;
                
            }
            
            int tamArq =(int)(long) arq.length();
            byte[] textoEmByte = new byte[tamArq-tamCabecalho];
            arq.read(textoEmByte);
            String textoCompactado = "";
            
            for(byte pedaco : textoEmByte )
            {    
                String str  = Integer.toBinaryString(pedaco);

                if(str.length()<8)
                    str = completaString(str);

                if(str.length()>8)
                    str = str.substring(str.length()-8, str.length());
                
                textoCompactado += str;
            }
            
            String textoPronto = "";
            while(!textoCompactado.isEmpty())
            {
                for(int i=0; i<cods.length; i++)
                {
                    if(textoCompactado.indexOf((String)cods[i][1])== 0)
                    {
                        textoPronto += cods[i][2];
                        
                        if((int)cods[i][0] > textoCompactado.length())
                            textoCompactado = "";
                        else
                            textoCompactado = textoCompactado.substring((int)cods[i][0],textoCompactado.length());
                    }
                    
                        
                }
            }
            
            
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