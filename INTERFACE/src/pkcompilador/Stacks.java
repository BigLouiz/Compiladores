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
public class Stacks {
    
    
    public static void main(String[] args){
        
        Stack<String> stack = new Stack<String>();
        
        stack.push("bottom");
        
        printStack(stack);
        
        stack.push("second");
        
        printStack(stack);
        
        stack.push("third");
        
        printStack(stack);
        
        //Peek:Indica o que se encontra no topo da pilha
        System.out.println("Peek:" + stack.peek());
        
        //Search: Retorna -1 caso nao encontrado
        //Retorna  de cima pra baixo a posicao do que esta sendo procurado
        
        //Third -1
        //Second -2
        //bottom - 3
        
        //Search deve retornar 3
        
        
        System.out.println("Search:" + stack.search("bottom"));
        
        
        
        stack.pop();
        
        printStack(stack);
        
        stack.pop();
        
        printStack(stack);
        
        stack.pop();
        
        printStack(stack);
        
    }
    
    
    private static void printStack(Stack<String> s){
        
        if(s.isEmpty()){
            
            System.out.println("Stack Empty");
        }
        else{
            System.out.printf("%s TOP\n", s);
            
        }
    }
}
