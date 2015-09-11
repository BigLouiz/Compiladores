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
    
    protected String simbolo;
    protected String lexema;
    
    public Token(){
     
        

    }

    public Token(String lexema,String simbolo) {
        this.simbolo = simbolo;
        this.lexema = lexema;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    @Override
    public String toString() {
                  
        return lexema + "\t\t" + simbolo; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
