package com.artur.pessoas;

public class Pessoa {

    private String nome;
    private String endereco;
    private String telefone;

    // Construtor que inicializa nome, endereço e telefone
    public Pessoa(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Construtor alternativo que inicializa apenas o nome
    public Pessoa(String nome){
        this.nome = nome;
    }

    // Métodos getters e setters para os atributos privados

    public String getNome() {
        return nome;
    }

    // Define o nome da pessoa
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o endereço da pessoa
    public String getEndereco() {
        return endereco;
    }

    // Define o endereço da pessoa
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // Retorna o telefone da pessoa
    public String getTelefone() {
        return telefone;
    }

    // Define o telefone da pessoa
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
