/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.StringTokenizer;
import lexico.Token;
import sintatico.TabelaDeSimbolos;
import sintatico.Simbolo;
import geracao.GeracaoDeCodigo;

/**
 *
 * @author 09073553
 */
public class AnalisadorSemantico 
{
    private String           expressao;
    private String           tipos;    
    private Pilha            pilha;
    private TabelaDeSimbolos tabela;
    private int linha, coluna;
    
    public AnalisadorSemantico(TabelaDeSimbolos tabela) 
    {
        pilha = new Pilha();
        expressao = "";
        tipos = "";
    }
    
    public void insereFator(Token fator, String tipo)
    {
        expressao+=" "+fator.getLexema();
        if(fator.getSimbolo().equals("sidentificador"))
        {
            if(tipo.equals("inteiro") || tipo.equals("funcao inteiro"))
            {
                tipos+=" inteiro";
            }
            else if(tipo.equals("booleano") || tipo.equals("funcao booleano"))
            {
                tipos+=" booleano";
            }

        }
        else if (  fator.getSimbolo().equals("sverdadeiro")
                || fator.getSimbolo().equals("sfalso") )
        {
            tipos+=" booleano";
        }
        else if (fator.getSimbolo().equals("snumero"))
        {
            tipos+=" inteiro";
        }
        linha = fator.getLinha();
        coluna = fator.getColuna();
    }
    
    public void insereOperador(Token operador)
    {
        expressao+=" "+operador.getLexema();
        
        if(operador.getSimbolo().equals("sdiv"))
        {
            tipos+=" /";
        }
        else if (  operador.getSimbolo().equals("smais")
                || operador.getSimbolo().equals("smenos")
                || operador.getSimbolo().equals("sou")
                || operador.getSimbolo().equals("se") 
                || operador.getSimbolo().equals("smult")
                || operador.getSimbolo().equals("smaior")
                || operador.getSimbolo().equals("smaiorig") 
                || operador.getSimbolo().equals("sig") 
                || operador.getSimbolo().equals("smenor") 
                || operador.getSimbolo().equals("smenorig") 
                || operador.getSimbolo().equals("sdif"))//
        {
            tipos+=" "+operador.getLexema();
        }
        linha = operador.getLinha();
        coluna = operador.getColuna();
    }
    
    public void insereSinal(Token sinal)
    {

        
        if(sinal.getSimbolo().equals("snao"))
        {
            tipos+=" "+sinal.getLexema();
            expressao+= " " + sinal.getLexema();
        }
        else if(sinal.getSimbolo().equals("smenos"))
        {
            tipos+=" @";
            expressao+=" @";
        }
        else if(sinal.getSimbolo().equals("smais"))
        {
            
        }
        linha = sinal.getLinha();
        coluna = sinal.getColuna();

    }
    
    public void insereParenteses(Token par) 
    {
        expressao+=" "+par.getLexema();
        tipos+=" "+par.getLexema();
        linha = par.getLinha();
        coluna = par.getColuna();
    }
    
    
    public String transformacaoPosfixa(String exp)
    {
        StringTokenizer tok = new StringTokenizer(exp," ");
        String aux;
        String           posfixa;
        posfixa = "";
        aux = "";
        
        while(tok.hasMoreTokens())
        {
            aux = tok.nextToken();
            if(aux.equals("("))
            {
                pilha.push(aux);
            }
            else if(ehOperador(aux))
            {
                while(precedencia(aux) <= precedencia(pilha.peek()))
                {
                    posfixa+= " " + pilha.pop();
                }

                pilha.push(aux);
            }
            
            else if(aux.equals(")"))
            {
                while(!pilha.peek().equals("("))
                {
                    posfixa +=" "+pilha.pop();
                }
                pilha.pop();
            }
            else
            {
                posfixa +=" "+aux;

            }
        }
        
        while(!pilha.isEmpty())
        {
            aux = pilha.pop();
            if(!aux.equals("("))
            {
                posfixa +=" "+aux;
            }
        }
        
        return posfixa;
    }

    private boolean ehOperador(String aux) 
    {
        if(aux.equals("+"))
        {
            return true;
        }
        else if(aux.equals("-"))
        {
            return true;
        }
        else if(aux.equals("*"))
        {
            return true;
        }
        else if(aux.equals("div"))
        {
            return true;
        }
        else if(aux.equals("<"))
        {
            return true;
        }
        else if(aux.equals(">"))
        {
            return true;
        }
        else if(aux.equals("<="))
        {
            return true;
        }
        else if(aux.equals(">="))
        {
            return true;
        }
        else if(aux.equals("e"))
        {
            return true;
        }
        else if(aux.equals("ou"))
        {
            return true;
        }
        else if(aux.equals("="))
        {
            return true;
        }
        else if(aux.equals("<>"))
        {
            return true;
        }
        else if(aux.equals("@"))
        {
            return true;
        }
        else if(aux.equals("/"))
        {
            return true;
        }
        else if(aux.equals("nao"))
        {
            return true;
        }
        else return false;
            
    }
    

    public String getExpressao() {
        return expressao;
    }

    
    public void novaExpressao()
    {
        expressao = "";
        tipos = "";
    }
    
    public void analisaTiposDaExpressao(String tipoEsperado) throws ExcecaoSemantico
    {    
        StringTokenizer tok = new StringTokenizer(transformacaoPosfixa(tipos)," ");
        String aux = "",operando1="",operando2="",expressaoAux="";
        String pos = transformacaoPosfixa(expressao);
        while(tok.hasMoreTokens())
        {
            aux = tok.nextToken();
            if(  aux.equals("inteiro")
              || aux.equals("booleano"))
            {
                expressaoAux +=operando2+" ";
                operando2 = operando1;
                operando1 = aux;

            }
            else if(aux.equals("@"))//sinal + ou -
            {
                if(operando2.equals(""))
                {
                    if(!operando1.equals("inteiro"))
                    {
                        throw new ExcecaoSemantico("Sinal atribuido a tipo booleano", linha, coluna);
                    }
                }
                else if (operando2.equals("inteiro"))
                {
                    expressaoAux+=operando1+" ";
                }
                else
                {
                    throw new ExcecaoSemantico("Sinal atribuido a tipo booleano", linha, coluna);
                }
                operando1 = "";
                operando2 = "";
                expressaoAux+="inteiro ";
                while(tok.hasMoreTokens())
                {
                    expressaoAux+=tok.nextToken()+" ";
                }
                tok = new StringTokenizer(expressaoAux," ");
                expressaoAux = "";       
            }
            else if(aux.equals("nao"))
            {   
                if(operando2.equals(""))
                {
                    if(!operando1.equals("booleano"))
                    {
                        throw new ExcecaoSemantico("Operador \"nao\" atribuido a tipo inteiro", linha, coluna);
                    }

                }
                else if (operando2.equals("booleano"))
                {
                    expressaoAux+=operando1+" ";
                }
                else
                {
                    throw new ExcecaoSemantico("Operador \"nao\" atribuido a tipo inteiro", linha, coluna);
                }
                operando1 = "";
                operando2 = "";
                expressaoAux+="booleano ";
                while(tok.hasMoreTokens())
                {
                    expressaoAux+=tok.nextToken()+" ";
                }
                tok = new StringTokenizer(expressaoAux," ");
                expressaoAux = "";                
            }
            else if(  aux.equals("ou")
                   || aux.equals("e") )
            {
                if(!operando1.equals(operando2))
                {
                    throw new ExcecaoSemantico("Tipos diferentes utilizados na operação", linha, coluna);
                }
                if(!operando2.equals("booleano"))
                {
                    throw new ExcecaoSemantico("Tipo dos operandos inválido para o operador \""+aux+"\"", linha, coluna);
                }
                else 
                {
                    operando1 = "";
                    operando2 = "";
                    expressaoAux+="booleano ";
                    while(tok.hasMoreTokens())
                    {
                        expressaoAux+=tok.nextToken()+" ";
                    }
                    tok = new StringTokenizer(expressaoAux," ");
                    expressaoAux = "";       
                }
            }
            else if(  aux.equals("+")
                   || aux.equals("-")
                   || aux.equals("*")
                   || aux.equals("/") )
            {
                if(!operando1.equals(operando2))
                {
                    throw new ExcecaoSemantico("Tipos diferentes utilizados na operação", linha, coluna);
                } 
                if(!operando2.equals("inteiro"))
                {
                    throw new ExcecaoSemantico("Tipo dos operandos inválido para o operador \""+aux+"\"", linha, coluna);
                }
                else 
                {
                    operando1 = "";
                    operando2 = "";   
                    expressaoAux+="inteiro ";
                    while(tok.hasMoreTokens())
                    {
                        expressaoAux+=tok.nextToken()+" ";
                    }
                    tok = new StringTokenizer(expressaoAux," ");
                    expressaoAux = "";   
                }
            }
            else if(  aux.equals(">")
                   || aux.equals(">=")
                   || aux.equals("<")
                   || aux.equals("<=")
                   || aux.equals("=")
                   || aux.equals("<>") )
            {
                if(!operando1.equals(operando2))
                {
                    throw new ExcecaoSemantico("Tipos diferentes utilizados na operação", linha, coluna);
                }
                if(!operando2.equals("inteiro"))
                {
                    throw new ExcecaoSemantico("Tipo dos operandos inválido para o operador \""+aux+"\"", linha, coluna);
                }
                else 
                {
                    operando1 = "";
                    operando2 = "";   
                    expressaoAux+="booleano ";
                    while(tok.hasMoreTokens())
                    {
                        expressaoAux+=tok.nextToken()+" ";
                    }
                    tok = new StringTokenizer(expressaoAux," ");
                    expressaoAux = "";  
                }
            }
        }
        
        if(!tipoEsperado.equals(aux.trim()))
        {
            throw new ExcecaoSemantico("Tipo da expressao incompatível",linha,coluna);
        }
        
    }

    private int precedencia(String op)
    {
        if(  op.equals("nao")
          || op.equals("@"))
        {
            return 4;
        }
        else if(  op.equals("*")
                || op.equals("/")
                || op.equals("div"))
        {
            return 3;
        }
        else if(  op.equals("+")
                || op.equals("-"))
        {
            return 2;
        }
        else if(  op.equals("<")
                || op.equals("<=")
                || op.equals(">")
                || op.equals(">=")
                || op.equals("=")
                || op.equals("<>"))
        {
            return 1;
        }
        else if(  op.equals("e")
                || op.equals("ou"))
        {
            return 0;
        }
//        else if(op.equals("("))
//            return 100;
        return -1;
    }
    
    public void geraCodigoExpressao(TabelaDeSimbolos tabela, GeracaoDeCodigo geracao) throws ExcecaoSemantico
    {    
        StringTokenizer tok = new StringTokenizer(transformacaoPosfixa(expressao)," ");
        String aux = "";

        while(tok.hasMoreTokens())
        {
            aux = tok.nextToken();
            if(aux.equals("+"))
            {
                geracao.gera("", "ADD", "", "");
            }
            else if(aux.equals("-"))
            {
                geracao.gera("", "SUB", "", "");
            }
            else if(aux.equals("@"))
            {
                geracao.gera("", "INV", "", "");
            }
            else if(aux.equals("div"))
            {
                geracao.gera("", "DIVI", "", "");
            }
            else if(aux.equals("*"))
            {
                geracao.gera("", "MULT", "", "");
            }
            else if(aux.equals("e"))
            {
                geracao.gera("", "AND", "", "");
            }
            else if(aux.equals("ou"))
            {
                geracao.gera("", "OR", "", "");
            }
            else if(aux.equals(">="))
            {
                geracao.gera("", "CMAQ", "", "");
            }
            else if(aux.equals("<="))
            {
                geracao.gera("", "CMEQ", "", "");
            }
            else if(aux.equals("<>"))
            {
                geracao.gera("", "CDIF", "", "");
            }
            else if(aux.equals("="))
            {
                geracao.gera("", "CEQ", "", "");
            }
            else if(aux.equals(">"))
            {
                geracao.gera("", "CMA", "", "");
            }
            else if(aux.equals("<"))
            {
                geracao.gera("", "CME", "", "");
            }
            else if(aux.equals("nao"))
            {
                geracao.gera("", "NEG", "", "");
            }
            else if(aux.matches("^[0-9]*$"))
            {
                geracao.gera("", "LDC", aux, "");
            }
            else
            {
                if(aux.equals("verdadeiro"))
                {
                    geracao.gera("", "LDC", "1", "");
                }
                else if(aux.equals("falso"))
                {
                    geracao.gera("", "LDC", "0", "");
                }
                else
                {
                    Simbolo auxSimb = tabela.pegaSimbolo(aux);
                    
                    if(auxSimb != null)
                    {
                        if(auxSimb.getRotulo() != 0)
                        {
                            geracao.gera("", "CALL", "L"+String.valueOf(tabela.pegaRotulo(aux)), "");
                            geracao.gera("", "LDV", String.valueOf(tabela.pegaRegiaoMemoria(aux)), "");
                        }
                        else
                        {
                            geracao.gera("", "LDV", String.valueOf(tabela.pegaRegiaoMemoria(aux)), "");
                        }
                    }
                    else
                    {
                        geracao.gera("", "ERRO", String.valueOf(tabela.pegaRegiaoMemoria(aux)), "");
                    }
                }
            }
        }
    }

}
