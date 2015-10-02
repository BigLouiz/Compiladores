/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkglexico;

import java.util.*;

/**
 *
 * @author Louiz
 */
public class Atributos {

    public static String UrlArquivo;
    public String TextoArquivo;
    public int i;
    public ArrayList<String> Tokens = new ArrayList<>();


    public int getI() {
        return i;
    }
    public ArrayList<String> getTokens() {
        return Tokens;
    }
    public void setTokens(ArrayList<String> tokens) {
        Tokens = tokens;
    }
    public void setI(int i) {
        this.i = i;
    }
    public String getUrlArquivo() {
        return UrlArquivo;
    }
    public void setUrlArquivo(String urlArquivo) {
        UrlArquivo = urlArquivo;
    }
    public String getTextoArquivo() {
        return TextoArquivo;
    }
    public void setTextoArquivo(String textoArquivo) {
        TextoArquivo = textoArquivo;
    }


}

