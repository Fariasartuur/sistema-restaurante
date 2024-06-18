package com.artur.pessoas;

import com.artur.interfaces.Identificacao;

public class Gerente extends Pessoa implements Identificacao{

    private Long idGerente;
    private float salario;
    private static Gerente gerenteAtual;

    public Gerente(String nome, String endereco, String telefone, float salario) {
        super(nome, endereco, telefone);
        this.salario = salario;
    }

    @Override
    public Long getId() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    public static void setGerenteAtual(Gerente gerente) {
        gerenteAtual = gerente;
    }

    public static Gerente getGerenteAtual() {
        return gerenteAtual;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
}