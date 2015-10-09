/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkcompilador;

/**
 *
 * @author Louiz
 */
public class Sintatico {
    
    
    private static Token token;
    private static Lexico lexico;
    private static Sintatico sintatico;
    private static int linha;
    public static void main(String args[]) throws Exception{
        
        lexico = new Lexico();
        
        //token  = lexico.getToken();
        
        sintatico = new Sintatico();
        linha=0;
        System.out.println("Sintatico Aqui:::::");
        /*
        if(token != null){
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getToken();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getToken();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        */
        
        token  = lexico.getToken();
        while(token == null){
          token  = lexico.getToken();  
            
        }
        if(token.simbolo == "sprograma"){
            
            token  = lexico.getToken();
            
            if(token.simbolo == "sidentificador"){
                               
                token  = lexico.getToken();
                
                if("sponto_virgula".equals(token.simbolo)){
                
                    
                    
                    sintatico.Analisa_Bloco();
                    if(token == null){
                        
                        System.out.println("erro : falta ponto final");
                        System.exit(-1);
                    }
                    
                    if(token.simbolo == "sponto"){
                        
                        if(lexico.checkEOF() == true || lexico.GetComment() == 1){
                            
                            System.out.println("Compilado com sucesso");
                        }
                        else{
                            System.out.println("Compilado com sucesso");
                            //System.out.println("ERRO 1!!!");
                           // System.exit(-1);
                            
                        }
                            
                        
                    }
                    else{
                        System.out.println("erro 02: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                        System.exit(-1);
                    }
                }
                
                else{
                    System.out.println(token.simbolo);
                    System.out.println("erro 03: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
            }
            
            else{
                System.out.println(token.simbolo);
                System.out.println("erro 04: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
        
        else{
            System.out.println(token.simbolo);
            System.out.println("erro 05: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
            System.exit(-1);
        }
        
        }
    
    public void Analisa_Bloco() throws Exception{
        
        token  = lexico.getToken();
        sintatico.Analisa_Et_Variaveis();
        sintatico.Analisa_Subrotinas();
        sintatico.Analisa_Comandos();
    }
    
    public void Analisa_Et_Variaveis() throws Exception{
        
        if(token.simbolo == "svar"){
            
            token  = lexico.getToken();
            if(token.simbolo == "sidentificador"){
                
                while(token.simbolo == "sidentificador"){
                
                    sintatico.Analisa_Variaveis();
                    if(token.simbolo=="sponto_virgula")
                        token  = lexico.getToken();
                    else{
                        System.out.println("erro 06: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                        System.exit(-1);
                    }
                }
                       
            }
            else{
                System.out.println("erro 07: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
    }
    
    public void Analisa_Variaveis() throws Exception{
        do{
            if(token.simbolo == "sidentificador"){
                token  = lexico.getToken();
                
                if(token.simbolo == "svirgula" || token.simbolo == "sdoispontos"){
                    if(token.simbolo == "svirgula"){
                        token  = lexico.getToken();
                        
                        if(token.simbolo == "sdoispontos"){
                            System.out.println("erro 08: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                            System.exit(-1);
                        }
                    }
                    else{
                       // System.out.println("ERRO 9!!!");
                        //System.exit(-1);
                        
                    }
                }
                else{
                    System.out.println("erro 09: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
            }
            else{
                System.out.println("erro 11: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
        while(token.simbolo != "sdoispontos");
               token  = lexico.getToken();
               sintatico.Analisa_Tipo();
    }
    
    
    
    public void Analisa_Tipo() throws Exception{
        if(token.simbolo != "sinteiro" && token.simbolo != "sbooleano"){
            System.out.println("erro 12: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
            System.exit(-1);
        }
        
        token  = lexico.getToken();
    }
    
    
    
    
    public void Analisa_Comandos() throws Exception{
        
        if(token.simbolo == "sinicio"){
            token  = lexico.getToken();
            sintatico.Analisa_Comando_Simples();
            while(token.simbolo != "sfim"){
                if(token.simbolo == "sponto_virgula"){
                    token  = lexico.getToken();
                    if(token.simbolo != "sfim"){
                        sintatico.Analisa_Comando_Simples();
                    }
                }
                else{
                    System.out.println("erro 13: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
            }
            token  = lexico.getToken();
        }
        else{
            System.out.println(""+ token.simbolo);
            System.out.println("erro 14: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
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
        token  = lexico.getToken();
        if(token.simbolo == "satribuicao")
            sintatico.Analisa_Atribuicao();
        else
            sintatico.Chamada_Procedimento();
    }
    
    
    
    public void Analisa_Leia() throws Exception{
        token  = lexico.getToken();
        if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getToken();
            if(token.simbolo == "sidentificador"){
                
                token  = lexico.getToken();
                if(token.simbolo == "sfecha_parenteses")
                    token  = lexico.getToken();
                else{
                    System.out.println("erro 15: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("erro 16: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("erro 17: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);            
        }
    }
    
    
    
    public void Analisa_Escreva() throws Exception{
        token  = lexico.getToken();
        if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getToken();
            if(token.simbolo == "sidentificador"){
                
                token  = lexico.getToken();
                if(token.simbolo == "sfecha_parenteses")
                    token  = lexico.getToken();
                else{
                    System.out.println("erro 18: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
                
            }
            else{
                    System.out.println("erro 19: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
            }
        }
        else{
                    System.out.println("erro 20: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);            
        }
    }
     
    
    public void Analisa_Enquanto() throws Exception{
        
        token  = lexico.getToken();
        sintatico.Analisa_Expressao();
        if(token.simbolo == "sfaca"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Comando_Simples();
        }
        else{
            System.out.println("erro 21: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
            System.exit(-1);
        }
        
    }
    
    
    
    public void Analisa_Se() throws Exception{
        
        token  = lexico.getToken();
        Analisa_Expressao();
        if(token.simbolo == "sentao"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Comando_Simples();
            if(token.simbolo == "ssenao"){
                
                token  = lexico.getToken();
                sintatico.Analisa_Comando_Simples();
                
            }
        }
        else{
            System.out.println("erro 22: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
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
                token  = lexico.getToken();
            else{
                System.out.println("erro 23: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
    }
    
    
    
    public void Analisa_Declaracao_Procedimento() throws Exception{
        
        token  = lexico.getToken();
        if(token.simbolo == "sidentificador"){
            
            token  = lexico.getToken();
            if(token.simbolo == "sponto_virgula")
                sintatico.Analisa_Bloco();
            else{
                System.out.println("erro 24: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
        else{
            System.out.println("erro 25: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Declaracao_Funcao() throws Exception{
        
        token  = lexico.getToken();
        if(token.simbolo == "sidentificador"){
            
            token  = lexico.getToken();
            if(token.simbolo == "sdoispontos"){
                
                token  = lexico.getToken();
                if(token.simbolo == "sinteiro" || token.simbolo == "sboolean"){
                    
                    token  = lexico.getToken();
                    if(token.simbolo == "sponto_virgula"){
                        sintatico.Analisa_Bloco();
                    }
                    else{
                        System.out.println("ERRO ponto e virgula!!!");
                        System.exit(-1);
                    }
                }
                else{
                    System.out.println("erro 26: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                    System.exit(-1);
                }
            }
            else{
                System.out.println("erro 27: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
        else{
            System.out.println("erro 28: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
            System.exit(-1);
        }
    }
    
    
    
    public void Analisa_Expressao() throws Exception{
        
        sintatico.Analisa_Expressao_Simples();
        if(token.simbolo == "smaior" || token.simbolo == "smaiorig" || token.simbolo == "smenor" || token.simbolo == "smenorig" || token.simbolo == "sdif" || token.simbolo == "sig"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Expressao_Simples();
        }
    }
    
    
    
    public void Analisa_Expressao_Simples() throws Exception{
        
        if(token.simbolo == "smais" || token.simbolo == "smenos"){
            
            token  = lexico.getToken();
            
        }
        sintatico.Analisa_Termo();
        while(token.simbolo == "smais" || token.simbolo == "smenos" || token.simbolo == "sou"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Termo();
        }
    }
    
    
    
    public void Analisa_Termo() throws Exception{
        
        sintatico.Analisa_Fator();
        
        while(token.simbolo == "smult" || token.simbolo == "sdiv" || token.simbolo == "se"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Fator();
        }
    }
    
    
    
    public void Analisa_Fator() throws Exception{
        
        if(token.simbolo == "sidentificador")
            sintatico.Analisa_Chamada_Funcao();
        
        else if(token.simbolo == "snumero")
            token  = lexico.getToken();
        
        else if(token.simbolo == "snao"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Fator();
        }
        
        else if(token.simbolo == "sabre_parenteses"){
            
            token  = lexico.getToken();
            sintatico.Analisa_Expressao();
            if(token.simbolo == "sfecha_parenteses")
                token  = lexico.getToken();
            else{
                System.out.println("erro 29: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +   (linha + 1));
                System.exit(-1);
            }
        }
        
        else if(token.lexema == "verdadeiro" || token.lexema == "falso"){
            token  = lexico.getToken();
        }
        else{
           System.out.println("erro 30: " + token.simbolo + " " + token.lexema + " palavra faltando/replicada, Linha:" +    (linha + 1));
            System.exit(-1);
        }
    }

    //Metodos a serem criados
    
    private void Analisa_Chamada_Funcao() throws Exception {
        token = lexico.getToken();
    }

    private void Analisa_Atribuicao() throws Exception {
      
        token = lexico.getToken();
        //INICIA NOVA ANALISE DO SEMANTICO 
        //semantico.novaExpressao();
        sintatico.Analisa_Expressao();
    
    
    }

    private void Chamada_Procedimento() {
        
    }
    
    void SomaLinha(){
        linha++;
    }
    

}
