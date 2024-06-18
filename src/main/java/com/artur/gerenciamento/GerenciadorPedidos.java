package com.artur.gerenciamento;

import com.artur.controle.ItemPedido;
import com.artur.interfaces.Listagem;

import java.util.*;

public class GerenciadorPedidos implements Listagem {

    // Comparator para ordenar os pedidos primeiro pelo nome do pedido e depois pelo ID do cliente
    static Comparator<ItemPedido> comparator = Comparator.comparing(ItemPedido::getNomePedido).thenComparing(ItemPedido::getIdCliente);

    // Variável estática para contar o número de pedidos
    private static Long contadorPedidos = 0L;

    // Inicializa o conjunto de pedidos com o comparator
    private static final Map<Long, Set<ItemPedido>> listaPedidos = new TreeMap<>();; // Conjunto de pedidos (TreeSet para manter a ordem)


    // Método para adicionar um item ao pedido
    public void adicionarItem(ItemPedido item) {
        // Obtém ou cria um novo conjunto de pedidos para o ID do cliente
        Set<ItemPedido> pedidosCliente = listaPedidos.computeIfAbsent(item.getIdCliente(), k -> new TreeSet<>(comparator));

        boolean itemExistente = false; // Flag para verificar se o item já existe na lista

        for (ItemPedido i : pedidosCliente) {
            // Verifica se o item já existe na lista e pertence ao mesmo cliente
            if (Objects.equals(item.getIdItemCardapio(), i.getIdItemCardapio())) {
                i.setQuantidade(i.getQuantidade() + item.getQuantidade());
                itemExistente = true;
                break;
            }
        }

        if (!itemExistente) {
            // Se o item não existir na lista para o mesmo cliente, adiciona um novo item
            contadorPedidos++;
            item.setIdPedido(contadorPedidos); // Define o ID do pedido
            pedidosCliente.add(item); // Adiciona o item ao conjunto de pedidos do cliente
        }
    }

    // Método para remover um item do pedido
    public void removerItem(Long idItem) {
        // Itera sobre todos os conjuntos de pedidos para encontrar e remover o item
        for (Set<ItemPedido> pedidosCliente : listaPedidos.values()) {
            ItemPedido itemRemovido = null; // Variável para armazenar o item a ser removido
            for (ItemPedido item : pedidosCliente) {
                if (Objects.equals(item.getId(), idItem)) {
                    itemRemovido = item; // Encontra o item a ser removido
                    break;
                }
            }

            if (itemRemovido != null) {
                pedidosCliente.remove(itemRemovido); // Remove o item do conjunto de pedidos
                contadorPedidos--; // Decrementa o contador de pedidos
                System.out.println("Item removido com sucesso.");
                return; // Sai do método após remover o item
            }
        }

        System.out.println("Item não encontrado.");
    }

    // Lista todos os pedidos
    @Override
    public void listar() {
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
        } else {
            for (Map.Entry<Long, Set<ItemPedido>> entry : listaPedidos.entrySet()) {
                Long idCliente = entry.getKey();
                Set<ItemPedido> pedidosCliente = entry.getValue();
                for (ItemPedido pedido : pedidosCliente) {
                    System.out.println("ID Cliente: " + idCliente + " | ID Pedido: " + pedido.getId() +
                            " | Nome do pedido: " + pedido.getNomePedido() + " | Quantidade: " + pedido.getQuantidade() +
                            " | Preço: " + pedido.getPrecoPedido() + " | ID do Item de Cardápio: " + pedido.getIdItemCardapio());
                }
            }
        }
    }

    // Método para obter o mapa de pedidos
    public Map<Long, Set<ItemPedido>> getListaPedidos() {
        return listaPedidos;
    }
}
