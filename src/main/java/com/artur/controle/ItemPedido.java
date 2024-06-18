// Importação das classes necessárias do pacote com.artur
// Este pacote contém as implementações de controle, interfaces e definições de pessoas do sistema
package com.artur.controle;


// Importação da interface Identificacao do pacote com.artur.interfaces
// Esta interface é implementada pela classe ItemPedido para fornecer um método getId()
import com.artur.interfaces.Identificacao;
// Importação da classe Cliente do pacote com.artur.pessoas
// Esta classe é utilizada no construtor de ItemPedido para associar um cliente ao pedido
import com.artur.pessoas.Cliente;

public class ItemPedido implements Identificacao{

    private final String nomePedido;
    private final float precoPedido;
    private Long idPedido;
    private final Long idItemCardapio;
    private int quantidade;
    private final Long idCliente;
    private final String nomeCliente;

    public ItemPedido(Long idPedido, String nomePedido, int quantidade, float precoPedido, Cliente cliente) {
        this.nomePedido = nomePedido;
        this.precoPedido = precoPedido;
        this.quantidade = quantidade;
        this.idItemCardapio = idPedido;
        this.idCliente = cliente.getId();
        this.nomeCliente = cliente.getNome();
    }

     // Método getter para obter o nome do pedido
    public String getNomePedido() {
        return nomePedido;
    }

     // Implementação do método da interface Identificacao para obter o ID do item
    @Override
    public Long getId() {
        return this.idPedido;
    }

    // Método setter para definir o ID do pedido
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    // Métodos getters e setters para obter e definir a quantidade do pedido
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

     // Métodos getters para obter o preço do pedido e o identificador do item do cardápio associado ao pedido
    public float getPrecoPedido() {
        return precoPedido;
    }

    public Long getIdItemCardapio() {
        return idItemCardapio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
}
