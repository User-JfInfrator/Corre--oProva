import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {

    private LocalDate data;
    private Produto produto;
    private int quantidade;
    
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Venda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.produto = produto;
        this.quantidade = quantidade;
    }


    @Override
    public String toString() {
        return String.format("- Data da venda: %s,Produto: %s,Quantidade vendida: %d,Valor unitário: %.2f,Valor total: %.2f",
        data.format(df),produto.getNome(),quantidade,produto.getValor(),produto.getValor()*quantidade);
    }


    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getValor() {
        return produto.getValor() * quantidade;
    }
    
}
