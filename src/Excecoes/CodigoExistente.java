package Excecoes;

public class CodigoExistente extends Exception {
    
    private static final long serialVersionUID = 1L;
    public CodigoExistente(){
    super("Código já existe!");
    }
    
}
