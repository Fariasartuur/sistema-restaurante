package com.artur.gerenciamento;

import java.util.LinkedHashMap;
import java.util.Map;

import com.artur.controle.Mesa;
import com.artur.interfaces.Listagem;


public class GerenciadorMesas implements Listagem {

    private static final Map<Long, Mesa> listaMesas = new LinkedHashMap<>();
    private static Long countIdMesas = 0L;  // Contador global para os IDs das mesas

     // Método protegido para criar mesas iniciais
    protected void criarMesa() {
        adicionarMesa(new Mesa(2)); // Adiciona uma mesa com capacidade para 2 pessoas
        adicionarMesa(new Mesa(4)); // Adiciona uma mesa com capacidade para 4 pessoas
        adicionarMesa(new Mesa(4)); // Adiciona uma mesa com capacidade para 4 pessoas
        adicionarMesa(new Mesa(6)); // Adiciona uma mesa com capacidade para 6 pessoas
        adicionarMesa(new Mesa(8)); // Adiciona uma mesa com capacidade para 8 pessoas

    }

    // Método protegido para adicionar uma mesa à lista
    protected void adicionarMesa(Mesa mesa) {

        countIdMesas++;
        mesa.setIdMesa(countIdMesas); // Define o ID da mesa e incrementa o contador global
        listaMesas.put(mesa.getId(), mesa);  // Adiciona a mesa à lista de mesas
    }

    // Método protegido para remover uma mesa da lista
    protected void removerMesa(Long canIdMesa){
        Mesa mesaRemover = listaMesas.get(canIdMesa);

        // Procurar pela Mesa com o ID especificado
        if(mesaRemover != null){
            if(mesaRemover.isStatusMesa()){ // Verifica se a mesa está ocupada
                System.out.println("Mesa ocupada, libere a reserva antes de remover esta mesa.");
            } else{
                // Remover a mesa da lista de mesas
                listaMesas.remove(canIdMesa);
                System.out.println("Mesa removida com sucesso.");

                // Atualiza os IDs da lista
                Long novoId = 1L;
                Map<Long, Mesa> novoMapMesas = new LinkedHashMap<>();

                for (Mesa mesa : listaMesas.values()) {
                    mesa.setIdMesa(novoId);
                    novoMapMesas.put(novoId, mesa);
                    novoId++;
                }

                // Substitui o antigo mapa com IDs reorganizados
                listaMesas.clear();
                listaMesas.putAll(novoMapMesas);

                // Atualiza o contador de IDs
                countIdMesas = novoId;
            }
        } else{
            System.out.println("Mesa não encontrado.");
        }
    }

    public void listar() {

         // Lista todas as mesas
        System.out.println("==================== MESAS ====================\n");
        for (Mesa mesas : listaMesas.values()) {
            System.out.print("Num. Mesa: " + mesas.getId() + " | Capacidade da Mesa: " + mesas.getCapacidade());
            if (mesas.isStatusMesa()) {
                System.out.println(" | (Ocupado)");
            } else {
                System.out.println(" | (Livre)");
            }

        }
        System.out.println();

    }

     // Método para obter a lista de mesas
    public static Map<Long, Mesa> getListaMesas() {
        return listaMesas;
    }

    // Método para obter o contador global
    public Long getCountIdMesas() {
        return countIdMesas;
    }
}
