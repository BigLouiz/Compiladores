/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sintatico;

/**
 *
 * @author 09073553
 */
public class TabelaDeSimbolos {
    
    private int topo;  
    private Simbolo[] tabela = new Simbolo[999];
    
    public void TabelaDeSimbolos()
    {
        topo = 0;
    }
    
    public void insere(String lexema,String tipo)
    {
        tabela[topo] = new Simbolo(lexema, tipo, -1);
        topo++;
    }
  
    public void insere(String lexema, String tipo, boolean marca)
    {
        tabela[topo] = new Simbolo(lexema, tipo, -1, marca);
        topo++;
    }
    
    public void insere(String lexema,String tipo, int regiaoMemoria)
    {
        tabela[topo] = new Simbolo(lexema,tipo,regiaoMemoria);
        topo++;
    }
  
    public void insere(String lexema, String tipo, int regiaoMemoria, boolean marca)
    {
        tabela[topo] = new Simbolo(lexema, tipo, regiaoMemoria, marca);
        topo++;
    }
    
    public void insere(String lexema, String tipo, boolean marca, int rotulo)
    {
        tabela[topo] = new Simbolo(lexema, tipo, marca, rotulo);
        topo++;
    }
    
    public void insere(String lexema, String tipo, boolean marca, int rotulo, int regiaoMemoria)
    {
        tabela[topo] = new Simbolo(lexema, tipo, marca, rotulo, regiaoMemoria);
        topo++;
    }
    
    public Simbolo remove()
    {
        topo--;
        return tabela[topo];
    }
    
    public boolean verificaDeclaracao(String lexema)
    {
        for(int i = 0; i < topo; i++)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                return true;
            }

        }
        return false;
    }
    
    public void colocaTipo(String tipo)
    {
        for(int i=topo-1; i>=0;i--)
        {
            if(tabela[i].getTipo().equals("variavel"))
            {
                tabela[i].setTipo(tipo);
            }
            else break;
        }
    }
    
    public boolean temDuplicidade(String lexema)
    {       
        for(int i=topo-1; i>=0;i--)
        {
            if(!tabela[i].getMarca())
            {
                if(lexema.equals(tabela[i].getLexema()))
                {
                    return true;
                }
            }
            else break;
        } 
        return false;
    }
    
    public void colocaTipoFuncao(String tipo)
    {
        tabela[topo-1].setTipo(tipo);
    }
    
    public boolean temFuncaoComLexema(String lexema)
    {       
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                if(tabela[i].getTipo().equals("inteiro") || tabela[i].getTipo().equals("booleano"))
                {
                    if(tabela[i].getRotulo() != 0)
                    {
                        return true;
                    }
                }
            }
        } 
        return false;
    }
    
    public void desempilha()
    {
        while(true){
            
            if(topo >= 0 && tabela[topo-1] != null && !tabela[topo-1].getMarca())
            {
                remove();
            }
            else{
                if(tabela[topo-1].getMarca())
                {
                    tabela[topo-1].setMarca(false);
                }
                
                break;
            }
            
        }
        
        
        //while(topo >= 0 && tabela[topo-1] != null && !tabela[topo-1].getMarca())
        //{

        //        remove();

        //}

    }
    
    public String tipoDoLexema(String lexema)
    {
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                return tabela[i].getTipo();
            }
        } 
        
        return null;
    }
    
    public boolean verificaSeInteiro(String lexema)
    {
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                if(tabela[i].getTipo().equals("inteiro")){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Simbolo pegaSimbolo(String lexema)
    {
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                return tabela[i];
            }
        } 
        
        return null;
    }
    
    public int pegaRegiaoMemoria(String lexema)
    {
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                return tabela[i].getRegiaoMemoria();
            }
        } 
        
        return -1;
    }
    
    public int pegaRotulo(String lexema)
    {
        for(int i = topo-1; i >= 0 ; i--)
        {
            if(lexema.equals(tabela[i].getLexema()))
            {
                return tabela[i].getRotulo();
            }
        } 
        
        return -1;
    }
    
}
