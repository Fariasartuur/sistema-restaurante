package com.artur.controle;

// Importação da interface Identificacao do pacote com.artur.interfaces
// Esta interface é implementada pela classe ItemCardapio para fornecer um método getId()
import com.artur.interfaces.Identificacao;

public class ItemCardapio implements Identificacao{

    private String nome;
    private final String tamanho;
    private final String descricao;
    private final String categoria;
    private final double preco;
    private Long idItem;

    
    public ItemCardapio(String nome, String tamanho, String descricao, double preco) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.descricao = descricao;
        this.preco = preco;
        // Determina a categoria do item
        this.categoria = definirCategoria(nome);
    }

     // Método privado para determinar a categoria do item com base no nome
    private String definirCategoria(String nome) {

        if (nome.contains("Bife") || nome.contains("Frango") || nome.contains("Spaghetti")) {
            return "Pratos Principais";
        } else if (nome.contains("Arroz") || nome.contains("Batata") || nome.contains("Salada")) {
            return "Acompanhamentos";
        } else if (nome.contains("Refrigerante") || nome.contains("Suco") || nome.contains("Agua")) {
            return "Bebidas";
        } else if (nome.contains("Mousse") || nome.contains("Pudim") || nome.contains("Sorvete")) {
            return "Sobremesas";
        } else {
            return "Outros/Adicionados";
        }

    }

    // Método para representação textual do objeto
    @Override
    public String toString() {
        return nome + ": " + descricao + "\nTamanho: " + tamanho + "\nPreço: R$ " + preco + "\n";
    }

     // Métodos getters e setters para acessar e modificar os atributos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    // Implementação do método da interface Identificacao para obter o ID do item
    @Override
    public Long getId() {
        return idItem;
    }
    
    // Método para definir o ID do item
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

}
