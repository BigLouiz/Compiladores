/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkcompilador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.sql.DriverManager.println;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Louiz
 */


public class Lexico {
    
     private static Character caracter;
     private static ArrayList<Token> token = new ArrayList();
     private static char[] memory;
     private int cont = 0;
   
     static BufferedReader in;
     private  static FileInputStream arquivo;
     private int token_indice = -1;
    
     private Sintatico sintatico;
     private Boolean flag_pontovirgula;
     private int flag=2;
     
     //Analisador Lexical
     
     public Lexico() throws Exception{

         Scanner ler = new Scanner(System.in);
         System.out.printf("Informe o nome de arquivo texto:\n");
         String nome = ler.nextLine(); 
         Scanner file = new Scanner(new File(nome));
         File ww = new File(nome);
         System.out.printf("\nConteúdo do arquivo texto:\n"); 
         int j=0;
         
         sintatico = new Sintatico();
         String str = "";
         try { 
             in = new BufferedReader(new FileReader(nome));  
        char ch; 
        
             try (FileInputStream arq = new FileInputStream(nome)) {
                 arquivo = arq;
                 int r;
                
                 
// lê da segunda até a última linha 
             } 
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
    } 
    //System.out.print(str);
    memory = str.toCharArray();
    
    /*for(int i=0;i<memory.length;i++){
         //System.out.println();
         System.out.print(""+memory[i]);
         
    }*/
    
    
    
    
    //Resetar ponteiro do arquivo
            
    /*int BUFFER_SIZE = 1000;
in.mark(BUFFER_SIZE);
lerCaracter();  // returns the GET
//lerCaracter();  // returns the Host header
in.reset();     // rewinds the stream back to the mark
lerCaracter();  // returns the GET again*/
   //Exibe Tokens
     lerCaracter();
 }
     
 public Token getToken() throws Exception{
     
   Token tk;
   
  
   
   
   
   while(checkEOF() == false){
       
       
       
       while(caracter.equals('{')||caracter.equals(' ') && checkEOF() == false ||  caracter.equals('\n') && checkEOF() == false){
           if(caracter.equals('{')){
               
               flag = 1;
           
               
                 while(!caracter.equals('}')&& checkEOF() == false){
                    
                     lerCaracter();
                     
                 }
                 
                 
                 
                 
               
            }
           
           
           
           
           if(caracter.equals(' ')){
           
               
                 while(caracter.equals(' ')&& checkEOF() == false){
                    lerCaracter();
                 }  
               
            }
           if(caracter.equals('\n')){
               lerCaracter();
               sintatico.SomaLinha();
           }
       }
       
        
       
       if(caracter.equals('}')){
                  
               
               flag=0;
       }
           
       
       if(checkEOF() == false){
           tk = PegaToken();
           while(tk == null){
               
            tk = PegaToken();   
           }
           //Insere na lista
           if(caracter.equals('.')){
           token.add(new Token(".","sponto"));
           }
           return tk;
           
       }
       
       
       //Fecha arquivo.Java nao precisa
        
        
   }
   
   
   /*for(Token t : token){
            System.out.println(t);
           // System.out.println(c.getLexema());
    }*/
         return null;
        
        
        
        
      
    
}

     
     public int GetComment() throws Exception{
         
         return flag;
     }

    public Character lerCaracter()throws Exception{
         //this.caracter = memory[cont];
         int r;
         
         if((r = in.read()) != -1) {
                     
         caracter = (char)r;
         if(caracter.equals('}') && flag==0){
                     System.out.println("erro lexico: fecha parentesis");
                     System.exit(-1);
                   }  
         cont++;
         
         }
         else{
            System.out.println("*****  End of File!!!!  *****");
         }
         
         return caracter;
     }  
     
     //Metodo para verificar o final do arquivo
    protected boolean checkEOF() throws IOException{
       int r;
       int BUFFER_SIZE = 1000;
       in.mark(BUFFER_SIZE);      
       if((r = in.read()) != -1) {
          in.reset(); 
          return false;
       }
       else{
          in.reset(); 
          return true;
       }
        
    }

    
     
     
     protected Token TrataOperadorAritmetico() throws Exception{
         String op = "" + caracter;
         Token tk = null;
         switch(op)
         {
             
         case "+":
            token.add(new Token("+","smais"));
            tk = new Token("+","smais");
            break;   
                 
         case "-":
            token.add(new Token("-","smenos"));
            tk = new Token("-","smenos");
            break;     
               
         case "*":
            token.add(new Token("*","smult"));
            tk = new Token("*","smult");
            break;
         }
         lerCaracter();
         
         return tk;
     }
     
     protected Token PegaToken() throws Exception{
             
         Character ret;
         Token tk = null;
         
         if(Character.isDigit(caracter)){
            tk = TrataDigito();
         }
         else if(Character.isLetter(caracter)){
             tk = Trata_id_and_palavra();
             
         } 
         else if(caracter.equals(':')){
             tk = TrataAtribuicao(); //<-mas isso a gente faz aqui, aproveita nishida e cria os metodos que estao faltando
         }  
         else if(caracter.equals('+')||caracter.equals('-')||caracter.equals('*')){
             tk = TrataOperadorAritmetico();
         }
         else if(caracter.equals('<')||caracter.equals('>')||caracter.equals('=')||caracter.equals('!')){
             tk = TrataOperadorRelacional();
         }
         else if(caracter.equals(';')||caracter.equals(',')||caracter.equals('(')||caracter.equals(')')||caracter.equals('.')){
             tk = TrataPontuacao();
         }
  
         else if(caracter.equals('\n')||caracter.equals('\r') || caracter.equals('}')|| caracter.equals('\t') || caracter.equals(' ')){
            if(caracter.equals('\n')){
            sintatico.SomaLinha();
            }
             lerCaracter();
            
         }
         else{
             //lerCaracter();
             
             
             
             //TrataErro();
          
             System.out.println("*** ERRO Lexico ***");
             System.exit(-1);
             
         }
         return tk;
        
     }
     
     
     
     
     protected Token TrataDigito() throws Exception{
          //Inicio
        String num = "" + caracter;
        lerCaracter();
        Token tk;
        while(Character.isDigit(caracter))
        {
            num = num + caracter;
            lerCaracter();
        }
        
        token.add(new Token(num,"snumero"));
        tk = new Token(num,"snumero");
        
        return tk;
        
     }
     
     
     protected Token Trata_id_and_palavra() throws Exception{
           
            String id = "" + caracter;
            Token tk;         
            lerCaracter();
            
            while(Character.isLetter(caracter) || Character.isDigit(caracter)  ){
                id = id + caracter;
                lerCaracter();
            }
            
            
            
            if(id.equals("programa")){
                token.add(new Token(id,"sprograma"));
                tk = new Token(id,"sprograma");
            }
            else if(id.equals("se")){
                token.add(new Token(id,"sse"));
                tk = new Token(id,"sse");
            }
            else if(id.equals("entao")){
                token.add(new Token(id,"sentao"));
                tk = new Token(id,"sentao");
            }
            else if(id.equals("senao")){
                token.add(new Token(id,"ssenao"));
                tk = new Token(id,"ssenao");
            }
            else if(id.equals("enquanto")){
                token.add(new Token(id,"senquanto"));
                tk = new Token(id,"senquanto");
            }
            else if(id.equals("faca")){
                token.add(new Token(id,"sfaca"));
                tk = new Token(id,"sfaca");
            }
            else if(id.equals("inicio")){
                token.add(new Token(id,"sinicio"));
                tk = new Token(id,"sinicio");
            }
            else if(id.equals("fim")){
                token.add(new Token(id,"sfim"));
                tk = new Token(id,"sfim");
            }
            else if(id.equals("escreva")){
                token.add(new Token(id,"sescreva"));
                tk = new Token(id,"sescreva");
                
            }
            else if(id.equals("leia")){
                token.add(new Token(id,"sleia"));
                tk = new Token(id,"sleia");
                
            }
            else if(id.equals("var")){
                token.add(new Token(id,"svar"));
                tk = new Token(id,"svar");
            
            }    
            else if(id.equals("booleano")){
                token.add(new Token(id,"sbooleano"));
                tk = new Token(id,"sbooleano");
            }
            else if(id.equals("inteiro")){
                token.add(new Token(id,"sinteiro"));
                tk = new Token(id,"sinteiro");
            }
            else if(id.equals("verdadeiro")){
                token.add(new Token(id,"sverdadeuri"));
                tk = new Token(id,"sverdadeuri");
            
            }
            else if(id.equals("falso")){
                token.add(new Token(id,"sfalso"));
                tk = new Token(id,"sfalso");
            
            }
            else if(id.equals("procedimento")){
                token.add(new Token(id,"sprocedimento"));
                tk = new Token(id,"sprocedimento");
            }
            else if(id.equals("funcao")){
                token.add(new Token(id,"sfuncao"));
                tk = new Token(id,"sfuncao");
            }
            else if(id.equals("div")){
                token.add(new Token(id,"sdiv"));
                tk = new Token(id,"sdiv");
            }
            else if(id.equals("e")){
                token.add(new Token(id,"se"));
                tk = new Token(id,"se");
            }
            else if(id.equals("ou")){
                token.add(new Token(id,"sou"));
                tk = new Token(id,"sou");
            
            }
            else if(id.equals("nao")){
                token.add(new Token(id,"snao"));
                tk = new Token(id,"snao");
            }
            else{
                token.add(new Token(id,"sidentificador"));
                tk = new Token(id,"sidentificador");
            }
            return tk;
     }
     
     
     
     
     protected Token TrataAtribuicao() throws Exception{
         
         lerCaracter();
         Token tk;
         if(caracter.equals('=')){
             token.add(new Token(":=","satribuicao"));
             tk = new Token(":=","satribuicao");
             lerCaracter();
         }
         else{
             token.add(new Token(":","sdoispontos"));
             tk = new Token(":","sdoispontos");
         }
         return tk;
     }
     
     protected Token TrataOperadorRelacional() throws Exception{
         String op = "" + caracter;
         Token tk = null;
         switch(op)
         {
             
         case "<":
             lerCaracter();
             if(caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token("<=","smenorig"));
                 tk = new Token("<=","smenorig");
                lerCaracter();
             }  
             else{
                 token.add(new Token("<","smenor"));
                 tk = new Token("<","smenor");
             }
             break;  
         case ">":
             lerCaracter();
             if( caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token(">=","smaiorig"));
                 tk = new Token(">=","smaiorig");
                 lerCaracter();
             }   
             else{
                 token.add(new Token(">","smaior"));
                 tk = new Token(">","smaior");
             }
             break;
         case "=":
             token.add(new Token("=","sig"));
             tk = new Token("=","sig");
             lerCaracter();
             break;
         case "!":
             lerCaracter();
             if(caracter.equals('=')){
                 
            op = op + caracter;
            token.add(new Token("!=","sdif"));
            tk = new Token("!=","sdif");
            lerCaracter();
             } 
         else
             break;
             
             
         }
         return tk;
     }
     
     protected Token TrataPontuacao() throws Exception{
         String op = "" + caracter;
         Token tk = null;
         switch(op)
         {
             
         case ".":
            token.add(new Token(op,"sponto"));
            tk = new Token(op,"sponto");
            break;   
                 
         case ";":
            token.add(new Token(op,"sponto_virgula"));
            tk = new Token(op,"sponto_virgula");
            this.flag_pontovirgula = true;
            break;     
               
         case ",":
            token.add(new Token(op,"svirgula"));
            tk = new Token(op,"svirgula");
            break;
             
         case "(":
            token.add(new Token(op,"sabre_parenteses"));
            tk = new Token(op,"sabre_parenteses");
            break;
             
         case ")":
            token.add(new Token(op,"sfecha_parenteses"));
            tk = new Token(op,"sfecha_parenteses");
            break;
         }
         lerCaracter();
         /*
         if(this.flag_pontovirgula == true && !caracter.equals('\n') || !caracter.equals('\r')){
             System.out.println("Erro Ponto e Virgula");
             System.exit(-1);
         }
         else{
             this.flag_pontovirgula = false;
         }
         */
         if(caracter.equals('"')){
         lerCaracter();
         }
         
         return tk;
     }

    private void TrataErro() {
        
        

//To change body of generated methods, choose Tools | Templates.
    }

    

     
     protected  Token getTokens(){
         
         token_indice++;
         
         if(token.isEmpty()){
             
             System.out.println("Compilado com Sucesso!");
             return null;
         }
         else{
             System.out.println("" + token_indice);
             return token.get(token_indice);
         }
     }
     
    
}




