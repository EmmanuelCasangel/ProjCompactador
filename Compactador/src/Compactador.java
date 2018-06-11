

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compactador {
        private String texto;
	private String nomeArq;
	private int[] bytesArqLido = new int[256];
	private RandomAccessFile arq ; //cria o arquivo
        ArvoreCompactadora<Informacao>[] arvores = new ArvoreCompactadora[256];
        private int qtd=0;
       
	
	
	public Compactador(String nArq)
	{
		try 
		{       nomeArq = nArq;
			arq = new RandomAccessFile(nArq,"rw");
		}
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	
	private void lerArquivo()
	{
		try {
                    
                        this.texto = new String(Files.readAllBytes(Paths.get(nomeArq)));
			for(;;)
			{
				int atual;
				
				atual = arq.read();
			
				if(atual == -1)
					break;
				
				System.out.println(atual);
				
				
				
				bytesArqLido[atual] =bytesArqLido[atual] + 1;
					
				
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	  }
	
        
        public void Compactar()
        {   
            try 
            {
                lerArquivo();                 
                iniciarVetor();
                ordenarVetor();
                mesclarArvores();
                finalizarCompactacao();
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
                
                RandomAccessFile arqNovo = new RandomAccessFile(nomeArq, "rw");
           
           
                String[] cods = arvores[0].novosCodigos();
                
                int qtdCods = 0;
		for (String cod : cods) {
                    if (cod != null)
                        qtdCods++;
		}
                
                String txtEmCod = "";
		for (char c : this.texto.toCharArray()) {
			txtEmCod += cods[c-1];
		}
                
                int qtdLixo = 8 - txtEmCod.length() % 8;
		if (qtdLixo == 8)
			qtdLixo = 0;
                
                
                for(int i=0; i<cods.length;i++)
                {
                    if(cods[i]!=null)
                    {
                        byte tamCod    = (byte)  cods[i].length();
                        String codStr = completaString(cods[i]);
                        byte[] codB = stringToByteArray(codStr);
                        byte codAnt  = (byte)i;
                        
                        arqNovo.write(tamCod);
                        arqNovo.write(codB);
                        arqNovo.write(codAnt);
                    }
                }
            
            }
            catch(Exception erro)
            {
                
            }
        }
        
        private byte[] stringToByteArray(String str)
        {
            int tamVet = (int) Math.ceil(str.length()/8.);
            byte[] ret = new byte[tamVet];
            
            for(int i=0; i<tamVet; i++)
            {
                ret[i] = (byte)Integer.parseInt(str.substring(i*8, (i*8)+8),2);
            }
            
            return ret;
        }
        
        private String completaString(String str)
        {
            while(str.length()%8!=0)
            {
                str = "0"+str;
            }
            return str;
        }

        private void iniciarVetor()
        {
        	ArvoreCompactadora<Informacao> arvoreC;

        	for(int i=0;i<=bytesArqLido.length; i++)
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
        	for (int i = 0; i < qtd-1; i++) 
           {
                for (int h = 0; h < i; h++) 
                {                    
                    if (arvores[i].getRaiz().getFreq() < arvores[h].getRaiz().getFreq()) 
                    {
                        ArvoreCompactadora<Informacao>aux  = arvores[i];
                    
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
        	ArvoreCompactadora<Informacao> aux;
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
	
	
	
	
	
	
	/*
	 * trab compactador
	-----------------
	cada byte um novo codigo
	os comuns codigo menor e mais raros codigo maior
	ler um byte de tamanho ate 255
	----------------
	classes e metodos necessarios:
	RandomAccessFile arq = new RandomAccessFile(nomeArq, "rw"); //cria o arquivo
	int arq.read();// le um byte e retorna um int de 0 a 255
	int arq.read(byte[] b);//retorna qtd bytes ele leu
	int read (byte[] b, int off, int len);//poe a partir de uma posicao off ate o len
	long length
	void skipBytes(int n);//pula n bytes
	readFully

	void write(int b);//escreve o bytes
	void write(byte[] b);//escreve tais bytes do vetor
	------------------
	exemplo:

	conteudo:
	o\nescencial\ninvisivel\naos\nolhos\n

	int [] lenght = 256;

	1.zera  tudo
	//[pos] = char (n de ocorrencias)
	[13]=enter(0),...,[97]=a(0),...,[99]=c(0),..., [101]=e(0),...,[104]=h(0),[105]=i(0),...,[108]=l(0),...,[110]=n(0),[111]=o(0),...,[115]=a(0),...,[118]=v(0),...

	2.soma +1 a cada ocorrencia do do byte no arquivo
	[13]=enter(5),...,[97]=a(2),...,[99]=c(1),..., [101]=e(4),...,[104]=h(2),[105]=i(4),...,[108]=l(3),...,[110]=n(2),[111]=o(4),...,[115]=s(5),...,[118]=v(2),...

	3.transformar esse vetor em outro vetor de árvore, os nos contem duas informações: o valor em bytes e o n. ocorrencias
	arvore[] lenght = 256; (No<Informacao>)
	Informacao {
		int byte;
		int freq;
	}

	4.colocar as ocorrencias diferentes de zero sucetivamente
	[0]=13(5),[1]=97(2),[2]=104(2),[3]=99(5),[4]=101(4),[5]=108(3),[6]=110(2),[7]=111(4),[8]=115(5),[9]=118(2),[10]=enter(5),...,[255]=null
	qtd = 11

	5.ordenar esse vetor em ordem descrecente
	[0]=13(5),[1]=115(5),[2]=101(4),[3]=105(4),[4]=111(4),[5]=108(3),[6]=97(2),[7]=104(2),[8]=110(2),[9]=118(2),[10]=99(1),...,[255]=null
	qtd = 11

	(agr é um loop de 6 e 7 ate qtd == 1)
	6.cria um nó com freq da soma dos dois ultimos, ponteiro para os dois ultimos, e o ponteiro do penultimo para esse novo nó e o ponteiro do ultimo nó null
	[0]=13(5),[1]=115(5),[2]=101(4),[3]=105(4),[4]=111(4),[5]=108(3),[6]=97(2),[7]=104(2),[8]=110(2),[9]=((118(2))(99(1)))(3),...,[255]=null
	qtd = 10

	7.reordenar esse novo vetor
	[0]=13(5),[1]=115(5),[2]=101(4),[3]=105(4),[4]=111(4),[5]=108(3),[6]=((118(2))(99(1)))(3),[7]=97(2),[8]=104(2),[9]=110(2),...,[255]=null
	qtd=10

	...

	no fim, na pos 0 do vetor, os bytes q tem a maior frequencia estão mais acima e os q tem menos frequencia pra baixo

	declarar Codigo [] cod e por null em tudo
	{
		Codigo c = new Codigo();
		print(raiz, c);
	}

	Codigo{
		String cod;

		public void mais(int bit){
			this.cod += bit;
		}

		public void tiraUltimo(){
			cod.substring(0, cod.length() - 1);//algo assm
		}
	}

	//todo ponteiro da direita do no vale 1 e da esquerda vale 0 (os bits)

	public print (No raiz, Codigo c) {
		if (raiz != null) {
			cod[raiz.getbyte()] = (Codigo)c.clone();
		} else {
			c.mais(0);
			print(raiz.getEsq(), c);
			c.tiraUltimo();
			c.mais(1);
			print(raiz.getDir(), c);
			c.tiraUltimo();
		}
	}

	[13]=000; [97]=0101; [99]=01001; [101]=011; [104]=1100; [105]=100; [108]=111; [110]=101; [111]=001; [115]=001; [118]=01000; 

	o\nescencial\ninvisivel\naos\nolhos\n

	le de novo e substitui com o novo valor dos codigos

	1010000110010010111101010011000101111000011110000010011010100010000111110000101101001000101111110010100101XXXXXX
	1234567812345678123456781234567812345678123456781234567812345678123456781234567812345678123456781234567812345678
	//esse codigo final ta errado, mas da pra entender a ideia

	poe o cabecalho
	6|11|tabela
	qtd bits lixo|qts diferentes caracteres|um codigo em byte e seu cod novo
	 */
}
