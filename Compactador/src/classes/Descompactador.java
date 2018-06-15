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
                    cod[j] = arq.readByte();
                    tamCabecalho++;
                }
                //pos 0 guarda tamanho do codigo
                cods[i][0] = tamCod;
                tamCabecalho += 4;
                //pos 1 guarda o codigo criado
                cods[i][1]   = cod;
                //guarda o codigo original
                cods[i][2]   = (arq.readByte() & 0xFF);//pega o valor de 0 a 255
                tamCabecalho += 1;                      // e nao de -127 a 126
                
            }
            
            
            int tamArq =(int)(long) arq.length();
            byte[] textoEmByte = new byte[tamArq-tamCabecalho];
            arq.read(textoEmByte);//tem lugares que ficam com valor negativo     
            arq.close();
            
            String textoCompactado = "";
            
            for(byte pedaco : textoEmByte )
            {    
                String str  = Integer.toBinaryString(pedaco);

                //if(str.length()<8)
                    //str = completaString(str);

                if(str.length()>8)
                    str = str.substring(str.length()-8, str.length());
                
                textoCompactado += str;
            }
            System.out.println(textoCompactado);
            ListaByte textoDescompactado = new ListaByte();
            while(!textoCompactado.isEmpty())
            {
                for(int i=0; i<cods.length; i++)
                {
                    String cod="";
                    for(byte pedCod:(byte[])cods[i][1])
                        cod+= Integer.toBinaryString(pedCod);//passa o codigo que nos geramos, que
                                                             //é um byte[], para uma string
                    
                    int lixo = cod.length()-(int)cods[i][0];
                    cod = cod.substring(lixo);//retira o lixo
                            
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
            
            //fiz ate aqui por enquanto
            
            /*byte[] textoPronto = textoDescompactado.listaToByteArray();
            
            
            int onde  = nArq.indexOf(".comp");
            String nNovoArq = nArq.substring(0, onde-1);
            
            RandomAccessFile escrevArq = new RandomAccessFile(nNovoArq,"rw");
           
            escrevArq.write(textoEmByte);*/
            
            
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