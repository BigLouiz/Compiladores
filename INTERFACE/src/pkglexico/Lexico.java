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
    
     private char caracter;
     private Token token = new Token();
     private static char[] memory;
     private int cont = 0;
     private static Lexico read = new Lexico();
     
     
     
     public static void main(String args[]) throws Exception{
         
         Scanner ler = new Scanner(System.in);
         System.out.printf("Informe o nome de arquivo texto:\n");
         String nome = ler.nextLine(); 
         System.out.printf("\nConteúdo do arquivo texto:\n"); 
         int j=0;
         
         String str = "";
         try { 
             try (FileInputStream arq = new FileInputStream(nome)) {
                 int r;
                 
                 while ((r = arq.read()) != -1) {
                     
                     str += (char)r;
                     
                     j++;
                     // do something with the character c
                 }
// lê da segunda até a última linha 
             } 
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
    } 
    //System.out.print(str);
    memory = str.toCharArray();
    
    for(int i=0;i<memory.length;i++){
         //System.out.println();
         System.out.print(""+memory[i]);
         
     }
    
    
    
}


       public  void lerCaracter()throws Exception{
         this.caracter = memory[cont];
         cont++;
         
         System.out.println(caracter);
         
     }  
     

    
     
     
     protected void TrataOperadorAritmetico(){


     }
     
     protected void TrataDigito(){


     }
     
     
     protected void Trata_id_and_palavra(){


     }
     
     
     protected void PegaToken(){


     }
     
     
     
     
     protected void TrataAtribuicao(){


     }
     
     protected void TrataOperadorRelacional(){


     }
     
     protected void TrataPontuacao(){
         println("Fodase");

     }
     
     protected void trataOperadorAritmetico(){


     }
}


