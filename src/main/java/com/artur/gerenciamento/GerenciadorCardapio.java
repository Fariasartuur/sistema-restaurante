package com.artur.gerenciamento;

// Importações necessárias para formatação de números e manipulação de listas e mapas.
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.artur.controle.ItemCardapio;
import com.artur.interfaces.Listagem;

import java.util.List;

public class GerenciadorCardapio implements Listagem {

    private final Map<String, List<ItemCardapio>> cardapio; // Mapa que armazena os itens do cardápio por categoria
    private static Long IdGlobal = 1L; // Contador global para atribuir IDs únicos aos itens do cardápio


    // Construtor da classe
    public GerenciadorCardapio() {
        this.cardapio = new LinkedHashMap<>(); // Inicializa o mapa do cardápio
        gerarItems(); // Chama o método para gerar os itens do cardápio
    }

    // Método privado para gerar itens do cardápio
    private void gerarItems() {

         // Inicializa as categorias no mapa do cardápio
        cardapio.put("Pratos Principais", new ArrayList<>());
        cardapio.put("Acompanhamentos", new ArrayList<>());
        cardapio.put("Bebidas", new ArrayList<>());
        cardapio.put("Sobremesas", new ArrayList<>());
        cardapio.put("Outros", new ArrayList<>());

        // Pratos Principais
        adicionarItem("Pratos Principais", new ItemCardapio("Bife a Parmegiana", "Unico", "Bife de carne bovina empanado e coberto com molho de tomate e queijo derretido. Acompanha arroz branco e batatas fritas.", 25.00));
        adicionarItem("Pratos Principais", new ItemCardapio("Frango Grelhado", "Unico", "Peito de frango grelhado suculento, temperado com ervas finas. Servido com arroz branco e salada verde.", 20.00));
        adicionarItem("Pratos Principais", new ItemCardapio("Spaghetti a Bolonhesa", "Unico", "Massa spaghetti cozida al dente, coberta com um delicioso molho de carne moida e tomate, finalizado com queijo parmesao ralado.", 18.00));

        // Acompanhamentos
        adicionarItem("Acompanhamentos", new ItemCardapio("Arroz Branco", "Unico", "Porcao de arroz branco cozido no ponto perfeito.", 5.00));
        adicionarItem("Acompanhamentos", new ItemCardapio("Batata Frita - Pequena", "Pequena", "Porção de batatas fritas crocantes.", 8.00));
        adicionarItem("Acompanhamentos", new ItemCardapio("Batata Frita - Media", "Media", "Porcao de batatas fritas crocantes.", 10.00));
        adicionarItem("Acompanhamentos", new ItemCardapio("Batata Frita - Grande", "Grande", "Porcao de batatas fritas crocantes.", 12.00));
        adicionarItem("Acompanhamentos", new ItemCardapio("Salada Verde", "Unico", "Mix de folhas verdes frescas, tomate, pepino e cenoura, servido com molho vinagrete.", 7.00));

        // Bebidas
        adicionarItem("Bebidas", new ItemCardapio("Refrigerante (lata)", "Lata", "Lata de refrigerante de sua escolha (Coca-Cola, Pepsi, Sprite, etc.).", 5.00));
        adicionarItem("Bebidas", new ItemCardapio("Suco Natural", "300ml", "Suco fresco de frutas naturais da estacao (laranja, limao, morango, etc.).", 6.00));
        adicionarItem("Bebidas", new ItemCardapio("Agua Mineral", "500ml", "Garrafa de agua mineral.", 3.00));

        // Sobremesas
        adicionarItem("Sobremesas", new ItemCardapio("Mousse de Chocolate", "Unico", "Deliciosa sobremesa feita com chocolate cremoso e leve.", 8.00));
        adicionarItem("Sobremesas", new ItemCardapio("Pudim de Leite", "Unico", "Sobremesa tradicional a base de leite condensado, ovos e acucar caramelizado.", 7.00));
        adicionarItem("Sobremesas", new ItemCardapio("Sorvete - 1 bola", "1 bola", "Sorvete cremoso de sua escolha.", 4.00));
        adicionarItem("Sobremesas", new ItemCardapio("Sorvete - 2 bolas", "2 bolas", "Sorvete cremoso de sua escolha.", 7.00));
        adicionarItem("Sobremesas", new ItemCardapio("Sorvete - 3 bolas", "3 bolas", "Sorvete cremoso de sua escolha.", 10.00));

    }

    
   // Método privado para retornar a categoria de um item baseado no nome da categoria
    private String Categoria(String categoria) {

        return switch (categoria) {
            case "Pratos Principais", "Acompanhamentos" -> "Pratos Principais";
            case "Bebidas" -> "Acompanhamentos";
            case "Sobremesas" -> "Bebidas";
            case "Outros" -> "Sobremesas";
            default -> null;
        };

    }

    // Método privado para obter o ID mais alto de uma categoria específica
    private Long obterIdMaisAltoCategoria(String categoria, List<ItemCardapio> itensCategoria) {
        Long idMaisAlto = null;

        // Verifica se a lista de itens da categoria não é nula e não está vazia
        if (itensCategoria != null && !itensCategoria.isEmpty()) {
            // Inicializa idMaisAlto com o primeiro ID válido da categoria
            for (ItemCardapio item : itensCategoria) {
                if (item.getCategoria().equals(categoria)) {
                    idMaisAlto = item.getId(); // Define o primeiro ID como o mais alto inicialmente
                    break;
                }
            }

            // Percorre os itens da categoria para encontrar o ID mais alto
            for (ItemCardapio item : itensCategoria) {
                if (item.getCategoria().equals(categoria) && item.getId() > idMaisAlto) {
                    idMaisAlto = item.getId(); // Atualiza o ID mais alto encontrado
                }
            }
        } else{
            return null;
        }

        return idMaisAlto;
    }

    // Método público para adicionar um item ao cardápio
    public void adicionarItem(String categoria, ItemCardapio item) {

        Long idAlto;

        // Obtém a categoria anterior e a lista de itens da categoria anterior
        String catAnterior = Categoria(categoria);
        List<ItemCardapio> itensCatAnterior = cardapio.get(catAnterior);
        List<ItemCardapio> itens = cardapio.get(categoria);

        
          // Define o ID do item baseado na categoria
        if (categoria.equals("Pratos Principais")) {
            IdGlobal++;
            item.setIdItem(IdGlobal);
        } else {
            idAlto = obterIdMaisAltoCategoria(catAnterior, itensCatAnterior);
            if(idAlto == null){
                System.out.println("Erro, categoria vazia.");
            } else{
                item.setIdItem(idAlto + 1);
            }
        }

        // Adiciona o item à lista da categoria
        if (itens != null) {
            itens.add(item);
        }

        // Reorganiza os IDs dos itens no cardápio
        Long novoId = 1L;
        for (Map.Entry<String, List<ItemCardapio>> entry : cardapio.entrySet()) {
            for (ItemCardapio itemCategoria : entry.getValue()) {
                itemCategoria.setIdItem(novoId++);
            }
        }

    }


    
    // Método público para remover um item do cardápio
    public void removerItem(String categoria, int id) {

          // Verifica se a categoria existe no cardápio
        if (cardapio.containsKey(categoria)) {
            List<ItemCardapio> itensCategoria = cardapio.get(categoria);
            ItemCardapio itemRemovido = null;

            // Encontra o item a ser removido pelo ID
            for (ItemCardapio item : itensCategoria) {
                if (item.getId() == id) {
                    itemRemovido = item;
                    break;
                }
            }

             // Remove o item se encontrado e reorganiza os IDs restantes
            if (itemRemovido != null) {
                itensCategoria.remove(itemRemovido);
                System.out.println("Item removido com sucesso.");

                Long novoId = 1L;
                for (Map.Entry<String, List<ItemCardapio>> entry : cardapio.entrySet()) {
                    for (ItemCardapio itemCategoria : entry.getValue()) {
                        itemCategoria.setIdItem(novoId++);
                    }
                }

            } else {
                System.out.println("ID do Item não encontrado na categoria: " + categoria);
            }
        } else {
            System.out.println("Categoria não encontrada: " + categoria);
        }
    }

      // Método para listar todos os itens do cardápio
    @Override
    public void listar() {
         
        // Define o formato para exibir os preços
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", symbols);

        System.out.println("==================== CARDAPIO ====================");

        // Percorre as categorias e seus itens no cardápio
        for (Map.Entry<String, List<ItemCardapio>> entry : getCardapio().entrySet()) {
            String categoria = entry.getKey();
            List<ItemCardapio> itensCategoria = entry.getValue();

            System.out.println(categoria + "\n");

            for (ItemCardapio item : itensCategoria) {
                System.out.println("ID: " + item.getId() + " | " + item.getNome() + ": " + item.getDescricao());
                System.out.println("Tamanho: " + item.getTamanho());
                System.out.println("Preço: R$ " + df.format(item.getPreco()) + "\n");

            }

        }

    }
    

    // Método para imprimir itens de uma categoria específica
    public void imprimirItensCategoria(String categoria, Map<String, List<ItemCardapio>> cardapio) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", symbols);
        

        // Verifica se a categoria existe no cardápio
        if (cardapio.containsKey(categoria)) {
            List<ItemCardapio> itensCategoria = cardapio.get(categoria);
            for (ItemCardapio item : itensCategoria) {
                System.out.println("ID: " + item.getId() + " | " + item.getNome() + ": " + item.getDescricao());
                System.out.println("Tamanho: " + item.getTamanho());
                System.out.println("Preço: R$ " + df.format(item.getPreco()) + "\n");
            }
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }

    // Método getter para obter o cardápio
    public Map<String, List<ItemCardapio>> getCardapio() {
        return cardapio;
    }


}
