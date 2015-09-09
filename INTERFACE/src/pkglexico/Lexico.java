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
     private Token token = new Token();
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
   
   read.lerCaracter();
   
   while(read.checkEOF() == false){
       
       while(caracter.equals('{')||caracter.equals(' ') && read.checkEOF() == false ){
           if(caracter.equals('{')){
           
               
                 while(!caracter.equals('}')&& read.checkEOF() == false){
                    read.lerCaracter();
                 }  
               
            }
           
           if(caracter.equals(' ')){
           
               
                 while(!caracter.equals(' ')&& read.checkEOF() == false){
                    read.lerCaracter();
                 }  
               
            }
       }
       
       if(caracter.equals('.')){
           read.PegaToken();
           //Insere na lista
       }
       //Fecha arquivo.Java nao precisa
       
   }
      
    
}


    public  void lerCaracter()throws Exception{
         //this.caracter = memory[cont];
         int r;
                
         if((r = in.read()) != -1) {
                     
         caracter = (char)r;
         cont++;
         
         System.out.println(caracter);
         
         
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
            token.setSimbolo("smais");
            break;   
                 
         case "-":
            token.setSimbolo("smenos");
            break;     
               
         case "*":
            token.setSimbolo("smult");
            break;
         }
         token.setLexema(op);
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
         else if(caracter.equals('<')||caracter.equals('>')||caracter.equals('=')){
             read.TrataOperadorRelacional();
         }
         else if(caracter.equals(';')||caracter.equals(',')||caracter.equals('(')||caracter.equals(')')||caracter.equals('.')){
             read.TrataPontuacao();
         }
         else{
             System.err.close(); ///????
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
        
        token.setSimbolo("snumero");
        token.setLexema(num);

     }
     
     
     protected void Trata_id_and_palavra(){


     }
     
     
     
     
     
     
     
     protected void TrataAtribuicao(){


     }
     
     protected void TrataOperadorRelacional() throws Exception{
         String op = "" + caracter;
         
         switch(op)
         {
             
         case "<":
             read.lerCaracter();
             if(caracter.equals('=')){
                 op = op + caracter;
                 token.setSimbolo("smanorig");
                read.lerCaracter();
             }  
            else
                 token.setSimbolo("smenor");
                 
         case ">":
             read.lerCaracter();
             if( caracter.equals('=')){
                 op = op + caracter;
                 token.setSimbolo("smaiorig");
                 read.lerCaracter();
             }   
             else
                 token.setSimbolo("smaior");
             
         case "=":
             token.setSimbolo("sig");
             read.lerCaracter();
             
         case "!":
             read.lerCaracter();
             if(caracter.equals('=')){
                 
            op = op + caracter;
            token.setSimbolo("sdif");
            read.lerCaracter();
             }  
         else
             break;
             
             token.setLexema(op);
         }

     }
     
     protected void TrataPontuacao(){
         String op = "" + caracter;
         
         switch(op)
         {
             
         case ".":
            token.setSimbolo("sponto");
            break;   
                 
         case ";":
            token.setSimbolo("sponto_virgula");
            break;     
               
         case ",":
            token.setSimbolo("svirgula");
            break;
             
         case "(":
            token.setSimbolo("sabre_parenteses");
            break;
             
         case ")":
            token.setSimbolo("sfecha_parenteses");
            break;
         }
         token.setLexema(op);

     }
     
    
}


