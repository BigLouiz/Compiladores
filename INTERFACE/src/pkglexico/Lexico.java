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
     private static Lexico read = new Lexico();
     static BufferedReader in;
     private  static FileInputStream arquivo;
     
     
     //Analisador Lexical
     
     public static void main(String args[]) throws Exception{
         
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
read.lerCaracter();  // returns the GET
//read.lerCaracter();  // returns the Host header
in.reset();     // rewinds the stream back to the mark
read.lerCaracter();  // returns the GET again*/
   //Exibe Tokens
   
   read.lerCaracter();
   
   while(read.checkEOF() == false){
       
       while(caracter.equals('{')||caracter.equals(' ') && read.checkEOF() == false ){
           if(caracter.equals('{')){
           
               
                 while(!caracter.equals('}')&& read.checkEOF() == false){
                    read.lerCaracter();
                 }  
               
            }
           
           if(caracter.equals(' ')){
           
               
                 while(caracter.equals(' ')&& read.checkEOF() == false){
                    read.lerCaracter();
                 }  
               
            }
       }
       
       if(read.checkEOF() == false){
           read.PegaToken();
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


    public  void lerCaracter()throws Exception{
         //this.caracter = memory[cont];
         int r;
                
         if((r = in.read()) != -1) {
                     
         caracter = (char)r;
         cont++;
         
        
         
         
         }
         else{
            System.out.println("End OF File!!!!");
         }
         
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
         read.lerCaracter();
     }
     
     protected void PegaToken() throws Exception{
         
         if(Character.isDigit(caracter)){
             read.TrataDigito();
         }
         else if(Character.isLetter(caracter)){
                read.Trata_id_and_palavra();
             
         } 
         else if(caracter.equals(':')){
             read.TrataAtribuicao(); //<-mas isso a gente faz aqui, aproveita nishida e cria os metodos que estao faltando
         }  
         else if(caracter.equals('+')||caracter.equals('-')||caracter.equals('*')){
             read.TrataOperadorAritmetico();
         }
         else if(caracter.equals('<')||caracter.equals('>')||caracter.equals('=')||caracter.equals('!')){
             read.TrataOperadorRelacional();
         }
         else if(caracter.equals(';')||caracter.equals(',')||caracter.equals('(')||caracter.equals(')')||caracter.equals('.')){
             read.TrataPontuacao();
         }
         else{
            read.lerCaracter(); ///????
         }
        
     }

     
     
     protected void TrataDigito() throws Exception{
          //Inicio
        String num = "" + caracter;
        read.lerCaracter();
        
        while(Character.isDigit(caracter))
        {
            num = num + caracter;
            read.lerCaracter();
        }
        
        token.add(new Token(num,"snumero"));
        
     }
     
     
     protected void Trata_id_and_palavra() throws Exception{
          String id = "" + caracter;
                        
            read.lerCaracter();
            
            while(Character.isLetter(caracter)){
                id = id + caracter;
                read.lerCaracter();
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
            
            
            
            
            /*
            switch(id){
                case "programa":
                    token.setSimbolo("sprograma");
                    break;
                    
                case "se":
                    token.setSimbolo("sse");
                    break;
                    
                case "entao":
                    token.setSimbolo("sentao");
                    break;
                    
                case "senao":
                    token.setSimbolo("ssenao");
                    break;
                    
                case "enquanto":
                    token.setSimbolo("senquanto");
                    break;
                    
                case "faca":
                    token.setSimbolo("sfaca");
                    break;
                    
                case "inicio":
                    token.setSimbolo("sinicio");
                    break;
                    
                case "fim":
                    token.setSimbolo("sfim");
                    break;
                    
                case "escreva":
                    token.setSimbolo("sescreva");
                    break;
                    
                case "leia":
                    token.setSimbolo("sleia");
                    break;
                    
                case "var":
                    token.setSimbolo("svar");
                    break;
                    
                case "inteiro":
                    token.setSimbolo("sinteiro");
                    break;
                    
                case "booleano":
                    token.setSimbolo("sbooleano");
                    break;
                    
                case "verdadeiro":
                    token.setSimbolo("sverdadeiro");
                    break;
                    
                case "falso":
                    token.setSimbolo("sfalso");
                    break;
                    
                case "procedimento":
                    token.setSimbolo("sprocedimento");
                    break;
                    
                case "funcao":
                    token.setSimbolo("sfuncao");
                    break;
                    
                case "div":
                    token.setSimbolo("sdiv");
                    break;
                    
                case "e":
                    token.setSimbolo("se");
                    break;
                    
                case "ou":
                    token.setSimbolo("sou");
                    break;
                    
                case "nao":
                    token.setSimbolo("snao");
                    break;
                    
        
                token.setSimbolo("sidentificador");
            }
            */

     }
     
     
     
     
     
     
     
     protected void TrataAtribuicao() throws Exception{
         
         read.lerCaracter();
         
         if(caracter.equals('=')){
             token.add(new Token(":=","satribuicao"));
             read.lerCaracter();
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
             read.lerCaracter();
             if(caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token("<=","smanorig"));
                read.lerCaracter();
             }  
            else
                 token.add(new Token("<","smenor"));
             break;  
         case ">":
             read.lerCaracter();
             if( caracter.equals('=')){
                 op = op + caracter;
                 token.add(new Token(">=","smaiorig"));
                 read.lerCaracter();
             }   
             else
                 token.add(new Token(">","smaior"));
             break;
         case "=":
             token.add(new Token("=","sig"));
             read.lerCaracter();
             break;
         case "!":
             read.lerCaracter();
             if(caracter.equals('=')){
                 
            op = op + caracter;
            token.add(new Token("!=","sdif"));
            read.lerCaracter();
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
         read.lerCaracter();
         
         
         if(caracter.equals('"')){
         read.lerCaracter();
         }
     }
     
    
}


