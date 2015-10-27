/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkcompilador;

import static java.lang.Math.sqrt;

/**
 *
 * @author Louiz
 */
public class Semantico {
    
    
    
    public static void main(String args[]){
       
    

     
    // write your code in C99
        int A=4;
        int B=17;
    int i,j,cont;
    
    cont = 0;
    j=0;

    for(i=A; i<=B; i++){
    
        j = (int) sqrt(i);
    
        if(j*j == i){
    
            cont++;
        }
    
    }
    
    System.out.print(cont);

   

        
    
        
    }
    
    
    
    
    
    
    
   
    
    
}
