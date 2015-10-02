/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkglexico;

public class Metodos {

    public String Tokens(){

        Atributos sw = new Atributos();
        sw.Tokens.add("programa");
        sw.Tokens.add("se");
        sw.Tokens.add("entao");
        sw.Tokens.add("enquanto");
        sw.Tokens.add("inicio");
        sw.Tokens.add("para");
        sw.Tokens.add("ate");
        sw.Tokens.add("faz");
        sw.Tokens.add("fim");
        sw.Tokens.add("variaveis");
        sw.Tokens.add("inteiro");
        sw.Tokens.add("real");
        sw.Tokens.add("caracter");
        sw.Tokens.add("fimse");
        sw.Tokens.add("escreva");
        sw.Tokens.add("leia");
        sw.Tokens.add("repita");
        sw.Tokens.add("+");
        sw.Tokens.add("-");
        sw.Tokens.add("/");
        sw.Tokens.add("*");
        sw.Tokens.add("<");
        sw.Tokens.add(">");
        sw.Tokens.add("=>");
        sw.Tokens.add("<=");
        sw.Tokens.add("==");
        sw.Tokens.add("<>");
        sw.Tokens.add("e");
        sw.Tokens.add("ou");
        return sw.Tokens.toString();
    }

    public void Analizador(){

    }

}

