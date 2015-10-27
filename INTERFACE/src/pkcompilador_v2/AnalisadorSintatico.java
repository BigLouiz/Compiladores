/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sintatico;

import geracao.GeracaoDeCodigo;
import java.io.FileNotFoundException;
import java.io.IOException;
import lexico.AnalisadorLexico;
import lexico.ExcecaoLexico;
import lexico.Token;
import semantico.AnalisadorSemantico;
import semantico.ExcecaoSemantico;

/**
 *
 * @author Leonardo
 */
public class AnalisadorSintatico {
    
    private AnalisadorLexico lexico;
    private Token token;
    private TabelaDeSimbolos tabela;
    private AnalisadorSemantico semantico;
    private GeracaoDeCodigo geracao;
    private int rotulo;
    
    //GERACAO DE CODIGO
    private int enderecoBase;
    //GERACAO DE CODIGO
    
    public AnalisadorSintatico(AnalisadorLexico lex)
    {
        lexico = lex;
        tabela = new TabelaDeSimbolos();
        semantico = new AnalisadorSemantico(tabela);
        geracao = new GeracaoDeCodigo();
        
        //GERACAO DE CODIGO
        enderecoBase = 0;
        //GERACAO DE CODIGO

    }
    
    public void analisa() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico, FileNotFoundException, IOException
    {
        /*verifica se a sintaxe: 
         * 
         * programa <identificador>;
         * <bloco>.
         * 
         * está correta
         */
        rotulo = 1;
        
        token = lexico.proximoToken();
        
        if(token.simboloEquals("sprograma") && token != null)
        {
            token = lexico.proximoToken();
            if(token.simboloEquals("sidentificador") && token != null)
            {
                tabela.insere(token.getLexema(), "nomedeprograma",true);
                
                token = lexico.proximoToken();
                
                if(token.simboloEquals("sponto_virgula") && token != null)
                {
                    //GERACAO DE CODIGO
                    geracao.gera("", "START", "", "");
                    
                    
                    int quantidade;
                    //GERACAO DE CODIGO
                    
                    quantidade = analisaBloco();
                    if(token != null && token.simboloEquals("sponto"))
                    {
                        if(lexico.temToken())
                        {

                            throw new ExcecaoSintatico("Programa já terminado. Conteúdo não reconhecido", token.getLinha(), token.getColuna());//ARRUMAR
                        }
                        else
                        {
                            System.out.println("OK");
                            
                            
                            
                            //GERACAO DE CODIGO TESTE
                            
                            if(quantidade > 0)
                            {
                                enderecoBase = enderecoBase - quantidade;
                                geracao.gera("", "DALLOC", String.valueOf(enderecoBase), String.valueOf(quantidade));
                            }
                            
                            //enderecoBase--;
                            //geracao.gera("", "DALLOC", String.valueOf(enderecoBase), "1");
                            
                            geracao.gera("", "HLT", "", "");
                            
                            geracao.geraOBJ();
                            
                            System.out.println(geracao.getCodigo());
                            //GERACAO DE CODIGO TESTE
                        }
                    }
                    else {
                        throw new ExcecaoSintatico(". esperado", token.getLinha(), token.getColuna());
                    }
                }
                else {
                    throw new ExcecaoSintatico("; esperado", token.getLinha(), token.getColuna());
                }
            }
            else {
                throw new ExcecaoSintatico("Nome do programa esperado", token.getLinha(), token.getColuna());
            }
        }
        else {
            throw new ExcecaoSintatico("Identificador programa esperado", token.getLinha(), token.getColuna());
        }
    }

    private int analisaBloco() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        //geracao de codigo
        int quantidade = 0;
        //geracao de codigo
        
        token = lexico.proximoToken();

        quantidade = analisaEtVariaveis();
        quantidade = quantidade + analisaSubrotinas();
        analisaComandos();
        
        
        //geracao de codigo
        return quantidade;
        //geracao de codigo
        
        
        /*
        token = lexico.proximoToken();
        
        if(token != null)
        {
            throw new ExcecaoSintatico("Programa terminado", 0, 0);
        }
                 */   
    }

    private int analisaEtVariaveis() throws ExcecaoSintatico, ExcecaoLexico
    {
        //GERACAO DE CODIGO
        int quantidade = 0;
        //GERACAO DE CODIGO
        
        if(token.simboloEquals("svar") && token != null)
        {
            token = lexico.proximoToken();
            
            if(token.simboloEquals("sidentificador") && token != null)
            {
                while(token.simboloEquals("sidentificador"))
                {
                    quantidade = quantidade + analisaVariaveis();

                    if(token.simboloEquals("sponto_virgula"))
                    {
                        token = lexico.proximoToken();
                    }
                    else
                    {
                        throw new ExcecaoSintatico("; esperado", token.getLinha(), token.getColuna());
                    }
                }
            }
            else
            {
                throw new ExcecaoSintatico("Identificador", token.getLinha(), token.getColuna());                     // ?
            }
        }
        
        //GERACAO DE CODIGO
        return quantidade;
        //GERACAO DE CODIGO
        
    }

    // Método que verifica as variáveis até encontrar ";".
    private int analisaVariaveis() throws ExcecaoSintatico, ExcecaoLexico
    {
        
        int quantidade = 0;
        
        do
        {
            if(token.simboloEquals("sidentificador") && token != null)
            {
                if(!tabela.temDuplicidade(token.getLexema()))
                {
                    tabela.insere(token.getLexema(), "variavel", enderecoBase+quantidade);
                    //inserir na tabela o endereco da variavel = enderecoBase+quantidade
                    
                    quantidade ++;
                }
                else
                {
                    throw new ExcecaoSintatico("Variável já existente.", token.getLinha(), token.getColuna());
                }
                
                token = lexico.proximoToken();
                    
                if(token.simboloEquals("svirgula") || (token.simboloEquals("sdoispontos") && token != null))
                {
                    if(token.simboloEquals("svirgula"))
                    {
                        token = lexico.proximoToken();
                            
                        if(token.simboloEquals("sdoispontos") && token != null)
                        {
                            throw new ExcecaoSintatico("Variável esperada.", token.getLinha(), token.getColuna());
                        }
                    }
                }
                else
                {
                    throw new ExcecaoSintatico(", ou : esperado", token.getLinha(), token.getColuna());
                }
            }
            else
            {
                throw new ExcecaoSintatico("Identificador esperado..", token.getLinha(), token.getColuna());
            }
        }
        while(!token.simboloEquals("sdoispontos"));
        
        //geracao de codigo
        geracao.gera("", "ALLOC", String.valueOf(enderecoBase), String.valueOf(quantidade));
        
        enderecoBase = enderecoBase + quantidade;
        //geracao de codigo
        
        
        token = lexico.proximoToken();
        
        analisaTipo();
        
        //GERACAO DE CODIGO
        return quantidade;
        //GERACAO DE CODIGO
    }
    
    // Método que verifica o tipo da variável.
    private void analisaTipo() throws ExcecaoSintatico, ExcecaoLexico
    {
        if(!token.simboloEquals("sinteiro") && !token.simboloEquals("sbooleano"))
        {
            throw new ExcecaoSintatico("Tipo Inteiro ou Booleano esperado.", token.getLinha(), token.getColuna());
        }
        else
        {
            tabela.colocaTipo(token.getLexema());
            //CHECAR
        }
        
        token = lexico.proximoToken();
    }
    
    private void analisaComandos() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        if(token.simboloEquals("sinicio") && token != null)
        {
            token = lexico.proximoToken();
            
            analisaComandoSimples();
            
            while(!token.simboloEquals("sfim") && token != null)
            {
                if(token.simboloEquals("sponto_virgula")) //Verificar: algoritmo diferente da linguagem
                {
                    token = lexico.proximoToken();
                    
                    if(!token.simboloEquals("sfim") && token != null)
                    {
                        analisaComandoSimples();
                    }
                }
                else
                {
                    throw new ExcecaoSintatico("; esperado ou expressão invalida.", token.getLinha(), token.getColuna());
                }
            }
            
            token = lexico.proximoToken();

        }
        else
        {
            throw new ExcecaoSintatico("Inicio esperado.", token.getLinha(), token.getColuna());                                      // ?
        }
    }
    
    private void analisaComandoSimples() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        if(token.simboloEquals("sidentificador"))
        {
            analisaAtribChprocedimento();
        }
        else if(token.simboloEquals("sse"))
        {
            analisaSe();            
        }
        else if(token.simboloEquals("senquanto"))
        {
            analisaEnquanto();
        }
        else if(token.simboloEquals("sleia"))
        {
            analisaLeia();
        }
        else if(token.simboloEquals("sescreva"))
        {
            analisaEscreva();
        }
        else
        {
            analisaComandos();
        }
    }
    
    private void analisaAtribChprocedimento() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        /*Semantico*/
        /*Verifica se é variavel e se ja foi declarada*/
        Token aux;
        
        if(!tabela.verificaDeclaracao(token.getLexema()))
        {
            throw new ExcecaoSintatico("Identificador não existente.", token.getLinha(), token.getColuna());
        }
        
        aux = token;
        token = lexico.proximoToken();
        
        if(token.simboloEquals("satribuicao") && token != null)
        {
            analisaAtribuicao(aux);
        }
        else
        {
            analisaChamadaProcedimento(aux);
        }
    }
    
    private void analisaLeia() throws ExcecaoSintatico, ExcecaoLexico
    {
        token = lexico.proximoToken();
        
        if(token.simboloEquals("sabre_parenteses") && token != null)
        {
            token = lexico.proximoToken();
            
            if(token.simboloEquals("sidentificador") && token != null)
            {
                //CHECAR
                if(tabela.verificaDeclaracao(token.getLexema()) && !tabela.temFuncaoComLexema(token.getLexema()))
                {
                    
                    //geracaodecodigo
                    geracao.gera("", "RD", "", "");
                    geracao.gera("", "STR", String.valueOf(tabela.pegaRegiaoMemoria(token.getLexema())), "");
                    //geracaodecodigo
                    
                    if(tabela.verificaSeInteiro(token.getLexema()))
                    {
                        token = lexico.proximoToken();
                    
                        if(token.simboloEquals("sfecha_parenteses") && token != null)
                        {
                            token = lexico.proximoToken();
                        }
                        else
                        {
                            throw new ExcecaoSintatico(") esperado.", token.getLinha(), token.getColuna());
                        }
                    }
                    else
                    {
                        throw new ExcecaoSintatico("Variável a ser lida não é inteiro.", token.getLinha(), token.getColuna());
                    }
                }
                else
                {
                    throw new ExcecaoSintatico("Variável não existente.", token.getLinha(), token.getColuna());
                }
                
            }
            else
            {
                throw new ExcecaoSintatico("Identificador esperado.", token.getLinha(), token.getColuna());
            }
        }
        else
        {
            throw new ExcecaoSintatico("( esperado.", token.getLinha(), token.getColuna());
        }
    }
    
    private void analisaEscreva() throws ExcecaoSintatico, ExcecaoLexico
    {
        token = lexico.proximoToken();
        
        if(token.simboloEquals("sabre_parenteses") && token != null)
        {
            token = lexico.proximoToken();
            
            if(token.simboloEquals("sidentificador") && token != null)
            {
                //CHECAR
                if(tabela.verificaDeclaracao(token.getLexema()))
                {
                    
                    //geracaodecodigo
                    if(tabela.temFuncaoComLexema(token.getLexema()))
                    {
                        geracao.gera("", "CALL", "L"+String.valueOf(tabela.pegaRotulo(token.getLexema())), "");
                        //geracao.gera("", "LDV", String.valueOf(tabela.pegaRegiaoMemoria(token.getLexema())), "");
                    }
                    //geracaodecodigo
                    
                    //geracaodecodigo
                    geracao.gera("", "LDV", String.valueOf(tabela.pegaRegiaoMemoria(token.getLexema())), "");
                    geracao.gera("", "PRN", "", "");
                    //geracaodecodigo
                    
                    if(tabela.verificaSeInteiro(token.getLexema()))
                    {
                        token = lexico.proximoToken();
                    
                        if(token.simboloEquals("sfecha_parenteses") && token != null)
                        {
                            token = lexico.proximoToken();
                        }
                        else
                        {
                            throw new ExcecaoSintatico(") esperado.", token.getLinha(), token.getColuna());
                        }
                    }
                    else
                    {
                        throw new ExcecaoSintatico("Variável a ser escrita não é inteiro.", token.getLinha(), token.getColuna());
                    }
                }
                else
                {
                    throw new ExcecaoSintatico("Variável \""+token.getLexema()+"\" não declarada.", token.getLinha(), token.getColuna());
                }
                
            }
            else
            {
                throw new ExcecaoSintatico("Identificador esperado.", token.getLinha(), token.getColuna());
            }
        }
        else
        {
            throw new ExcecaoSintatico("( esperado.", token.getLinha(), token.getColuna());
        }
    }
    
    private void analisaEnquanto() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        //GERACAO DE CODIGO
        int auxrot1, auxrot2;
        auxrot1 = rotulo;
        geracao.gera("L"+String.valueOf(rotulo), "NULL", "", "");
        rotulo = rotulo + 1;
        //GERACAO DE CODIGO
        
        token = lexico.proximoToken();
        
        //INICIA NOVA ANALISE DO SEMANTICO
        semantico.novaExpressao();
        analisaExpressao();
        semantico.analisaTiposDaExpressao("booleano");

        //GERA CODIGO BASEADO NA EXPRESSAO DADA PELO SEMANTICO
        
        if(token.simboloEquals("sfaca") && token != null)
        {
            //GERACAO DE CODIGO
            auxrot2 = rotulo;
            
            semantico.geraCodigoExpressao(tabela, geracao);
            geracao.gera("", "JMPF", "L"+String.valueOf(rotulo), "");
            
            rotulo = rotulo + 1;
            //GERACAO DE CODIGO
            
            token = lexico.proximoToken();
            
            analisaComandoSimples();
            
            //GERACAO DE CODIGO
            geracao.gera("", "JMP", "L"+String.valueOf(auxrot1), "");
            geracao.gera("L"+String.valueOf(auxrot2), "NULL", "", "");
            //GERACAO DE CODIGO
            
        }
        else
        {
            throw new ExcecaoSintatico("Simbolo: "+token.getLexema()+" invalido. faca esperado.", token.getLinha(), token.getColuna());
        }
    }
    
    private void analisaSe() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        
        token = lexico.proximoToken();
        
        //INICIA NOVA ANALISE DO SEMANTICO
        semantico.novaExpressao();
        analisaExpressao();
        semantico.analisaTiposDaExpressao("booleano");
        
        semantico.geraCodigoExpressao(tabela, geracao);
        geracao.gera("", "JMPF", "L"+String.valueOf(rotulo), "");
        
        int auxrot1 = rotulo;
        rotulo = rotulo + 1;
        
        //GERA CODIGO BASEADO NA EXPRESSAO DADA PELO SEMANTICO         
        if(token.simboloEquals("sentao") && token != null)
        {
            token = lexico.proximoToken();
            
            analisaComandoSimples();
            
            if(token.simboloEquals("ssenao") && token != null)
            {
                //geracaodecodigo
                int auxrot2 = rotulo;
                rotulo = rotulo + 1;
                
                geracao.gera("", "JMP", "L"+String.valueOf(auxrot2), "");

                geracao.gera("L"+String.valueOf(auxrot1), "NULL", "", "");
                //geracaodecodigo
                
                token = lexico.proximoToken();
                
                analisaComandoSimples();
                
                //geracaodecodigo
                geracao.gera("L"+String.valueOf(auxrot2), "NULL", "", "");
                //geracaodecodigo
            }
            else
            {
                //geracaodecodigo
                geracao.gera("L"+String.valueOf(auxrot1), "NULL", "", "");
                //geracaodecodigo
            }
            
        }
        else
        {
            throw new ExcecaoSintatico("Então esperado.", token.getLinha(), token.getColuna());
        }
    }
        
    private int analisaSubrotinas() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        //GERACAO DE CODIGO
        int quantidade = 0;
        
        int auxrot, flag;
        
        flag = 0;
        auxrot = 0;
        
        int endBaseAux = 0;
        
        if(token.simboloEquals("sprocedimento") || token.simboloEquals("sfuncao"))
        {
            //GERACAO DE CODIGO
            if(token.simboloEquals("sfuncao"))
            {
                endBaseAux = enderecoBase;
                geracao.gera("", "ALLOC", String.valueOf(endBaseAux), "1");
                enderecoBase++;
                
                quantidade = quantidade + 1;
            }
            //GERACAO DE CODIGO
            
            auxrot = rotulo;
            geracao.gera("", "JMP", "L"+String.valueOf(rotulo), "");
            rotulo = rotulo + 1;
            flag = 1;
        }
        //GERACAO DE CODIGO
        
        while(token.simboloEquals("sprocedimento") || token.simboloEquals("sfuncao"))
        {
            if(token.simboloEquals("sprocedimento") && token != null)
            {
                analisaDeclaracaoProcedimento();
            }
            else
            {
                analisaDeclaracaoFuncao(endBaseAux);
            }
            
            if(token.simboloEquals("sponto_virgula") && token != null)
            {
                token = lexico.proximoToken();
            }
            else
            {
                throw new ExcecaoSintatico("Simbolo: "+token.getLexema()+" invalido. ; esperado.", token.getLinha(), token.getColuna());
            }
        }
        
        //GERACAO DE CODIGO
        if(flag == 1)
        {
            geracao.gera("L"+String.valueOf(auxrot), "NULL", "", "");
        }
        //GERACAO DE CODIGO
        
        return quantidade;
    }
    
    private void analisaDeclaracaoProcedimento() throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        token = lexico.proximoToken();
        
        //geracao de codigo
        int quantidade = 0;
        //geracao de codigo
        
        if(token.simboloEquals("sidentificador") && token != null)
        {
            if(!tabela.verificaDeclaracao(token.getLexema()))
            {
                tabela.insere(token.getLexema(),"procedimento",true,rotulo);
                
                //GERACAO DE CODIGO
                geracao.gera("L"+String.valueOf(rotulo), "NULL", "", "");
                rotulo = rotulo + 1;
                //GERACAO DE CODIGO
            
                token = lexico.proximoToken();

                if(token.simboloEquals("sponto_virgula") && token != null)
                {
                    quantidade = analisaBloco();
                }
                else
                {
                    throw new ExcecaoSintatico("; esperado.", token.getLinha(), token.getColuna());
                }
            }
            else throw new ExcecaoSintatico("Procedimento "+token.getLexema()+" já declarado", token.getLinha(), token.getColuna());

        }
        else
        {
            throw new ExcecaoSintatico("Identificador esperado.", token.getLinha(), token.getColuna());
        }
        
        //DESPILHA OU VOLTA NÍVEL
        tabela.desempilha();
        
        //geracao de codigo
        if(quantidade > 0)
        {
            enderecoBase = enderecoBase - quantidade;
            geracao.gera("", "DALLOC", String.valueOf(enderecoBase), String.valueOf(quantidade));
        }
        geracao.gera("", "RETURN", "", "");
        //geracao de codigo
    }
    
    private void analisaDeclaracaoFuncao(int enderecoBaseFuncao) throws ExcecaoSintatico, ExcecaoLexico, ExcecaoSemantico
    {
        //geracao de codigo
        int quantidade = 0;
        //geracao de codigo
        
        token = lexico.proximoToken();
        
        //ETAPA DE GERAÇÃO DE CÓDIGO
        
        if(token.simboloEquals("sidentificador") && token != null)
        {
            if(!tabela.verificaDeclaracao(token.getLexema()))
            {
                tabela.insere(token.getLexema(),"funcao",true,rotulo, enderecoBaseFuncao);
                
                //GERACAO DE CODIGO               
                geracao.gera("L"+String.valueOf(rotulo), "NULL", "", "");
                rotulo = rotulo + 1;
                //GERACAO DE CODIGO

                token = lexico.proximoToken();

                if(token.simboloEquals("sdoispontos") && token != null)
                {

                    token = lexico.proximoToken();

                    if((token.simboloEquals("sinteiro") || token.simboloEquals("sbooleano")) && token != null)
                    {
                        if(token.simboloEquals("sinteiro")){
                            tabela.colocaTipoFuncao("inteiro");
                        }
                        else if(token.simboloEquals("sbooleano")){
                            tabela.colocaTipoFuncao("booleano");
                        }
                        
                        token = lexico.proximoToken();

                        if(token.simboloEquals("sponto_virgula") && token != null)
                        {
                            quantidade = analisaBloco();
                        }

                    }
                    else
                    {
                        throw new ExcecaoSintatico("inteiro ou Booleano esperado.", token.getLinha(), token.getColuna());
                    }

                }
                else
                {
                    throw new ExcecaoSintatico(": esperado.", token.getLinha(), token.getColuna());
                }

            }
            else
            {
                throw new ExcecaoSintatico("Identificador "+token.getLexema()+" ja declarado.", token.getLinha(), token.getColuna());
            }

        }
        else
        {
            throw new ExcecaoSintatico("Identificador esperado.", token.getLinha(), token.getColuna());
        }
        //DESPILHA OU VOLTA NÍVEL ATE A PRIMEIRA MARCA
        tabela.desempilha();
        
        //geracao de codigo
        if(quantidade > 0)
        {
            enderecoBase = enderecoBase - quantidade;
            geracao.gera("", "DALLOC", String.valueOf(enderecoBase), String.valueOf(quantidade));
        }
        geracao.gera("", "RETURN", "", "");
        //geracao de codigo
    }

    private void analisaAtribuicao(Token aux) throws ExcecaoLexico, ExcecaoSintatico, ExcecaoSemantico 
    {
        token = lexico.proximoToken();
        //INICIA NOVA ANALISE DO SEMANTICO 
        semantico.novaExpressao();
        analisaExpressao();
        semantico.analisaTiposDaExpressao(tabela.tipoDoLexema(aux.getLexema()));
        
        semantico.geraCodigoExpressao(tabela, geracao);
        
        geracao.gera("", "STR", String.valueOf(tabela.pegaRegiaoMemoria(aux.getLexema())), "");
        //GERA CODIGO BASEADO NA EXPRESSAO DADA PELO SEMANTICO 
    }

    private void analisaExpressao() throws ExcecaoLexico, ExcecaoSintatico 
    {
        analisaExpressaoSimples();
        
        if(token.simboloEquals("smaior") ||
           token.simboloEquals("smaiorig") ||
           token.simboloEquals("sig") ||
           token.simboloEquals("smenor") ||
           token.simboloEquals("smenorig") ||
           token.simboloEquals("sdif") )
        {
            semantico.insereOperador(token);
            token = lexico.proximoToken();
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws ExcecaoLexico, ExcecaoSintatico 
    {
        if(token.simboloEquals("smais") ||
           token.simboloEquals("smenos") )
        {
            semantico.insereSinal(token);
            token = lexico.proximoToken();      
        }
        
        analisaTermo();
        
        
        while(token.simboloEquals("smais") ||
              token.simboloEquals("sou") ||
              token.simboloEquals("smenos"))
        {
            semantico.insereOperador(token);
            token = lexico.proximoToken();  
            analisaTermo();
        }
    }

    private void analisaTermo() throws ExcecaoLexico, ExcecaoSintatico 
    {
        analisaFator();
        
        while(token.simboloEquals("smult") ||
              token.simboloEquals("sdiv") ||
              token.simboloEquals("se"))
        {
            semantico.insereOperador(token); 
            token = lexico.proximoToken();  
            analisaFator();
        }
    }

    private void analisaFator() throws ExcecaoLexico, ExcecaoSintatico 
    {
        if(token.simboloEquals("sidentificador"))//Variavel ou função
        {

            if(tabela.verificaDeclaracao(token.getLexema()))
            {
                
                if(tabela.temFuncaoComLexema(token.getLexema()))
                {
                    analisaChamadaFuncao();

                }
                else//VERIFICAR SE FALTA BUSCA NA TABELA PARA CHAMADA DE VARIAVEL
                {
                    //BUSCA NA TABELA O TIPO DO IDENTIFICADOR
                    semantico.insereFator(token,tabela.tipoDoLexema(token.getLexema())); 
                    token = lexico.proximoToken();
                }

            }
            else
            {
                throw new ExcecaoSintatico("Identificador "+token.getLexema()+" não decladara.", token.getLinha(), token.getColuna());
            }
            
        }
        else if(token.simboloEquals("snumero"))//numero
        {
            semantico.insereFator(token,"inteiro");
            token = lexico.proximoToken();
        }
        else if(token.simboloEquals("snao"))//nao
        {
            semantico.insereSinal(token); 
            token = lexico.proximoToken();
            analisaFator();
        }
        else if(token.simboloEquals("sabre_parenteses"))//(
        {
            semantico.insereParenteses(token); 
            token = lexico.proximoToken();
            analisaExpressao();
            if(token.simboloEquals("sfecha_parenteses"))//)
            {
                semantico.insereParenteses(token); 
                token = lexico.proximoToken();
            }
            else
            {
                throw new ExcecaoSintatico("Simbolo: "+token.getLexema()+"invalido. ) esperado", token.getLinha(), token.getColuna());
            }
        }
        else if(token.lexemaEquals("verdadeiro") ||
                token.lexemaEquals("falso"))
        {
            semantico.insereFator(token,"booleano"); 
            token = lexico.proximoToken();
        }
        else
        {
            throw new ExcecaoSintatico("Operando "+token.getLexema()+" não aceito", token.getLinha(), token.getColuna());
        }//ARRUMAR
        
    }
    
    private void analisaChamadaProcedimento(Token aux) throws ExcecaoLexico, ExcecaoSintatico 
    {
        //geracao de codigo
        geracao.gera("", "CALL", "L"+String.valueOf(tabela.pegaRotulo(aux.getLexema())), "");
        //geracao de codigo
        
        //if(!token.simboloEquals("sponto_virgula"))
        //{
            //throw new ExcecaoSintatico("; esperado.", token.getLinha(), token.getColuna());
        //}
    }
    
    private void analisaChamadaFuncao() throws ExcecaoLexico, ExcecaoSintatico 
    {
        //geracao de codigo
        //geracao.gera("", "CALL", "L"+String.valueOf(tabela.pegaRotulo(token.getLexema())), "");
        //geracao de codigo
        
        semantico.insereFator(token, tabela.tipoDoLexema(token.getLexema())); //VERIFICAR SE A TABELA RETORNA O TIPO DA FUNCAO CHAMADA
        token = lexico.proximoToken();
    }
    
}