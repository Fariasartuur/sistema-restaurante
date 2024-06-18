package com.artur.gerenciamento;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.artur.controle.ItemPedido;

public class GerenciadorCozinha {

    // Conjuntos de pedidos organizados por seu estado (pendente, em preparação, pronto).
    private final Set<ItemPedido> pedidosPendentes;
    private final Set<ItemPedido> pedidosEmPreparacao;
    private final Set<ItemPedido> pedidosProntos;

    public GerenciadorCozinha() {

         // Comparator para ordenar os pedidos primeiramente pelo nome e, em caso de empate, pelo ID do cliente.
        Comparator<ItemPedido> comparator = Comparator
                .comparing(ItemPedido::getNomePedido)
                .thenComparing(ItemPedido::getIdCliente);

        // Inicializa os conjuntos de pedidos usando o Comparator definido.
        this.pedidosPendentes = new TreeSet<>(comparator);
        this.pedidosEmPreparacao = new TreeSet<>(comparator);
        this.pedidosProntos = new TreeSet<>(comparator);
    }

     // Método estático para exibir uma animação de carregamento por um determinado tempo.
    public static void loadingAnimation(long durationInMillis) {
        try {
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < durationInMillis) {
                System.out.print("\rPreparando.  ");
                Thread.sleep(500);
                System.out.print("\rPreparando.. ");
                Thread.sleep(500);
                System.out.print("\rPreparando...");
                Thread.sleep(500);
            }
            System.out.print("\r                "); // Limpa a linha após a animação
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

     // Método para preparar um pedido. Este método exibe mensagens e animações para simular o processo de preparação.
    public void preparar(ItemPedido novoPedido) {
        System.out.println("Pedido adicionado com sucesso!");

        loadingAnimation(2000);
        marcarPedidoEmPendente(novoPedido);
        loadingAnimation(2000);
        marcarPedidoComoPreparando(novoPedido);
        loadingAnimation(2000);
        marcarPedidoComoPronto(novoPedido);
        loadingAnimation(2000);
        System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " entregue.");
    }
     
     // Marca o pedido como pendente, adicionando-o ao conjunto de pedidos pendentes.
    public void marcarPedidoEmPendente(ItemPedido novoPedido) {
        if (!atualizarQuantidadeSeExistente(novoPedido, pedidosPendentes)) {
            pedidosPendentes.add(novoPedido);
            System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " foi adicionado a pendentes.");
        } else {
            System.out.println("Pedido ID: " + novoPedido.getId() + " atualizado na lista de pendentes.");
        }
    }
    
      // Marca o pedido como em preparação, movendo-o do conjunto de pendentes para o de em preparação.
    public void marcarPedidoComoPreparando(ItemPedido novoPedido) {
        if (!atualizarQuantidadeSeExistente(novoPedido, pedidosEmPreparacao)) {
            ItemPedido pedidoPendente = null;
            for (ItemPedido pedido : pedidosPendentes) {
                if (pedido.getIdItemCardapio() == novoPedido.getIdItemCardapio() && pedido.getIdCliente() == novoPedido.getIdCliente()) {
                    pedidoPendente = pedido;
                    break;
                }
            }

            if (pedidoPendente != null) {
                pedidosPendentes.remove(pedidoPendente);
                pedidosEmPreparacao.add(novoPedido);
                System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " está em preparação.");
            } else {
                pedidosEmPreparacao.add(novoPedido);
                System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " adicionado diretamente a em preparação.");
            }
        } else {
            System.out.println("Pedido ID: " + novoPedido.getId() + " atualizado na lista de em preparação.");
        }
    }

      // Marca o pedido como pronto, movendo-o do conjunto de em preparação para o de prontos.
    public void marcarPedidoComoPronto(ItemPedido novoPedido) {
        if (!atualizarQuantidadeSeExistente(novoPedido, pedidosProntos)) {
            ItemPedido pedidoEmPreparacao = null;
            for (ItemPedido pedido : pedidosEmPreparacao) {
                if (pedido.getIdItemCardapio() == novoPedido.getIdItemCardapio() && pedido.getIdCliente() == novoPedido.getIdCliente()) {
                    pedidoEmPreparacao = pedido;
                    break;
                }
            }

            if (pedidoEmPreparacao != null) {
                pedidosEmPreparacao.remove(pedidoEmPreparacao);
                pedidosProntos.add(novoPedido);
                System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " está pronto.");
            } else {
                pedidosProntos.add(novoPedido);
                System.out.println("Pedido ID: " + novoPedido.getId() + " - " + novoPedido.getNomePedido() + " adicionado diretamente a prontos.");
            }
        } else {
            System.out.println("Pedido ID: " + novoPedido.getId() + " atualizado na lista de prontos.");
        }
    }
    
    // Método privado que atualiza a quantidade de um pedido se ele já existir no conjunto.
    private boolean atualizarQuantidadeSeExistente(ItemPedido novoPedido, Set<ItemPedido> pedidos) {
        for (ItemPedido pedido : pedidos) {
            if (pedido.getNomePedido().equals(novoPedido.getNomePedido()) && pedido.getIdCliente() == novoPedido.getIdCliente()) {
                pedido.setQuantidade(pedido.getQuantidade() + novoPedido.getQuantidade());
                return true;
            }
        }
        return false;
    }

    // Método para mostrar todos os pedidos pendentes.
    public void mostrarPedidosPendentes() {
        System.out.println("Pedidos pendentes:");
        for (ItemPedido pedido : pedidosPendentes) {
            System.out.println("ID: " + pedido.getId() + " | Nome do pedido: " + pedido.getNomePedido() + " | Quantidade: " + pedido.getQuantidade() + " | Preco: " + pedido.getPrecoPedido() + " | ID e Cliente: " + pedido.getIdCliente() + " - " + pedido.getNomeCliente());
        }
    }

     // Método para mostrar todos os pedidos em preparação.
    public void mostrarPedidosEmPreparacao() {
        System.out.println("Pedidos em Preparação:");
        for (ItemPedido pedido : pedidosEmPreparacao) {
            System.out.println("ID: " + pedido.getId() + " | Nome do pedido: " + pedido.getNomePedido() + " | Quantidade: " + pedido.getQuantidade() + " | Preco: " + pedido.getPrecoPedido() + " | ID e Cliente: " + pedido.getIdCliente() + " - " + pedido.getNomeCliente());
        }
    }

       // Método para mostrar todos os pedidos prontos.
    public void mostrarPedidosProntos() {
        System.out.println("Pedidos Prontos:");
        for (ItemPedido pedido : pedidosProntos) {
            System.out.println("ID: " + pedido.getId() + " | Nome do pedido: " + pedido.getNomePedido() + " | Quantidade: " + pedido.getQuantidade() + " | Preco: " + pedido.getPrecoPedido() + " | ID e Cliente: " + pedido.getIdCliente() + " - " + pedido.getNomeCliente());
        }
    }

    // Métodos getter para acessar os conjuntos de pedidos.
    public Set<ItemPedido> getPedidosPendentes() {
        return pedidosPendentes;
    }

    public Set<ItemPedido> getPedidosEmPreparacao() {
        return pedidosEmPreparacao;
    }

    public Set<ItemPedido> getPedidosProntos() {
        return pedidosProntos;
    }
}
