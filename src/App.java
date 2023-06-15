import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.time.format.DateTimeFormatter;

import Excecoes.CodigoExistente;
import Excecoes.OpcaoInvalida;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<Produto> listaProduto = new ArrayList<>();
        List<Venda> listaVenda = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int opcao = 0;
        
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        do {
            try{
            System.out.println("\n\nMENU\n\n");
            System.out.println("1- Incluir produto");
            System.out.println("2- Consultar produto");
            System.out.println("3- Listar produto");
            System.out.println("4- Vendas por período");
            System.out.println("5- Realizar vendas");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
            try{
            System.out.println("Digite o código do produto: ");
            String codigo = sc.next();
            if(!listaProduto.stream()
            .filter(p -> p .getCodigo().equals(codigo)).collect(Collectors.toList()).isEmpty()){
                throw new CodigoExistente();

            }
                
            System.out.println("Digite o nome do produto: ");
            String nome = sc.next();

            System.out.println("Digite o valor do produto: ");
            double valor = sc.nextDouble();

            System.out.println("Digite a quantidade em estoque do produto: ");
            int quantidade = sc.nextInt();

            listaProduto.add(new Produto(codigo, nome, valor, quantidade));
            

            }catch(CodigoExistente e){
                
                System.out.println(e.getMessage());
                sc.nextLine();

            }    
            
                voltarMenu(sc);
            } else if (opcao == 2) {
                System.out.println("Digite o código do produto: ");
                String codigo = sc.next();
                List<Produto> novalist = listaProduto.stream()
                .filter(p -> p .getCodigo().equals(codigo)).collect(Collectors.toList());;
                if(novalist.isEmpty()){
                    System.out.println("Produto não localizado");
                }
                else{
                    for (Produto produto : novalist) {
                        System.out.println(produto);
                        
                    }
                }
                sc.nextLine();

                voltarMenu(sc);
            } else if (opcao == 3) {
                System.out.println("\n-|Listagem de produtos|-\n");
                for (Produto produto : listaProduto) {
                    System.out.println(produto);   
                }
                DoubleSummaryStatistics resumo = listaProduto.stream()
                .collect(Collectors.summarizingDouble(Produto::getValor));
                System.out.println("Valor médio: " + resumo.getAverage());
                System.out.println("Maior valor: " + resumo.getMax());
                System.out.println("Menor valor: " + resumo.getMin());

                sc.nextLine();
                voltarMenu(sc);
            } else if (opcao == 4) {
                if(listaVenda.isEmpty()){
                System.out.println("Não existe vendas cadastradas");
                }
                else{
                System.out.println("-| Vendas Por Período |-\n");

                Map<LocalDate, List<Venda>> vendasPeriodo = listaVenda
                .stream().collect(Collectors.groupingBy(Venda::getData));
                vendasPeriodo.entrySet().forEach(e -> {
                System.out.println("Período de emissão: " + e.getKey().format(df));
                e.getValue().forEach(v -> System.out.println(v));
                Double resumo = e.getValue().stream()
                .collect(Collectors.averagingDouble(Venda::getValor));;
                System.out.println("Média das vendas: " + resumo);
                });
                
                
            }
                
                sc.nextLine();
                voltarMenu(sc);
            } else if (opcao == 5) {
                System.out.println("\n -|Realizar vendas|- \n");
                System.out.println("Digite o código do produto: ");
                String codigo = sc.next();

                List<Produto> novalist = listaProduto.stream()
                .filter(p -> p .getCodigo().equals(codigo)).collect(Collectors.toList());;
                if(novalist.isEmpty()){
                    System.out.println("Produto não localizado");
                }
                else{
                    System.out.println("Informe a data da venda: " + "Formato de data: dd/mm/aaaa");
                    LocalDate dataVenda = LocalDate.parse(sc.next(),df);

                    System.out.println("Informe a quantidade que deseja vender: ");
                    int quantidade = sc.nextInt();

                    Produto produto = novalist.get(0);
                    if(produto.getQuantidade() < quantidade){
                        System.out.println("Quantidade para venda insuficiente!");
                    }
                    else{
                        listaVenda.add(new Venda(dataVenda, produto, quantidade));
                        produto.setQuantidade(produto.getQuantidade() - quantidade);
                        System.out.println("-|Venda realizada com sucesso|-");

                    }
                }
                sc.nextLine();
                voltarMenu(sc);
            } else if (opcao != 0) {
                throw new OpcaoInvalida();
            }
            }catch(OpcaoInvalida e){
        System.out.println(e.getMessage());
            }
        } while (opcao != 0);
        
    

        System.out.println("Fim do programa!");
        sc.close();
    }
        
    
        private static void voltarMenu(Scanner sc) throws InterruptedException, IOException {
        System.out.println("\nPressione ENTER para voltar ao menu.");
        sc.nextLine();


        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");

        System.out.flush();
    }
}
