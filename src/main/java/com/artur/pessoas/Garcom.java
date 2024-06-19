//Este código define a classe Garcom, que herda da classe Pessoa e implementa a interface Identificacao. Ela possui os seguintes atributos privados: dataContratacao para armazenar a data de contratação formatada automaticamente no construtor, salario para o salário do garçom, idGarcom para o identificador único do garçom, e ocupado para indicar se o garçom está ocupado ou não.

package com.artur.pessoas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.artur.interfaces.Identificacao;

public class Garcom extends Pessoa implements Identificacao{

    private final String dataContratacao;
    private float salario;
    private Long idGarcom;
    private boolean ocupado;

    public Garcom(String nome, String endereco, String telefone, float salario) {
        super(nome, endereco, telefone);
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = hoje.format(formatador);

        this.salario = salario;
        this.dataContratacao = dataFormatada;
    }

    public float getSalario() {
        return salario;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    @Override
    public Long getId() {
        return idGarcom;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setIdGarcom(Long idGarcom) {
        this.idGarcom = idGarcom;
    }

    public boolean isOcupado() { return ocupado; }

    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }

}
