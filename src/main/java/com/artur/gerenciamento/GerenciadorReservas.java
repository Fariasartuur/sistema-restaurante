package com.artur.gerenciamento;
 
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.artur.controle.Reserva;
import com.artur.interfaces.Listagem;
import com.artur.controle.Mesa;
import com.artur.pessoas.Garcom;

public class GerenciadorReservas implements Listagem {

    private static final Map<Long, Reserva> listaReservas = new LinkedHashMap<>(); // Lista de reservas
    private static Long countIdReserva = 0L; // Contador para o ID das reservas


    // Método para escolher um garçom disponível
    public Garcom escolherGarcom() {

        if (GerenciadorDePessoas.getListaGarcom() == null || GerenciadorDePessoas.getListaGarcom().isEmpty()) {
            System.out.println("A lista de garcons está vazia.");
            return null;
        }

         // Percorre a lista de garçons e retorna o primeiro disponível
        for (Garcom g : GerenciadorDePessoas.getListaGarcom().values()) {
            if (!g.isOcupado()) {
                g.setOcupado(true);
                System.out.println("Garcom escolhido: ID " + g.getId());
                System.out.println("Nome do Garcom: " + g.getNome());
                return g;
            }
        }

           
        System.out.println("Nao existe garcom disponivel");  
        return null;

    }

    // Método protegido para adicionar uma reserva
    protected void adicionarReserva(Reserva reserva) {

        countIdReserva++;
        reserva.setIdReserva(countIdReserva); // Define o ID da reserva e incrementa o contador
        listaReservas.put(reserva.getId(), reserva); // Adiciona a reserva à lista
    }

    // Método protegido para cancelar uma reserva
    protected void cancelarReserva(Long IdReserva, Map<Long, Mesa> listaMesas, Map<Long, Garcom> listaGarcom) {
        // Procurar pela reserva com o ID especificado
        Reserva reservaRemover = listaReservas.get(IdReserva);

        // Se a reserva nao for encontrada, retorna
        if(reservaRemover == null){
            System.out.println("Reserva não encontrada.");
            return;
        }

        // Atualizar o status do garcom correspondente usando o Map listaGarcom
        Garcom garcom = listaGarcom.get(reservaRemover.getIdGarcom());
        if (garcom != null) {
            garcom.setOcupado(false);
        } else {
            System.out.println("Garçom não encontrado para a reserva.");
        }


        // Atualizar o status da mesa correspondente
        for (Mesa m : listaMesas.values()) {
            if (Objects.equals(m.getId(), reservaRemover.getIdMesa())) {
                m.setStatusMesa(false);
                break;
            }
        }

        // Remover a reserva da lista de reservas
        listaReservas.remove(IdReserva);
        System.out.println("Reserva removida com sucesso.");

        // Atualizar os IDs das reservas restantes
        Long novoId = 1L;
        Map<Long, Reserva> novoMapReservas = new LinkedHashMap<>();

        for(Reserva reserva : listaReservas.values()){
            reserva.setIdReserva(novoId);
            novoMapReservas.put(novoId, reserva);
            novoId++;
        }

        // Substitui o antigo mapa com IDs reorganizados
        listaReservas.clear();
        listaReservas.putAll(novoMapReservas);

        // Atualiza o contador de IDs
        countIdReserva = novoId;

    }

    @Override
    public void listar() {
         // Lista todas as reservas
        for (Reserva reserva : listaReservas.values()) {
            System.out.println("ID: " + reserva.getId() + " | Num. Mesa: " + reserva.getIdMesa() + " | Nome e ID do Gargom: "
                    + reserva.getIdGarcom() + " - " + reserva.getNomeGarcom() + " | Nome do Cliente: "
                    + reserva.getNomeCliente() + " | Telefone: " + reserva.getTelefoneCliente() + " | Data e Hora: "
                    + reserva.getDataReserva() + " - " + reserva.getHoraReserva());

        }

    }

    // Método para obter a lista de reservas
    public static Map<Long, Reserva> getListaReservas() {
        return listaReservas;
    }

}
