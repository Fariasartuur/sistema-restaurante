package com.artur.controle;

import com.artur.gerenciamento.GerenciadorCozinha;
import com.artur.gerenciamento.GerenciadorDePessoas;
import com.artur.interfaces.Listagem;
import com.artur.interfaces.MetodosPagamento;
import com.artur.pessoas.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Caixa implements MetodosPagamento, Listagem {
    
    private final List<ItemPedido> pedidosPagos;
    
    public Caixa(){
        this.pedidosPagos = new ArrayList<>();
    }
    
    public void listarPedidosParaPagar(GerenciadorCozinha cozinha, Long idCliente){
        boolean checar = false;

        // Verifica se o ID do cliente fornecido é válido
        Cliente cliente = GerenciadorDePessoas.getListaClientes().get(idCliente);
        if (cliente != null) {
            checar = true;
        }

        if (!checar) {
            System.out.println("ID DE CLIENTE INVALIDO.");
            return;
        }
        
       // Lista temporária para armazenar os pedidos do cliente
        List<ItemPedido> listaTemp = new ArrayList<>();

        // Este Filtra os pedidos prontos da cozinha para enviar ao cliente
        for (ItemPedido p : cozinha.getPedidosProntos()) {
            if (p.getIdCliente().equals(idCliente)) {
                listaTemp.add(p);
            }
        }

        double total = 0;
        System.out.println("========== SEU PEDIDO ==========");
        // Exibe os detalhes dos pedidos do cliente
        for (ItemPedido pedido : listaTemp) {
            System.out.println("ID: " + pedido.getId() + " | Nome: " + pedido.getNomePedido() + " | Quantidade: " + pedido.getQuantidade() + " | Preço: R$ " + pedido.getPrecoPedido() + " | Cliente ID e Nome: " + pedido.getIdCliente() + " - " + pedido.getNomeCliente());
            total += pedido.getPrecoPedido() * pedido.getQuantidade();
        }
        System.out.println("Total: R$ " + total);
        System.out.println("==========================");

    }

    // Método para listar os pedidos pagos
    public void listar(){
        for(ItemPedido p : pedidosPagos){
            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNomePedido() + " | Quantidade: " + p.getQuantidade() + " | Preço: R$ " + p.getPrecoPedido() + " | Cliente ID e Nome: " + p.getIdCliente() + " - " + p.getNomeCliente());

        }
    }

    public List<ItemPedido> getPedidosPagos() {
        return pedidosPagos;
    }

    public abstract void fazerPagamento(Scanner sc);
}
