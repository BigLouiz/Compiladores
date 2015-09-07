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
public class Token {
    
    private String simbolo;
    private String lexema;
    
    public Token(){
     
        

    }
    
    public void setSimbolo(String simbolo){
       this.simbolo = simbolo;
        
    }
    
    public void setLexema(String lexema){
        this.lexema = lexema;
    }
    
    public String getLexema(){
        
        return this.lexema;
    }
    
    public String getSimbolo(){
        
        return this.simbolo;
    }
    
}
