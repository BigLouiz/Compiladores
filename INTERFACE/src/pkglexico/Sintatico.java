/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkglexico;

/**
 *
 * @author Louiz
 */
public class Sintatico {
    
    
    private static Token token;
    private static Lexico lexico;
    
    public static void main(String args[]) throws Exception{
        
        lexico = new Lexico();
        
        token  = lexico.getTokens();
        
        System.out.println("Sintatico Aqui:::::");
        /*
        if(token != null){
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getTokens();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getTokens();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        */
        
        token  = lexico.getTokens();
        
        if(token.simbolo == "sprograma"){
            
            token  = lexico.getTokens();
            
            if(token.simbolo == "sindentificador"){
                
                token  = lexico.getTokens();
                
                if(token.simbolo == "spontovirgula"){
                
                    Analisa_Bloco();
                    
                    if(token.simbolo == "sponto"){
                        
                        if(lexico.checkEOF() == true || lexico.GetComment() == 1){
                            
                            System.out.println("SUCEEEEEESSO !!!");
                        }
                        else{
                            System.out.println("ERRO !!!");
                            System.exit(-1);
                            
                        }
                            
                        
                    }
                    
                    else{
                        System.out.println("ERRO !!!");
                        System.exit(-1);
                    }
                }
                
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
            }
            
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
        
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
        
        }
    
    public void Analisa_Bloco() throws Exception{
        
        token  = lexico.getTokens();
        Analisa_Et_Variaveis();
        Analisa_Subrotinas();
        Analisa_Comandos();
    }
    
    public void Analisa_Et_Variaveis() throws Exception{
        
        if(token.simbolo == "svar"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sidentificador"){
                
                while(token.simbolo == "sidentificador"){
                
                    Analisa_Variaveis();
                    if(token.simbolo=="sponto_virgula")
                        token  = lexico.getTokens();
                    else{
                        System.out.println("ERRO !!");
                        System.exit(-1);
                    }
                }
                       
            }
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
    }
    
    public void Analisa_Variaveis() throws Exception{
        do{
            if(token.simbolo == "sidentificador"){
                token  = lexico.getTokens();
                
                if(token.simbolo == "svirgula" || token.simbolo == "sdoispontos"){
                    if(token.simbolo == "svirgula"){
                        token  = lexico.getTokens();
                        
                        if(token.simbolo == "sdoispontos"){
                            System.out.println("ERRO !!!");
                            System.exit(-1);
                        }
                    }
                    else{
                        System.out.println("ERRO !!!");
                        System.exit(-1);
                    }
                }
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
            }
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
        while(token.simbolo == "sdoispontos");
               token  = lexico.getTokens();
               Analisa_Tipo();
    }
    
    
    
    public void Analisa_Tipo() throws Exception{
        if(token.simbolo != "sinteiro" && token.simbolo != "sbooelano"){
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
        
        token  = lexico.getTokens();
    }
    
    
    
    
    public void Analisa_Comandos() throws Exception{
        
        if(token.simbolo == "sinicio"){
            token  = lexico.getTokens();
            Analisa_Comando_Simples();
            while(token.simbolo != "sfim"){
                if(token.simbolo == "sponto_virgula"){
                    token  = lexico.getTokens();
                    if(token.simbolo != "sfim"){
                        Analisa_Comando_Simples();
                    }
                }
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
            }
            token  = lexico.getTokens();
        }
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Comando_Simples() throws Exception{
        if(token.simbolo == "sidentificador")
            Analisa_Atrib_ChProcedimento();
        else if(token.simbolo == "sse")
            Analisa_Se();
        else if(token.simbolo == "senquanto")
            Analisa_Enquanto();
        else if(token.simbolo == "sleia")
            Analisa_Leia();
        else if(token.simbolo == "sescreva")
            Analisa_Escreva();
        else
            Analisa_Comandos();
       
    }
    
    
    
    public void Analisa_Atrib_ChProcedimento() throws Exception{
        token  = lexico.getTokens();
        if(token.simbolo == "satribuicao")
            Analisa_Atribuicao();
        else
            Chamada_Procedimento();
    }
    
    
    
    public void Analisa_Leia() throws Exception{
        token  = lexico.getTokens();
        if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sidentificador"){
                
                token  = lexico.getTokens();
                if(token.simbolo == "sfecha_parenteses")
                    token  = lexico.getTokens();
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);            
        }
    }
    
    
    
    public void Analisa_Escreva() throws Exception{
        token  = lexico.getTokens();
        if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sidentificador"){
                
                token  = lexico.getTokens();
                if(token.simbolo == "sfecha_parenteses")
                    token  = lexico.getTokens();
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);            
        }
    }
     
    
    public void Analisa_Enquanto() throws Exception{
        
        token  = lexico.getTokens();
        Analisa_Expressao();
        if(token.simbolo == "sfaca"){
            
            token  = lexico.getTokens();
            Analisa_Comando_Simples();
        }
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
        
    }
    
    
    
    public void Analisa_Se() throws Exception{
        
        token  = lexico.getTokens();
        Analisa_Expressao();
        if(token.simbolo == "sentao"){
            
            token  = lexico.getTokens();
            Analisa_Comando_Simples();
            if(token.simbolo == "ssenao"){
                
                token  = lexico.getTokens();
                Analisa_Comando_Simples();
                
            }
        }
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
    }
    
    
    
    
    public void Analisa_Subrotinas() throws Exception{
        
        while(token.simbolo == "sprocedimento" || token.simbolo == "sfuncao"){
            
            if(token.simbolo == "sprocedimento")
                Analisa_Declaracao_Procedimento();
            else
                Analisa_Declaracao_Funcao();
            if(token.simbolo == "sponto_virgula")
                token  = lexico.getTokens();
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
    }
    
    
    
    public void Analisa_Declaracao_Procedimento() throws Exception{
        
        token  = lexico.getTokens();
        if(token.simbolo == "sidentificador"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sponto_virgula")
                Analisa_Bloco();
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Declaracao_Funcao() throws Exception{
        
        token  = lexico.getTokens();
        if(token.simbolo == "sidentificador"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sdoispontos"){
                
                token  = lexico.getTokens();
                if(token.simbolo == "sinteiro" || token.simbolo == "sboolean"){
                    
                    token  = lexico.getTokens();
                    if(token.simbolo == "sponto_virgula")
                        Analisa_Bloco();
                }
                else{
                    System.out.println("ERRO !!!");
                    System.exit(-1);
                }
            }
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Expressao() throws Exception{
        
        Analisa_Expressao_Simples();
        if(token.simbolo == "smaior" || token.simbolo == "smaiorig" || token.simbolo == "smenor" || token.simbolo == "smenorig" || token.simbolo == "sdif"){
            
            token  = lexico.getTokens();
            Analisa_Expressao_Simples();
        }
    }
    
    
    
    public void Analisa_Expressao_Simples() throws Exception{
        
        if(token.simbolo == "smais" || token.simbolo == "smenos"){
            
            token  = lexico.getTokens();
            Analisa_Termo();
        }
        
        while(token.simbolo == "smais" || token.simbolo == "smenos" || token.simbolo == "sou"){
            
            token  = lexico.getTokens();
            Analisa_Termo();
        }
    }
    
    
    
    public void Analisa_Termo() throws Exception{
        
        Analisa_Fator();
        
        while(token.simbolo == "smult" || token.simbolo == "sdiv" || token.simbolo == "se"){
            
            token  = lexico.getTokens();
            Analisa_Fator();
        }
    }
    
    
    
    public void Analisa_Fator() throws Exception{
        
        if(token.simbolo == "sidentificador")
            Analisa_Chamada_Funcao();
        
        else if(token.simbolo == "snumero")
            token  = lexico.getTokens();
        
        else if(token.simbolo == "snao"){
            
            token  = lexico.getTokens();
            Analisa_Fator();
        }
        
        else if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getTokens();
            Analisa_Expressao();
            if(token.simbolo == "sfecha_parenteses")
                token  = lexico.getTokens();
            else{
                System.out.println("ERRO !!!");
                System.exit(-1);
            }
        }
        
        else if(token.lexema == "verdadeiro" || token.lexema == "falso")
            token  = lexico.getTokens();
        
        else{
            System.out.println("ERRO !!!");
            System.exit(-1);
        }
    }
    
    
    
    
    }
