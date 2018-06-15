package classes;

import static java.awt.SystemColor.text;
import java.io.File;
import java.io.FileWriter;
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
            
            
            int qtdLixos = arq.readInt();
            System.out.println(String.valueOf(qtdLixos));
            int qtdCods = arq.readInt();
            System.out.println(String.valueOf(qtdCods));
            
            int tamCabecalho = 2;
            
            
            Object[][] cods = new Object[qtdCods][3];
            
            for(int i=0; i<qtdCods;i++)
            {
                int tamCod   = arq.readInt();
                int tamVet = (int) Math.ceil(tamCod/8.);  
                byte[] cod = new byte[tamVet];
                
                for(int j=0; j<tamVet; j++)
                {
                    cod[j] = (byte)arq.read();//acho que resolvi o erro
                    tamCabecalho++;
                }
                //pos 0 guarda tamanho do codigo
                cods[i][0] = tamCod;
                tamCabecalho += 4;
                //pos 1 guarda o codigo criado
                cods[i][1]   = cod;
                //guarda o codigo original
                cods[i][2]   = (int)arq.readByte();
                tamCabecalho += 1;
                
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
            
            Lista<Byte> textoDescompactado = new Lista();
            while(!textoCompactado.isEmpty())
            {
                for(int i=0; i<cods.length; i++)
                {
                    String cod="";
                    for(byte pedCod:(byte[])cods[i][1])
                        cod+= Integer.toBinaryString(pedCod);//passa o codigo que nos geramos, que
                                                             //é um byte[], para uma string
                    cod = cod.substring(cod.length()-(int)cods[i][0]);//retira o lixo
                            
                    if(textoCompactado.indexOf(cod)== 0)
                                                        
                    {
                        textoDescompactado.insiraNoFim((byte)cods[i][2]);
                        
                        if((int)cods[i][0] > textoCompactado.length())
                            textoCompactado = "";
                        else
                            textoCompactado = textoCompactado.substring(textoCompactado.length()-(int)cods[i][0]);
                    }                   
                }
            }
            
            int onde  = nArq.indexOf(".comp");
            String nNovoArq = nArq.substring(0, onde-1);
            FileWriter file = new FileWriter(nNovoArq);
            
            //file.write(textoPronto);
            file.close();
            
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