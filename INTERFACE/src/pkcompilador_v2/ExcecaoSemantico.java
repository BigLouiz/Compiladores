/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author Leonardo
 */
public class ExcecaoSemantico extends Exception{

    private int linha;
    private int coluna;
    private String mensagem;
    
    ExcecaoSemantico(String string, int linha, int coluna) {
        super(string);
        this.linha = linha;
        this.coluna = coluna;
        this.mensagem = string;
    }

    
    public int getLinha()
    {
        return linha;
    }
    
    public int getColuna()
    {
        return coluna;
    }

    public String getMensagem() {
        return mensagem;
    }
    
}
