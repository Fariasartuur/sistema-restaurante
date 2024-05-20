package com.artur.Main;

public class ItemCardapio{
	
	private String nome;
	private String tamanho;
	private String descricao;
	private double preco;
	private String categoria;
	
	public ItemCardapio(String nome, String tamanho, String descricao, double preco) {
		this.nome = nome;
		this.tamanho = tamanho;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = definirCategoria(nome);
	}
	
	private String definirCategoria(String nome) {
		
		if (nome.contains("Bife") || nome.contains("Frango") || nome.contains("Spaghetti")) {
            return "Pratos Principais";
        } else if (nome.contains("Arroz") || nome.contains("Batata") || nome.contains("Salada")) {
            return "Acompanhamentos";
        } else if (nome.contains("Refrigerante") || nome.contains("Suco") || nome.contains("Água")) {
            return "Bebidas";
        } else if (nome.contains("Mousse") || nome.contains("Pudim") || nome.contains("Sorvete")) {
            return "Sobremesas";
        } else {
            return "Outros";
        }
		
	}
	
	
	@Override
	public String toString() {
		return nome + ": " + descricao + "\nTamanho: " + tamanho + "\nPreço: R$ " + preco + "\n";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	

}
