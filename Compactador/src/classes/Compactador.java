package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Compactador {
        private byte[] textoEmByte;
	private String nomeArq;
	private long[] bytesArqLido = new long[256];
	private RandomAccessFile arq; //cria o arquivo
        ArvoreCompactadora[] arvores = new ArvoreCompactadora[256];
        private int qtd=0;
       
	
	
	public Compactador(String nArq)
	{
            try 
            {   
                nomeArq = nArq;
                this.textoEmByte = new byte[(int)(new File(nArq).length())];
                arq = new RandomAccessFile(nArq,"r");
                    
                arq.read(textoEmByte);
                arq.close();
            }
            catch (Exception e) 
            {
		e.printStackTrace();
            }
            Compactar();
	}
        	
	private void lerArquivo()
	{
            RandomAccessFile leArq;
            try 
            {
                leArq = new RandomAccessFile(nomeArq,"r");
                       
                for(;;)
                {
                    int atual;

                    atual = leArq.read();

                    if(atual == -1)
                        break;

                    //System.out.println(atual);				

                    bytesArqLido[atual] = bytesArqLido[atual]+1;
								
                }
                leArq.close();  
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
              
	}
	
        
        private void Compactar()
        {   
            
            try 
            {
                lerArquivo();                 
                iniciarVetor();
                ordenarVetor();
                mesclarArvores();
                //System.out.println(arvores[0].toString());
                finalizarCompactacao();
                System.out.println("Compactou"); 
                
            } 
            catch (Exception e) 
            {		
                e.printStackTrace();
            } 
            
            
        }

        private void finalizarCompactacao()
        {
            try
            {
                RandomAccessFile arqNovo = new RandomAccessFile(nomeArq+".comp", "rw");
                
           
                String[] cods = arvores[0].novosCodigos();
                
               
                
                String txtEmCod = "";
		for (byte b : this.textoEmByte)
                {
                    //funcionou o txtEmCod, pelo menos com texto 
                    txtEmCod += cods[(b&0xFF)];
		}
                
                int qtdCods = 0;
		for(int i=0; i<cods.length;i++) 
                {//esta certo
                    if(cods[i]!=null)
                        qtdCods++;
		}
                
                int qtdLixo = 8 - txtEmCod.length() % 8;
		if (qtdLixo == 8)
			qtdLixo = 0;
                
                //passar a qtdLixo e a qtdCods para byte
                arqNovo.writeInt(qtdLixo);
                arqNovo.writeInt(qtdCods);
                System.out.println(qtdLixo);
                System.out.println(qtdCods);
                //System.out.println(txtEmCod);
                
                int teste = 0;
                for(int i=0; i<cods.length;i++)
                {
                    if(cods[i]!=null)
                    {
                        
                        //byte codAnt  = (byte)i;//codigo Anterior
                        long freq  = bytesArqLido[i];//frquencia do codigo
                        
                        arqNovo.writeLong(freq);
                        arqNovo.writeByte(i);
                        System.out.println(freq);
                        System.out.println(i);
                        
                        teste ++;
                    }
                }
                
                
               System.out.println("Chegou aqui");
               System.out.println(teste);
               
               
                byte[] texto = stringToByteArray(txtEmCod);
                arqNovo.write(texto);                      
                arqNovo.close();
           
            }
            catch(Exception erro)
            {
                erro.printStackTrace();
            }
                
        }
        
        private byte[] stringToByteArray(String str)
        {
            String strC = completaString(str);
            
            int tamVet = strC.length()/8;//(int) Math.ceil(strC.length()/8.);
            
            
            byte[] ret = new byte[tamVet];
            /*
            for(int i=0; i<tamVet-1; i++)
            {
                ret[i] = (byte)Integer.parseInt(strC.substring(i*8, (i*8)+8),2);
            }*/
            
            String auxStr ="";
            int index = 0;
	    for (int i = strC.length()-1; i >= 0; i--) {
	    	auxStr += strC.charAt(strC.length()-1 -i);
	    	
	    	if (auxStr.length() % 8 == 0) {
		    ret[index] = (byte)Integer.parseInt(auxStr, 2);
                   
		    index++;
		    auxStr = "";
	    	}
	    }
            
            return ret;
        }
        
        private String completaString(String str)
        {
            while(str.length()%8!=0)
            {
                str = "0"+str;
            }
           
            //essa parte esta dando certo
            
            /*int sobrando = 8 - str.length() % 8;
            for (int i = 0; i < sobrando; i++) 
            {
                str += "0";
            }*/
            
            return str;
        }

        private void iniciarVetor()
        {
            ArvoreCompactadora arvoreC;

            for(int i=0;i<=bytesArqLido.length-1; i++)
            {
                if(bytesArqLido[i]!=0)
                {
                    
                    Informacao info = new Informacao(i,bytesArqLido[i]);
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
                {                    qtd--;
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
        
        
        
        
                
        
        
	public String toString()
	{
		String ret = "{";
		
		for(int i= 0; i< 255; i ++)
		{
			ret += i + ":" + bytesArqLido[i];
			if(i != 255)
				ret +=",";
		}
			ret += "}";
		
		
		return ret;
		
	}
	
	
}
