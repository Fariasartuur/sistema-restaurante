//Esta classe 'Mesa' representa uma mesa em um restaurante  
//Ela mantém informações como o identificador da mesa, sua capacidade e o status de reserva.


package com.artur.controle;

import com.artur.interfaces.Identificacao;

public class Mesa implements Identificacao{

    private Long idMesa;// Identificador único da mesa
    private int capacidade; // Capacidade máxima de pessoas na mesa
    private boolean statusMesa; // Status da mesa (reservada ou não)

    public Mesa(int capacidade) {
        this.capacidade = capacidade; // Define a capacidade da mesa
        this.statusMesa = false; // Inicialmente, a mesa não está reservada
    }

    // Método getter para verificar o status da mesa
    public boolean isStatusMesa() {
        return statusMesa;
    }

     // Método para reservar a mesa
    public void reservar() {
        statusMesa = true; // Define o status da mesa como reservada
    }

    // Implementação do método da interface Identificacao para obter o ID da mesa
    @Override
    public Long getId() {
        return idMesa;
    }

    // Métodos getters e setters para obter e definir a capacidade da mesa
    public int getCapacidade() {
        return capacidade;
    }

     // Métodos getters e setters para obter e definir o ID da mesa
    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    // Método setter para definir o status da mesa
    public void setStatusMesa(boolean statusMesa) {
        this.statusMesa = statusMesa;
    }

}
