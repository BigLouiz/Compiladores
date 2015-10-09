/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkcompilador;

import java.util.*;

/**
 *
 * @author Louiz
 */
public class ListaDeContatos {
    
    public static void main(String args[]){
        
        ArrayList<Contato> contatos = new ArrayList();
        
        Contato contato = new Contato("0","e ai  qqq");
        Contato contato1 = new Contato("1","e ai  ee");
        Contato contato2 = new Contato("2","e ai  www");
        
        contatos.add(contato);
        contatos.add(contato1);
        contatos.add(contato2);
        
        for(Contato c : contatos){
            System.out.println(c);
           // System.out.println(c.getLexema());
        }
        
        
        
        
    }
    
}
