package com.artur.gerenciamento;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.artur.interfaces.GerenciamentoPessoas;
import com.artur.pessoas.*;



public class GerenciadorDePessoas implements GerenciamentoPessoas {

    // Listas para armazenar clientes, garçons e gerentes.
    private static final Map<Long, Cliente> listaClientes = new LinkedHashMap<>();
    private static final Map<Long, Garcom> listaGarcom = new LinkedHashMap<>();
    private static final Map<Long, Gerente> listaGerente = new LinkedHashMap<>();

     // Variáveis estáticas para gerar IDs únicos para clientes, garçons e gerentes.
    private static Long countIdCliente = 0L;
    private static Long countIdGarcom = 0L;
    private static Long countIdGerente = 0L;


    // Construtor que inicializa as listas e gera garçons.
    public GerenciadorDePessoas() {
        gerarGarcom();
    }

    // Método para gerar garçons pré-definidos.
    public void gerarGarcom() {
        adicionarGarcom(new Garcom("Thiago", "Capao Redondo", "1234-5678", 1412));
        adicionarGarcom(new Garcom("Ana", "Av. Paulista, 100", "9876-5432", 1413));
        adicionarGarcom(new Garcom("Carlos", "Rua Augusta, 200", "8765-4321", 1414));
        adicionarGarcom(new Garcom("Maria", "Av. Faria Lima, 300", "7654-3210", 1415));
        adicionarGarcom(new Garcom("Pedro", "Rua Oscar Freire, 400", "6543-2109", 1416));
    }

    @Override
    public void adicionarCliente(Cliente cliente) {
        // Define o id do cliente e o adiciona a lista de cliente
        countIdCliente++;
        cliente.setIdCliente(countIdCliente);
        listaClientes.put(cliente.getId(), cliente);
    }

    @Override
    public void removerCliente(Long idCliente) {
        Cliente clienteRemover = listaClientes.get(idCliente);

        // Verifica se o id do cliente existe e o remove
        if(clienteRemover != null){
            listaClientes.remove(idCliente);
            System.out.println("Cliente removido com sucesso.");

            // Atualiza os IDs da lista
            Long novoId = 1L;
            Map<Long, Cliente> novoMapClientes = new LinkedHashMap<>();

            for (Cliente cliente : listaClientes.values()) {
                cliente.setIdCliente(novoId);
                novoMapClientes.put(novoId, cliente);
                novoId++;
            }

            // Substitui o antigo mapa com IDs reorganizados
            listaClientes.clear();
            listaClientes.putAll(novoMapClientes);

            // Atualiza o contador de IDs
            countIdCliente = novoId;
        } else{
            System.out.println("Cliente não encontrado.");
        }

    }

    @Override
    public void listarCliente() {
        for (Cliente cliente : listaClientes.values()) {
            System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome() + " | Endereco: " + cliente.getEndereco() + " | Telefone: " + cliente.getTelefone() + " | Data de Nascimento: " + cliente.getDataNasc());
        }
    }

    @Override
    public void adicionarGarcom(Garcom garcom) {
        // Define o id do garçom e o adiciona a lista de garçons
        countIdGarcom++;
        garcom.setIdGarcom(countIdGarcom);
        listaGarcom.put(garcom.getId(), garcom);
    }

    @Override
    public void removerGarcom(Long idCarcom) {
        Garcom garcomRemover = listaGarcom.get(idCarcom);

        // Verifica se o id do garçom existe e o remove
        if(garcomRemover != null){
            listaGarcom.remove(idCarcom);
            System.out.println("Garçom removido com sucesso.");

            // Atualiza os IDs da lista
            Long novoId = 1L;
            Map<Long, Garcom> novoMapGarcom = new LinkedHashMap<>();

            for(Garcom garcom : listaGarcom.values()){
                garcom.setIdGarcom(novoId);
                novoMapGarcom.put(novoId, garcom);
                novoId++;
            }

            // Substitui o antigo mapa com IDs reorganizados
            listaGarcom.clear();
            listaGarcom.putAll(novoMapGarcom);

            // Atualiza o contador de IDs
            countIdGarcom = novoId;
        } else{
            System.out.println("Garçom não encontrado.");
        }

    }

    @Override
    public void listarGarcom() {

        for (Garcom garcom : listaGarcom.values()) {
            System.out.print("ID: " + garcom.getId() + " | Nome: " + garcom.getNome() + " | Endereco: " + garcom.getEndereco() + " | Telefone: " + garcom.getTelefone() + " | Salario: " + garcom.getSalario() + " | Data de Contratacao: " + garcom.getDataContratacao());
            if (garcom.isOcupado()) {
                System.out.println(" | (Ocupado) ");
            } else {
                System.out.println(" | (Livre) ");
            }
        }
    }

    @Override
    public void adicionarGerente(Gerente gerente) {
        // Adiciona um gerente se não houver nenhum atualmente.
        if (Gerente.getGerenteAtual() == null) { // Se não existir um gerente, adiciona um.
            countIdGerente++;
            gerente.setIdGerente(++countIdGerente);
            Gerente.setGerenteAtual(gerente);
            listaGerente.put(gerente.getId(), gerente);
            System.out.println("Gerente adicionado com sucesso.");
        } else {
            System.out.println("Já existe um gerente no restaurante.");
        }
    }

    @Override
    public void removerGerente(Long idGerente) {
        Gerente gerenteRemover = listaGerente.get(idGerente);

  // Remove o gerente se o id fornecido corresponder ao id do gerente.
        if(gerenteRemover != null){
            listaGerente.remove(idGerente);
            Gerente.setGerenteAtual(null);
            System.out.println("Gerente removido com sucesso.");
        } else{
            System.out.println("Gerente não encontrado com o ID fornecido.");
        }


    }

    @Override
    public void listarGerente() {
     // Lista o gerente atual.
        if (Gerente.getGerenteAtual() != null) {
            Gerente gerenteAtual = Gerente.getGerenteAtual();
            System.out.println("Gerente atual: " + gerenteAtual.getNome());
        } else {
            System.out.println("Não há gerente no restaurante atualmente.");
        }
    }

   // Métodos para obter as listas de clientes, garçons e gerentes.


    public static Map<Long, Cliente> getListaClientes() { return listaClientes; }

    public static Map<Long, Garcom> getListaGarcom() { return listaGarcom; }

    public static Map<Long, Gerente> getListaGerente() { return listaGerente; }

}
