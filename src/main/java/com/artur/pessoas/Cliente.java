package com.artur.pessoas;

import com.artur.interfaces.Identificacao;

public class Cliente extends Pessoa implements Identificacao{

    private String dataNasc;
    private Long idCliente;

    public Cliente(String nome, String endereco, String telefone, String dataNasc) {
        super(nome, endereco, telefone);
        this.dataNasc = dataNasc;
    }

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