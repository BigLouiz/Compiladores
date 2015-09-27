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
    
    public static void main(String args[]) throws Exception{
        
        lexico = new Lexico();
        
        token  = lexico.getTokens();
        
        System.out.println("Sintatico Aqui:::::");
        
        if(token != null){
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getTokens();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        
        token  = lexico.getTokens();
        
        System.out.println("" + token.lexema + "         " + token.simbolo);
        }
    }

    
}
