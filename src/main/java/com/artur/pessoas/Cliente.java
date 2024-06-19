

package com.artur.pessoas;

import com.artur.interfaces.Identificacao;

// Classe Cliente, que é uma subclasse de Pessoa e implementa a interface Identificacao.
public class Cliente extends Pessoa implements Identificacao{

    private String dataNasc;
    private Long idCliente;

    //  O construtor Cliente recebe o nome, endereço, telefone e data de nascimento do cliente e inicializa
    //  esses atributos utilizando o construtor da classe Pessoa (superclasse).
    public Cliente(String nome, String endereco, String telefone, String dataNasc) {
        super(nome, endereco, telefone);
        this.dataNasc = dataNasc;
    }

    // O método getId() sobrescrito retorna o identificador único do cliente.
    @Override
    public Long getId() {
        return idCliente;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }


}
