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
    private static Sintatico sintatico;
    
    public static void main(String args[]) throws Exception{
        
        lexico = new Lexico();
        
        //token  = lexico.getTokens();
        
        sintatico = new Sintatico();
        
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
            
            if(token.simbolo == "sidentificador"){
                               
                token  = lexico.getTokens();
                
                if(token.simbolo == "sponto_virgula"){
                
                    
                    
                    sintatico.Analisa_Bloco();
                    
                    if(token.simbolo == "sponto"){
                        
                        if(lexico.checkEOF() == true || lexico.GetComment() == 1){
                            
                            System.out.println("SUCEEEEEESSO !!!");
                        }
                        else{
                            System.out.println("ERRO 1!!!");
                            System.exit(-1);
                            
                        }
                            
                        
                    }
                    
                    else{
                        System.out.println("ERRO 2!!!");
                        System.exit(-1);
                    }
                }
                
                else{
                    System.out.println(token.simbolo);
                    System.out.println("ERRO 3!!!");
                    System.exit(-1);
                }
            }
            
            else{
                System.out.println(token.simbolo);
                System.out.println("ERRO 4!!!");
                System.exit(-1);
            }
        }
        
        else{
            System.out.println(token.simbolo);
            System.out.println("ERRO 5!!!");
            System.exit(-1);
        }
        
        }
    
    public void Analisa_Bloco() throws Exception{
        
        token  = lexico.getTokens();
        sintatico.Analisa_Et_Variaveis();
        sintatico.Analisa_Subrotinas();
        sintatico.Analisa_Comandos();
    }
    
    public void Analisa_Et_Variaveis() throws Exception{
        
        if(token.simbolo == "svar"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sidentificador"){
                
                while(token.simbolo == "sidentificador"){
                
                    sintatico.Analisa_Variaveis();
                    if(token.simbolo=="sponto_virgula")
                        token  = lexico.getTokens();
                    else{
                        System.out.println("ERRO 6!!");
                        System.exit(-1);
                    }
                }
                       
            }
            else{
                System.out.println("ERRO 7!!!");
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
                            System.out.println("ERRO 8!!!");
                            System.exit(-1);
                        }
                    }
                    else{
                        System.out.println("ERRO 9!!!");
                        System.exit(-1);
                    }
                }
                else{
                    System.out.println("ERRO 10!!!");
                    System.exit(-1);
                }
            }
            else{
                System.out.println("ERRO 11!!!");
                System.exit(-1);
            }
        }
        while(token.simbolo == "sdoispontos");
               token  = lexico.getTokens();
               sintatico.Analisa_Tipo();
    }
    
    
    
    public void Analisa_Tipo() throws Exception{
        if(token.simbolo != "sinteiro" && token.simbolo != "sbooelano"){
            System.out.println("ERRO 12!!!");
            System.exit(-1);
        }
        
        token  = lexico.getTokens();
    }
    
    
    
    
    public void Analisa_Comandos() throws Exception{
        
        if(token.simbolo == "sinicio"){
            token  = lexico.getTokens();
            sintatico.Analisa_Comando_Simples();
            while(token.simbolo != "sfim"){
                if(token.simbolo == "sponto_virgula"){
                    token  = lexico.getTokens();
                    if(token.simbolo != "sfim"){
                        sintatico.Analisa_Comando_Simples();
                    }
                }
                else{
                    System.out.println("ERRO 13!!!");
                    System.exit(-1);
                }
            }
            token  = lexico.getTokens();
        }
        else{
            System.out.println(""+ token.simbolo);
            System.out.println("ERRO 14!!!");
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Comando_Simples() throws Exception{
        if(token.simbolo == "sidentificador")
            sintatico.Analisa_Atrib_ChProcedimento();
        else if(token.simbolo == "sse")
            sintatico.Analisa_Se();
        else if(token.simbolo == "senquanto")
            sintatico.Analisa_Enquanto();
        else if(token.simbolo == "sleia")
            sintatico.Analisa_Leia();
        else if(token.simbolo == "sescreva")
            sintatico.Analisa_Escreva();
        else
            sintatico.Analisa_Comandos();
       
    }
    
    
    
    public void Analisa_Atrib_ChProcedimento() throws Exception{
        token  = lexico.getTokens();
        if(token.simbolo == "satribuicao")
            sintatico.Analisa_Atribuicao();
        else
            sintatico.Chamada_Procedimento();
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
                    System.out.println("ERRO 15!!!");
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("ERRO 16!!!");
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("ERRO 17!!!");
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
                    System.out.println("ERRO 18!!!");
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("ERRO 19!!!");
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("ERRO 20!!!");
                    System.exit(-1);            
        }
    }
     
    
    public void Analisa_Enquanto() throws Exception{
        
        token  = lexico.getTokens();
        sintatico.Analisa_Expressao();
        if(token.simbolo == "sfaca"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Comando_Simples();
        }
        else{
            System.out.println("ERRO 21!!!");
            System.exit(-1);
        }
        
    }
    
    
    
    public void Analisa_Se() throws Exception{
        
        token  = lexico.getTokens();
        Analisa_Expressao();
        if(token.simbolo == "sentao"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Comando_Simples();
            if(token.simbolo == "ssenao"){
                
                token  = lexico.getTokens();
                sintatico.Analisa_Comando_Simples();
                
            }
        }
        else{
            System.out.println("ERRO 22!!!");
            System.exit(-1);
        }
    }
    
    
    
    
    public void Analisa_Subrotinas() throws Exception{
        
        while(token.simbolo == "sprocedimento" || token.simbolo == "sfuncao"){
            
            if(token.simbolo == "sprocedimento")
                sintatico.Analisa_Declaracao_Procedimento();
            else
                sintatico.Analisa_Declaracao_Funcao();
            if(token.simbolo == "sponto_virgula")
                token  = lexico.getTokens();
            else{
                System.out.println("ERRO 23!!!");
                System.exit(-1);
            }
        }
    }
    
    
    
    public void Analisa_Declaracao_Procedimento() throws Exception{
        
        token  = lexico.getTokens();
        if(token.simbolo == "sidentificador"){
            
            token  = lexico.getTokens();
            if(token.simbolo == "sponto_virgula")
                sintatico.Analisa_Bloco();
            else{
                System.out.println("ERRO 24!!!");
                System.exit(-1);
            }
        }
        else{
            System.out.println("ERRO 25!!!");
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
                        sintatico.Analisa_Bloco();
                }
                else{
                    System.out.println("ERRO 26!!!");
                    System.exit(-1);
                }
            }
            else{
                System.out.println("ERRO 27!!!");
                System.exit(-1);
            }
        }
        else{
            System.out.println("ERRO 28!!!");
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Expressao() throws Exception{
        
        sintatico.Analisa_Expressao_Simples();
        if(token.simbolo == "smaior" || token.simbolo == "smaiorig" || token.simbolo == "smenor" || token.simbolo == "smenorig" || token.simbolo == "sdif"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Expressao_Simples();
        }
    }
    
    
    
    public void Analisa_Expressao_Simples() throws Exception{
        
        if(token.simbolo == "smais" || token.simbolo == "smenos"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Termo();
        }
        
        while(token.simbolo == "smais" || token.simbolo == "smenos" || token.simbolo == "sou"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Termo();
        }
    }
    
    
    
    public void Analisa_Termo() throws Exception{
        
        sintatico.Analisa_Fator();
        
        while(token.simbolo == "smult" || token.simbolo == "sdiv" || token.simbolo == "se"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Fator();
        }
    }
    
    
    
    public void Analisa_Fator() throws Exception{
        
        if(token.simbolo == "sidentificador")
            sintatico.Analisa_Chamada_Funcao();
        
        else if(token.simbolo == "snumero")
            token  = lexico.getTokens();
        
        else if(token.simbolo == "snao"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Fator();
        }
        
        else if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getTokens();
            sintatico.Analisa_Expressao();
            if(token.simbolo == "sfecha_parenteses")
                token  = lexico.getTokens();
            else{
                System.out.println("ERRO 29!!!");
                System.exit(-1);
            }
        }
        
        else if(token.lexema == "verdadeiro" || token.lexema == "falso")
            token  = lexico.getTokens();
        
        else{
            System.out.println("ERRO 30!!!");
            System.exit(-1);
        }
    }

    //Metodos a serem criados
    
    private void Analisa_Chamada_Funcao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void Analisa_Atribuicao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void Chamada_Procedimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    }
