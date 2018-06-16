package classes;

import static java.awt.SystemColor.text;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;




public class Descompactador
{
    
    ArvoreCompactadora[] arvores = new ArvoreCompactadora[256];
    Object[][] cods;
    private int qtd=0;
    
    public Descompactador(String nArq)
    {
        try 
	{       
            
            RandomAccessFile arq = new RandomAccessFile(nArq,"r");
            
            
            int qtdLixos = arq.readInt();
            System.out.println(String.valueOf(qtdLixos));//certo
            int qtdCods = arq.readInt();
            System.out.println(String.valueOf(qtdCods));//certo
            
            int tamCabecalho = 8;
            
            
            cods = new Object[qtdCods][4];
            
            for(int i=0; i<qtdCods;i++)
            {
                int tamCod   = arq.readInt();
                System.out.println(tamCod);
                int tamVet = (int) Math.ceil(tamCod/8.);  
                byte[] cod = new byte[tamVet];
                
                
                arq.read(cod);
                tamCabecalho+= tamVet;
                
                
                //pos 0 guarda tamanho do codigo
                cods[i][0] = tamCod;
                tamCabecalho += 4;
                //pos 1 guarda o codigo criado
                cods[i][1]   = cod;
                //pos 2 guarda a frequencia
                cods[i][2]   = arq.readInt();
                tamCabecalho += 4;
                //pos 3 guarda o codigo original
                cods[i][3]   = arq.read();
                tamCabecalho += 1;                      
                
            }//deu certo?

            
            long tamArq = arq.length();
            byte[] textoEmByte = new byte[(int)tamArq-tamCabecalho];
            arq.read(textoEmByte);     
            arq.close();
            
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
            
            textoCompactado = textoCompactado.substring(qtdLixos);
            System.out.println("TextoCompactado:");
            System.out.println(textoCompactado);//ate aqui esta certo
            
            
            //precisa debugar e ir vendo o que ta errado 
            
            iniciarVetor();
            ordenarVetor();
            mesclarArvores();
            
            System.out.println(arvores[0].toString());
            
            int onde  = nArq.indexOf(".comp");
            String nNovoArq = nArq.substring(0, onde);
            
            RandomAccessFile escrevArq = new RandomAccessFile(nNovoArq,"rw");
            arvores[0].descompacta(escrevArq, textoCompactado);                     
                    
            
            
        }
        catch (Exception e) 
	{
            e.printStackTrace();
	}
    }
    
  
    
    private void iniciarVetor()
    {
            ArvoreCompactadora arvoreC;

            for(int i=0;i<=cods.length-1; i++)
            {
                if((int)cods[i][2]!=0)
                {
                    
                    Informacao info = new Informacao((Integer)cods[i][3], (int)cods[i][2]);
                    arvoreC = new ArvoreCompactadora(info);
                    qtd++;
                    arvores[qtd-1] = arvoreC;                    
                }
            }
        }


        private void ordenarVetor()
        {
        	for (int i = 0; i < qtd; i++) // mudado de qtd-1 para qtd.
        	{
                for (int h = 0; h < qtd; h++) //mudar aqui o i ; tem que ser  i < qtd, antigo estava h<1.
                {                    
                    if (arvores[i].getRaiz().getFreq() > arvores[h].getRaiz().getFreq())  //mudado aqui de < para >
                    {
                        ArvoreCompactadora aux  = arvores[i];
                    
                        arvores[i] = arvores[h];
                        arvores[h] = aux; 
                    }
                }
            }
        }
        
        private void mesclarArvores()throws Exception
        {
            try
            {
                do
                {   
                    qtd--;
                    arvores[qtd-1].junteSe(arvores[qtd]);
                    arvores[qtd] = null;
                    reorganizarVetor();               
                }
                while(qtd>1);
            }
            catch(Exception erro)
            {}
            
        }
        
        private void reorganizarVetor()
        {
        	ArvoreCompactadora aux;
        	for(int i = qtd-1; i>0;i--)
        	{
        		if(arvores[i].getRaiz().getFreq()> arvores[i-1].getRaiz().getFreq())
        		{
                            aux          = arvores[i];
                            arvores[i]   = arvores[i-1];
                            arvores[i-1] = aux;
        		}
        		else
                            break;
        		
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
    
    
    
    
    
    
    
    /* versao que nao passa a frequencia no cabecalho
       e nao monta arvore 
    
    public Descompactador(String nArq)
    {
        try 
	{       
            
            RandomAccessFile arq = new RandomAccessFile(nArq,"r");
            
            
            int qtdLixos = arq.readInt();
            System.out.println(String.valueOf(qtdLixos));
            int qtdCods = arq.readInt();
            System.out.println(String.valueOf(qtdCods));
            
            int tamCabecalho = 8;
            
            
            Object[][] cods = new Object[qtdCods][3];
            
            for(int i=0; i<qtdCods;i++)
            {
                int tamCod   = arq.readInt();
                int tamVet = (int) Math.ceil(tamCod/8.);  
                byte[] cod = new byte[tamVet];
                
                
                arq.read(cod);
                tamCabecalho+= tamVet;
                
                //pos 0 guarda tamanho do codigo
                cods[i][0] = tamCod;
                tamCabecalho += 4;
                //pos 1 guarda o codigo criado
                cods[i][1]   = cod;
                //guarda o codigo original
                cods[i][2]   = arq.read();
                tamCabecalho += 1;                      
                
            }
            
            
            int tamArq =(int)(long) arq.length();
            byte[] textoEmByte = new byte[tamArq-tamCabecalho];
            arq.read(textoEmByte);//tem lugares que ficam com valor negativo     
            arq.close();
            
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
            
            textoCompactado = textoCompactado.substring(qtdLixos);
            System.out.println("TextoCompactado:");
            System.out.println(textoCompactado);//ate aqui esta certo
            
            
            
            
            //ListaByte textoDescompactado = new ListaByte();
            int onde  = nArq.indexOf(".comp");
            String nNovoArq = nArq.substring(0, onde);
            
            RandomAccessFile escrevArq = new RandomAccessFile(nNovoArq,"rw");
            
            while(!textoCompactado.isEmpty())
            {
                for(int i=0; i<cods.length; i++)
                {
                    String cod="";
                    for(byte pedCod:(byte[])cods[i][1])
                        cod+= Integer.toBinaryString((byte)pedCod);//passa o codigo que nos geramos, que
                                                             //é um byte[], para uma string
                    
                    cod = completaString(cod);
                    while(cod.length()<(int)cods[i][0])
                        cod = "0" + cod; 
                    
                    
                                                             
                    int lixo;
                    lixo = cod.length()-(int)cods[i][0];
                    cod  = cod.substring(lixo);//retira o lixo
                            
                    if(textoCompactado.indexOf(cod)== 0)
                                                        
                    {
                        //textoDescompactado.insiraNoFim((byte)cods[i][2]);
                        escrevArq.write(((int)cods[i][2]));
                        
                        
                        if((int)cods[i][0] > textoCompactado.length())
                            textoCompactado = "";
                        else
                            textoCompactado = textoCompactado.substring((int)cods[i][0]);
                    }                   
                }
            }
            
            escrevArq.close();
            
            
            //fiz ate aqui por enquanto
            
            /*byte[] textoPronto = textoDescompactado.listaToByteArray();
            
            
            int onde  = nArq.indexOf(".comp");
            String nNovoArq = nArq.substring(0, onde-1);
            
            RandomAccessFile escrevArq = new RandomAccessFile(nNovoArq,"rw");
           
            escrevArq.write(textoEmByte);
            
            
        }
        catch (Exception e) 
	{
            e.printStackTrace();
	}
    }*/
    
 
}