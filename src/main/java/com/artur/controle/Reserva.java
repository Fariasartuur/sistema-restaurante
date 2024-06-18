package com.artur.controle;

import com.artur.interfaces.Identificacao;
import com.artur.pessoas.Garcom;

public class Reserva implements Identificacao{

     // Declaração dos atributos da classe
    private Long idReserva;// Identificador único da reserva
    private final Long idGarcom; // Identificador do garçom associado à reserva
    private final Long idCliente; // Identificador do cliente que fez a reserva
    private final String nomeGarcom; // Nome do garçom associado à reserva
    private Long idMesa; // Número da mesa reservada
    private String telefoneCliente; // Telefone do cliente
    private String nomeCliente; // Nome do cliente
    private String dataReserva; // Data da reserva
    private String horaReserva; // Hora da reserva



    // Construtor da classe
    public Reserva(String dataReserva, String horaReserva, String nomeCliente, String telefoneCliente, Long idMesa, Garcom g, Long idCliente) {
        this.idGarcom = g.getId(); // Obtém o ID do garçom
        this.idCliente = idCliente; // Define o ID do cliente
        this.nomeGarcom = g.getNome(); // Obtém o nome do garçom
        this.idMesa = idMesa; // Define o número da mesa
        this.telefoneCliente = telefoneCliente; // Define o telefone do cliente
        this.nomeCliente = nomeCliente; // Define o nome do cliente
        this.horaReserva = horaReserva; // Define a hora da reserva
        this.dataReserva = dataReserva; // Define a data da reserva

    }



    // Implementação do método da interface Identificacao para obter o ID da reserva
    @Override
    public Long getId() {
        return idReserva;
    }

    // Métodos getters para obter os atributos da classe
    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public Long getIdGarcom() {
        return idGarcom;
    }

    public String getNomeGarcom() {
        return nomeGarcom;
    }

    public Long getIdCliente() {
        return idCliente;
    }


    // Métodos setters para modificar os atributos da classe
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

}
