/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkglexico;
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
     private int token_indice;
    
     
     private Boolean flag_pontovirgula;
     //Analisador Lexical
     
     public Lexico() throws Exception{

          Scanner ler = new Scanner(System.in);
         System.out.printf("Informe o nome de arquivo texto:\n");
         String nome = ler.nextLine(); 
         Scanner file = new Scanner(new File(nome));
         File ww = new File(nome);
         System.out.printf("\nConteúdo do arquivo texto:\n"); 
         int j=0;
        
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
   
   int flag=2;
   
   while(checkEOF() == false){
       
       
       
       while(caracter.equals('{')||caracter.equals(' ') && checkEOF() == false ){
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
       }
       
       if(caracter.equals('}') && flag==0){
                     System.out.println("ERRO");
                     System.exit(-1);
                   }   
       
       if(caracter.equals('}')){
                  
               
               flag=0;
       }
           
       
       if(checkEOF() == false){
           PegaToken();
           //Insere na lista
           if(caracter.equals('.')){
           token.add(new Token(".","sponto"));
           }
           
       }
       
       
       //Fecha arquivo.Java nao precisa
        
        
   }
   
   
   for(Token t : token){
            System.out.println(t);
           // System.out.println(c.getLexema());
    }
        
        
        
        
      
    
}


    public Character lerCaracter()throws Exception{
         //this.caracter = memory[cont];
         int r;
                
         if((r = in.read()) != -1) {
                     
         caracter = (char)r;
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

    
     
     
     protected void TrataOperadorAritmetico() throws Exception{
           String op = "" + caracter;
         
         switch(op)
         {
             
         case "+":
            token.add(new Token("+","smais"));
            break;   
                 
         case "-":
            token.add(new Token("-","smenos"));
            break;     
               
         case "*":
            token.add(new Token("*","smult"));
            break;
         }
         lerCaracter();
     }
     
     protected void PegaToken() throws Exception{
             
         Character ret;
         
         if(Character.isDigit(caracter)){
             TrataDigito();
         }
         else if(Character.isLetter(caracter)){
                Trata_id_and_palavra();
             
         } 
         else if(caracter.equals(':')){
             TrataAtribuicao(); //<-mas isso a gente faz aqui, aproveita nishida e cria os metodos que estao faltando
         }  
         else if(caracter.equals('+')||caracter.equals('-')||caracter.equals('*')){
             TrataOperadorAritmetico();
         }
         else if(caracter.equals('<')||caracter.equals('>')||caracter.equals('=')||caracter.equals('!')){
             TrataOperadorRelacional();
         }
         else if(caracter.equals(';')||caracter.equals(',')||caracter.equals('(')||caracter.equals(')')||caracter.equals('.')){
             TrataPontuacao();
         }
  
         else if(caracter.equals('\n')||caracter.equals('\r') || caracter.equals('}')|| caracter.equals('\t')){
            lerCaracter();
         }
         else{
             //lerCaracter();
             
             
             
             //TrataErro();
          
             System.out.println("*** ERROR ***");
             System.exit(-1);
             
         }
        
     }
     
     
     
     
     protected void TrataDigito() throws Exception{
          //Inicio
        String num = "" + caracter;
        lerCaracter();
        
        while(Character.isDigit(caracter))
        {
            num = num + caracter;
            lerCaracter();
        }
        
        token.add(new Token(num,"snumero"));
        
     }
     
     
     protected void Trata_id_and_palavra() throws Exception{
          String id = "" + caracter;
                        
            lerCaracter();
            
            while(Character.isLetter(caracter)){
                id = id + caracter;
                lerCaracter();
            }
            
            
            
            if(id.equals("programa"))
                token.add(new Token(id,"sprograma"));
            
            else if(id.equals("se"))
                token.add(new Token(id,"sse"));
            
            else if(id.equals("entao"))
                token.add(new Token(id,"sentao"));
            
            else if(id.equals("senao"))
                token.add(new Token(id,"ssenao"));
            
            else if(id.equals("enquanto"))
                token.add(new Token(id,"senquanto"));
            
            else if(id.equals("faca"))
                token.add(new Token(id,"sfaca"));
            
            else if(id.equals("inicio"))
                token.add(new Token(id,"sinicio"));
            
            else if(id.equals("fim"))
                token.add(new Token(id,"sfim"));
            
            else if(id.equals("escreva"))
                token.add(new Token(id,"sescreva"));
            
            else if(id.equals("leia"))
                token.add(new Token(id,"sleia"));
            
            else if(id.equals("var"))
                token.add(new Token(id,"\tsvar"));
            
            else if(id.equals("boolean"))
                token.add(new Token(id,"sboolean"));
            
            else if(id.equals("inteiro"))
                token.add(new Token(id,"sinteiro"));
            
            else if(id.equals("verdadeiro"))
                token.add(new Token(id,"sverdadeuri"));
            
            else if(id.equals("falso"))
                token.add(new Token(id,"sfalso"));
            
            else if(id.equals("procedimento"))
                token.add(new Token(id,"sprocedimento"));
            
            else if(id.equals("funcao"))
                token.add(new Token(id,"sfuncao"));
            
            else if(id.equals("div"))
                token.add(new Token(id,"sdiv"));
            
            else if(id.equals("e"))
                token.add(new Token(id,"se"));
            
            else if(id.equals("ou"))
                token.add(new Token(id,"sou"));
            
            else if(id.equals("nao"))
                token.add(new Token(id,"snao"));
            
            else
                token.add(new Token(id,"sidentificador"));

     }
     
     
     
     
     protected void TrataAtribuicao() throws Exception{
         
         lerCaracter();
         
         if(caracter.equals('=')){
             token.add(new Token(":=","satribuicao"));
             lerCaracter();
         }
         else{
             token.add(new Token(":","sdoispontos"));
             
         }
         
     }
     
     protected void TrataOperadorRelacional() throws Exception{
         String op = "" + caracter;
         
         switch(op)
         {
             
         case "<":
             lerCaracter();
             if(caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token("<=","smanorig"));
                lerCaracter();
             }  
            else
                 token.add(new Token("<","smenor"));
             break;  
         case ">":
             lerCaracter();
             if( caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token(">=","smaiorig"));
                 lerCaracter();
             }   
             else
                 token.add(new Token(">","smaior"));
             break;
         case "=":
             token.add(new Token("=","sig"));
             lerCaracter();
             break;
         case "!":
             lerCaracter();
             if(caracter.equals('=')){
                 
            op = op + caracter;
            token.add(new Token("!=","sdif"));
            lerCaracter();
             } 
         else
             break;
             
             
         }

     }
     
     protected void TrataPontuacao() throws Exception{
         String op = "" + caracter;
         
         switch(op)
         {
             
         case ".":
            token.add(new Token(op,"sponto"));
            break;   
                 
         case ";":
            token.add(new Token(op,"sponto_virgula"));
            this.flag_pontovirgula = true;
            break;     
               
         case ",":
            token.add(new Token(op,"svirgula"));
            break;
             
         case "(":
            token.add(new Token(op,"sabre_parenteses"));
            break;
             
         case ")":
            token.add(new Token(op,"sfecha_parenteses"));
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
         return token.get(token_indice);
         }
     }
     
    
}




