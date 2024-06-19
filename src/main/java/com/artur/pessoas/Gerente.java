package com.artur.pessoas;

import com.artur.interfaces.Identificacao;

public class Gerente extends Pessoa implements Identificacao{

    // Atributos privados da classe Gerente
    private Long idGerente;
    private float salario;
    private static Gerente gerenteAtual;

    // Construtor da classe Gerente
    public Gerente(String nome, String endereco, String telefone, float salario) {
        super(nome, endereco, telefone);
        this.salario = salario;
    }
    
    // Método da interface Identificacao para retornar o ID do gerente
    @Override
    public Long getId() {
        return idGerente;
    }

    // Método para definir o ID do gerente
    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    // Método estático para definir o gerente atual
    public static void setGerenteAtual(Gerente gerente) {
        gerenteAtual = gerente;
    }

    // Método estático para obter o gerente atual
    public static Gerente getGerenteAtual() {
        return gerenteAtual;
    }

    // Método para obter o salário do gerente
    public float getSalario() {
        return salario;
    }

    // Método para definir o salário do gerente
    public void setSalario(float salario) {
        this.salario = salario;
    }
}
