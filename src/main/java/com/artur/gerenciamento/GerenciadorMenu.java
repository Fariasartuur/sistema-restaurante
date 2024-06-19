package com.artur.gerenciamento;
//  itemcardapio
import java.util.*;

import com.artur.controle.*;
import com.artur.pessoas.Cliente;
import com.artur.pessoas.Garcom;
import com.artur.pessoas.Gerente;

//OBS: as funções inserirInt, inserirDouble, inserirLong e inserirFloat, são funções que pega a entrada do usuário verifica se é um int ou double e retorna ela, se não for ele pede
//que o usuário insira de novo até acertar (essas funções evitam que o config pare se um valor diferente do que é requisitado seja inserido).

public class GerenciadorMenu {

    GerenciadorCozinha cozinha;
    GerenciadorPedidos pedidos;
    GerenciadorCardapio cardapio;
    GerenciadorMesas mesa;
    GerenciadorReservas reserva;
    GerenciadorDePessoas pessoa;
    Caixa caixa;
    Restaurante restaurante;

    public GerenciadorMenu() {
        this.pedidos = new GerenciadorPedidos();
        this.cozinha = new GerenciadorCozinha();
        this.cardapio = new GerenciadorCardapio();
        this.restaurante = new Restaurante();
        this.pessoa = new GerenciadorDePessoas();
        this.mesa = new GerenciadorMesas();
        this.reserva = new GerenciadorReservas();
        this.caixa = new Pagamento();
        mesa.criarMesa();
    }

    private void voltandoAnimacao(){
        try {
            // Inicia um temporizador para simular uma animação de carregamento
            long startTime = System.currentTimeMillis();
            long duration = 1000; // Duração da animação em milissegundos

            // Loop para exibir a animação
            while (System.currentTimeMillis() - startTime < duration) {
                System.out.print("\rVoltando.  "); // Exibe uma parte da animação
                Thread.sleep(500); // Aguarda 500 milissegundos
                System.out.print("\rVoltando.. "); // Exibe outra parte da animação
                Thread.sleep(500); // Aguarda 500 milissegundos
                System.out.print("\rVoltando...");// Exibe outra parte da animação
                Thread.sleep(500); // Aguarda 500 milissegundos
            }
            System.out.println(); // Quebra de linha após a animação
        } catch (InterruptedException e) {
            e.printStackTrace(); // Imprime informações sobre a exceção
        }
    }

    public int inserirInt(Scanner scanner) {
        int num = 0;
        boolean valido = false;

        while (!valido) {
            try {
                num = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero valido");
                scanner.next();
            }
        }

        return num;
    }

    public float inserirFloat(Scanner scanner) {
        float num = 0;
        boolean valido = false;

        while (!valido) {
            try {
                num = scanner.nextFloat();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero valido");
                scanner.next();
            }
        }

        return num;
    }

    public Long inserirLong(Scanner scanner){
        Long num = null;
        boolean valido = false;

        while (!valido) {
            try {
                num = scanner.nextLong();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero valido");
                scanner.next();
            }
        }

        return num;
    }

    public double inserirDouble(Scanner scanner) {
        double num = 0;
        boolean valido = false;

        while (!valido) {
            try {
                num = scanner.nextDouble();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero valido");
                scanner.next();
            }
        }

        return num;
    }

    public void imprimirCategorias() {
        System.out.println("1 - Pratos Principais");
        System.out.println("2 - Acompanhamentos");
        System.out.println("3 - Bebidas");
        System.out.println("4 - Sobremesas");
        System.out.println("5 - Outros");
    }

    // Menu de Atendimento

    public void menuAtendimento(Scanner sc) {
        int opcao1;
        Cliente cliente = null;

        do {
            System.out.println("\n==================== Atendimento ====================");
            System.out.println("\nOla!! Bem vindo ao " + restaurante.getNOME_RESTAURANTE() + ".");
            System.out.println("Para iniciar o atendimento, primeiro precisamos fazer seu cadastro.\n");
            System.out.println("Escolha uma das opções abaixo.");
            System.out.println("1 - Já tenho cadastro");
            System.out.println("2 - Realizar Cadastro");
            System.out.println("0 - Voltar");

            opcao1 = inserirInt(sc);
            sc.nextLine(); // Limpa o buffer do scanner

            switch (opcao1) {
                case 1:
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) { // Verifica se há clientes cadastrados
                        System.out.println("Nenhum Cliente Encontrado. Deseja cadastrar um Cliente? (1)Sim/(2)Nao");

                        int opcaoCliRe = inserirInt(sc); // Lê a opção de cadastrar um novo cliente
                        sc.nextLine(); // Limpa o buffer do scanner

                        if (opcaoCliRe == 1) { // Se o usuário deseja cadastrar um novo cliente
                            cliente = subMenuClienteCadastro(sc); // Chama o submenu para cadastro de cliente
                            opcao1 = 0; // Reseta a opção para sair do loop
                            break;
                        } else if (opcaoCliRe == 2) { // Se o usuário não deseja cadastrar
                            break;
                        } else {
                            System.out.println("Opção invalida. Voltando para o menu anterior...");
                            break;
                        }
                    } else {
                        opcao1 = 0; // Se há clientes cadastrados, sai do loop
                        break;
                    }
                case 2:
                    cliente = subMenuClienteCadastro(sc); // Chama o submenu para cadastro de cliente
                    opcao1 = 0; // Reseta a opção para sair do loop
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    return; // Sai do método
                default:
                    System.out.println("Opcao inserida invalida. Tente Novamente.");
            }
        } while (opcao1 != 0);

        System.out.println("Agora, Vamos fazer sua reserva!");
        if (subMenuReservaFazerReserva(sc)) { // Chama o submenu para fazer reserva
            System.out.println("Reserva feita com sucesso!");
        } else {
            System.out.println("Erro na reserva.");
            return; // Sai do método em caso de erro na reserva
        }

        int opcao2;
        boolean opcao = true;

        System.out.println("Certo, Agora faça seu pedido.");

        do {
            System.out.println("1 - Mostrar o Cardapio");
            System.out.println("2 - Fazer o Pedido");
            if (!cozinha.getPedidosProntos().isEmpty()) {
                System.out.println("3 - Ir Para o Pagamento");
            }

            opcao2 = inserirInt(sc); // Lê a opção escolhida pelo usuário
            sc.nextLine(); // Limpa o buffer do scanner

            switch (opcao2) {
                case 1:
                    cardapio.listar(); // Mostra o cardápio
                    break;
                case 2:
                    menuFazerPedido(cliente, sc); // Chama o menu para fazer pedido
                    break;
                case 3:
                    if (cozinha.getPedidosProntos().isEmpty()) { // Verifica se há pedidos prontos
                        System.out.println("Nenhum pedido foi realizado.");
                        break;
                    } else {
                        if (cliente != null) {
                            subMenuPagamentos(sc, cliente); // Chama o submenu de pagamentos
                        }
                        System.out.println("O atendimento foi finalizado. Obrigado por escolher "
                                + restaurante.getNOME_RESTAURANTE() + "!");
                        caixa.getPedidosPagos().addAll(cozinha.getPedidosProntos()); // Adiciona pedidos prontos aos pagos
                        Cliente finalCliente = cliente;
                        cozinha.getPedidosProntos().removeIf(pedido -> Objects.equals(pedido.getIdCliente(), finalCliente.getId())); // Remove pedidos prontos do cliente atual

                        opcao = false; // Sai do loop de pedidos
                    }
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente."); // Opção inválida
            }
        } while (opcao); // Continua no loop enquanto a flag estiver verdadeira
    }


    // Menu Fazer Pedido

    protected void menuFazerPedido(Cliente cliente, Scanner sc) {
        int opcaoCategoria; // Variável para armazenar a opção da categoria escolhida
        int continuar = 0; // Variável para controlar se o usuário deseja continuar adicionando itens

        do {
            // Exibe as categorias disponíveis para o pedido
            System.out.println("Qual a categoria do pedido?");
            imprimirCategorias();

            opcaoCategoria = inserirInt(sc); // Lê a opção de categoria escolhida
            sc.nextLine(); // Limpa o buffer do scanner

            ItemPedido novoPedido;

            switch (opcaoCategoria) {
                case 1:
                    novoPedido = FazerPedido(cliente, sc, "Pratos Principais");
                    break;
                case 2:
                    novoPedido = FazerPedido(cliente, sc, "Acompanhamentos");
                    break;
                case 3:
                    novoPedido = FazerPedido(cliente, sc, "Bebidas");
                    break;
                case 4:
                    novoPedido = FazerPedido(cliente, sc, "Sobremesas");
                    break;
                case 5:
                    novoPedido = FazerPedido(cliente, sc, "Outros");
                    break;
                default:
                    System.out.println("Opcao inserida invalida. Tente Novamente.");
                    continue; // Continua o loop
            }

            if (novoPedido != null) {
                System.out.println("Deseja continuar (1) ou cancelar o pedido (2)?");

                int continuar1 = inserirInt(sc); // Lê a opção de continuar ou cancelar o pedido
                sc.nextLine(); // Limpa o buffer do scanner

                if (continuar1 == 1) {
                    cozinha.preparar(novoPedido); // Prepara o novo pedido
                } else if (continuar1 == 2) {
                    pedidos.removerItem(novoPedido.getId()); // Remove o item do pedido
                }
            }

            System.out.println("Deseja adicionar mais itens? (1)Sim / (2)Nao");

            continuar = inserirInt(sc); // Lê a opção de adicionar mais itens
            sc.nextLine(); // Limpa o buffer do scanner

        } while (continuar == 1); // Continua o loop se o usuário desejar adicionar mais itens
    }

    // Fazer Pedido

    public ItemPedido FazerPedido(Cliente cliente, Scanner sc, String categoria) {
        ItemPedido novoPedido = null; // Declara um novo pedido
        int quantidade; // Variável para armazenar a quantidade do item
        Long idItem; // Variável para armazenar o ID do item

        List<ItemCardapio> itens = cardapio.getCardapio().get(categoria); // Obtém os itens da categoria

        if (itens.isEmpty()) { // Verifica se há itens na categoria
            System.out.println("Nao ha itens na categoria: " + categoria);
            return null;
        }

        if (cardapio.getCardapio().containsKey(categoria)) {
            if (cardapio.getCardapio().get(categoria).isEmpty()) {
                System.out.println("Desculpe, não há " + categoria + " disponível no momento.");
                return null;
            } else {
                cardapio.imprimirItensCategoria(categoria, cardapio.getCardapio()); // Imprime os itens da categoria
            }
        }

        System.out.println("Digite o ID do item:");
        idItem = inserirLong(sc); // Lê o ID do item

        boolean encontrado = false;
        for (ItemCardapio item : itens) {
            if (Objects.equals(item.getId(), idItem)) {
                encontrado = true;
                System.out.println("Digite a quantidade:");

                quantidade = inserirInt(sc); // Lê a quantidade do item
                sc.nextLine(); // Limpa o buffer do scanner

                // Cria um novo pedido com as informações do item
                novoPedido = new ItemPedido(item.getId(), item.getNome(), quantidade, (float) item.getPreco(), cliente);
                pedidos.adicionarItem(novoPedido); // Adiciona o pedido à lista de pedidos
                break;
            }
        }

        if (!encontrado) { // Verifica se o item foi encontrado
            System.out.println("Item não encontrado na categoria selecionada.");
            return null;
        }

        return novoPedido; // Retorna o novo pedido
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Menu de Administração

    public void menuAdministracao(Scanner sc) {
        int menuAdministracao; // Variável para armazenar a opção do menu de administração

        do {
            System.out.println("========== ADMINISTRACAO ==========");
            System.out.println("1 - Recepcao");
            System.out.println("2 - Cadastros");
            System.out.println("3 - Cardapio");
            System.out.println("4 - Cozinha");
            System.out.println("0 - Voltar");
            System.out.println("===================================");

            menuAdministracao = inserirInt(sc);

            switch (menuAdministracao) {
                case 1:
                    menuRecepcao(sc); // Chama o menu de recepção
                    break;
                case 2:
                    menuCadastros(sc); // Chama o menu de cadastros
                    break;
                case 3:
                    menuCardapio(sc); // Chama o menu do cardápio
                    break;
                case 4:
                    menuCozinha(sc); // Chama o menu da cozinha
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opcao inserida invalida. Tente Novamente.");
            }

        } while (menuAdministracao != 0); // Continua no loop enquanto a opção não for 0
    }

    // Menu da Recepção

    public void menuRecepcao(Scanner sc) {
        int opcaoRecepcao; // Variável para armazenar a opção do menu de recepção

        do {
            System.out.println("========== RECEPÇÃO ==========");
            System.out.println("1 - Caixa");
            System.out.println("2 - Reservas");
            System.out.println("3 - Mesas");
            System.out.println("0 - VOLTAR");
            System.out.println("==============================");

            opcaoRecepcao = inserirInt(sc);

            switch (opcaoRecepcao) {
                case 1:
                    menuCaixa(sc); // Chama o menu do caixa
                    break;
                case 2:
                    menuReservas(sc); // Chama o menu de reservas
                    break;
                case 3:
                    menuMesas(sc); // Chama o menu de mesas
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }
        } while (opcaoRecepcao != 0); // Continua no loop enquanto a opção não for 0
    }

    // Menu do Caixa

    public void menuCaixa(Scanner sc) {
        int opcaoCaixa; // Variável para armazenar a opção do menu do caixa

        do {
            System.out.println("========== CAIXA ==========");
            System.out.println("1 - Consultar listas dos pedidos");
            System.out.println("2 - Registrar pagamento");
            System.out.println("3 - Relatório de vendas");
            System.out.println("0 - VOLTAR");
            System.out.println("==============================");

            opcaoCaixa = inserirInt(sc);

            switch (opcaoCaixa) {
                case 1:
                    // Verifica e exibe o estado dos pedidos
                    if (cozinha.getPedidosPendentes().isEmpty()) {
                        System.out.println("Pedidos Pendentes esta vazio.");
                    } else {
                        cozinha.mostrarPedidosPendentes();
                    }

                    if (cozinha.getPedidosEmPreparacao().isEmpty()) {
                        System.out.println("Pedidos em Preparação esta vazio.");
                    } else {
                        cozinha.mostrarPedidosEmPreparacao();
                    }

                    if (cozinha.getPedidosProntos().isEmpty()) {
                        System.out.println("Pedidos Prontos esta vazio.");
                    } else {
                        cozinha.mostrarPedidosProntos();
                    }
                    break;
                case 2:
                    pessoa.listarCliente(); // Lista os clientes

                    List<ItemPedido> listaPedidos = new ArrayList<>(); // Cria uma lista de pedidos

                    System.out.println("Insira o ID do cliente: ");

                    Long idCliente = inserirLong(sc); // Lê o ID do cliente
                    sc.nextLine(); // Limpa o buffer do scanner

                    // Adiciona os pedidos prontos do cliente na lista de pedidos
                    if (!cozinha.getPedidosProntos().isEmpty()) {
                        for (ItemPedido p : cozinha.getPedidosProntos()) {
                            if (Objects.equals(p.getIdCliente(), idCliente)) {
                                listaPedidos.add(p);
                                break;
                            }
                        }
                    } else {
                        System.out.println("Nao existem pedidos com pagamentos pendentes.");
                    }

                    // Processa o pagamento se houver pedidos na lista
                    if (!listaPedidos.isEmpty()) {
                        caixa.listarPedidosParaPagar(cozinha, idCliente);
                        caixa.fazerPagamento(sc);
                        caixa.getPedidosPagos().addAll(cozinha.getPedidosProntos());
                        cozinha.getPedidosProntos().removeIf(p -> Objects.equals(p.getIdCliente(), idCliente));
                    }

                    break;
                case 3:
                    // Verifica se há pedidos pagos e lista ou avisa se não houver
                    if (caixa.getPedidosPagos().isEmpty()) {
                        System.out.println("Nao existem pedidos pagos.");
                    } else {
                        caixa.listar();
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }
        } while (opcaoCaixa != 0); // Continua no loop enquanto a opção não for 0
    }


    // Sub Menu Pagamentos

    public void subMenuPagamentos(Scanner sc, Cliente cliente) {
        caixa.listarPedidosParaPagar(cozinha, cliente.getId()); // Lista os pedidos para pagar
        caixa.fazerPagamento(sc); // Realiza o pagamento
    }

    // Menu de Reservas

    public void menuReservas(Scanner sc) {
        int opcaoReserva; // Variável para armazenar a opção do menu de reservas

        do {
            System.out.println("========== RESERVAS ==========");
            System.out.println("1 - Listar Reservas");
            System.out.println("2 - Fazer Reserva");
            System.out.println("3 - Cancelar Reserva");
            System.out.println("4 - Modificar Reserva");
            System.out.println("0 - VOLTAR");
            System.out.println("==============================");

            opcaoReserva = inserirInt(sc);

            switch (opcaoReserva) {
                case 1:
                    // Listar Reservas se exixtir
                    if (GerenciadorReservas.getListaReservas().isEmpty()) {
                        System.out.println("Nenhuma reserva registrada.");
                    } else {
                        reserva.listar();
                    }
                    break;
                case 2:
                    // Fazer Reserva
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) {
                        System.out.println("Nenhum Cliente Encontrado. Deseja cadastrar um Cliente? (1)Sim/(2)Nao");
                        int opcaoCliRe = inserirInt(sc);

                        if (opcaoCliRe == 1) {
                            menuClientes(sc); // Chama o menu de clientes para cadastro
                            break;
                        } else if (opcaoCliRe == 2) {
                            break;
                        } else {
                            System.out.println("Opcao invalida. Voltando para o menu anterior...");
                            break;
                        }
                    }

                    if (subMenuReservaFazerReserva(sc)) {
                        System.out.println("Reserva feita com sucesso.");
                    } else {
                        System.out.println("Erro na reserva.");
                    }
                    break;
                case 3:
                    // Cancelar Reserva
                    if (GerenciadorReservas.getListaReservas().isEmpty()) {
                        System.out.println("Nenhuma Reserva Registrada");
                        break;
                    }

                    System.out.println("========== Cancelar Reserva ==========");
                    reserva.listar();

                    System.out.println("\nInsira o ID da reserva: ");
                    Long canIdReserva = inserirLong(sc);

                    reserva.cancelarReserva(canIdReserva, GerenciadorMesas.getListaMesas(), GerenciadorDePessoas.getListaGarcom());
                    break;
                case 4:
                    // Modificar Reserva
                    if (GerenciadorReservas.getListaReservas().isEmpty()) {
                        System.out.println("Nenhuma Reserva Registrada");
                        break;
                    }

                    System.out.println("========== Modificar Reserva ==========");
                    reserva.listar();

                    System.out.println("\nInsira o ID da Reserva: ");
                    Long modIdReserva = inserirLong(sc);

                    Reserva novaReserva = GerenciadorReservas.getListaReservas().get(modIdReserva);

                    if(novaReserva == null){
                        System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
                        break;
                    }

                    System.out.println("O Que deseja modificar?");
                    System.out.println("1 - Mesa\n2 - Nome do Cliente\n3 - Telefone do Cliente\n4 - Data\n5 - Hora");

                    int modOpcao = inserirInt(sc);
                    sc.nextLine();

                    switch (modOpcao) {
                        case 1:
                            mesa.listar();
                            Long mesaTemp = 0L;

                            System.out.print("Insira o numero da nova mesa: ");
                            Long novaMesaNum = inserirLong(sc);

                            // Liberar a mesa anterior
                            Mesa mesaAtual = null;
                            for (Mesa mesinha : GerenciadorMesas.getListaMesas().values()) {
                                if (Objects.equals(mesinha.getId(), novaReserva.getIdMesa())) {
                                    mesaTemp = mesinha.getId();
                                    mesaAtual = mesinha;
                                    break;
                                }
                            }

                            if (mesaAtual != null) {
                                mesaAtual.setStatusMesa(false);
                            }

                            // Verificar se a nova mesa está disponível
                            Mesa mesaNova = null;
                            for (Mesa mesinha : GerenciadorMesas.getListaMesas().values()) {
                                if (Objects.equals(mesinha.getId(), novaMesaNum)) {
                                    if (mesinha.isStatusMesa()) {
                                        System.out.println("A nova mesa está ocupada. Tente novamente.");
                                        if (mesaAtual != null) {
                                            mesaAtual.setStatusMesa(true);
                                        }
                                    } else {
                                        mesaNova = mesinha;
                                    }
                                    break;
                                }
                            }

                            if (mesaNova != null) {
                                novaReserva.setIdMesa(mesaNova.getId());
                                mesaNova.setStatusMesa(true);
                                System.out.println("Mesa " + mesaTemp + " alterada com sucesso para mesa " + mesaNova.getId());
                            } else {
                                if (mesaAtual != null) {
                                    mesaAtual.setStatusMesa(true);
                                }
                                System.out.println("ID da nova mesa inválido ou a mesa está ocupada.");
                            }
                            break;
                        case 2:
                            // Modificar nome do cliente
                            String nomeTemp = novaReserva.getNomeCliente();

                            System.out.print("Insira o novo nome do cliente: ");
                            String novoNome = sc.nextLine();

                            novaReserva.setNomeCliente(novoNome);

                            System.out.println("Alterando nome do cliente " + nomeTemp + " para " + novaReserva.getNomeCliente());
                            break;
                        case 3:
                            // Modificar telefone do cliente
                            String telefoneTemp = novaReserva.getTelefoneCliente();

                            System.out.print("Insira o novo telefone do cliente: ");
                            String novoTelefone = sc.nextLine();

                            novaReserva.setTelefoneCliente(novoTelefone);

                            System.out.println("Alterando telefone do cliente " + telefoneTemp + " para " + novaReserva.getTelefoneCliente());
                            break;
                        case 4:
                            // Modificar data da reserva
                            String dataTemp = novaReserva.getDataReserva();

                            System.out.print("Insira a nova data: ");
                            String novaData = sc.nextLine();

                            novaReserva.setDataReserva(novaData);

                            System.out.println("Alterando a data " + dataTemp + " para " + novaReserva.getDataReserva());
                            break;
                        case 5:
                            // Modificar hora da reserva
                            String horaTemp = novaReserva.getHoraReserva();

                            System.out.print("Insira a nova hora: ");
                            String novaHora = sc.nextLine();

                            novaReserva.setHoraReserva(novaHora);

                            System.out.println("Alterando a hora " + horaTemp + " para " + novaReserva.getHoraReserva());
                            break;
                        default:
                            System.out.println("Opção inserida inválida. Tente novamente."); // Opção inválida
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente."); // Opção inválida
            }
        } while (opcaoReserva != 0); // Continua no loop enquanto a opção não for 0
    }

    // SubMenu de Reservas (Fazer Reservas)

    public boolean subMenuReservaFazerReserva(Scanner sc) {

        System.out.println("========== Clientes e Mesas ==========\n");
        pessoa.listarCliente(); // Lista os clientes

        System.out.print("\n");
        mesa.listar(); // Lista as mesas

        System.out.println("\n========== Fazer Reserva ==========");
        System.out.println("Digite o id do cliente ou digite (0) para voltar: ");
        Long idCliente = inserirLong(sc);

        if (idCliente == 0) {
            voltandoAnimacao(); // Animação de volta
            return false;
        }

        // Buscar o cliente diretamente no Map
        Cliente clienteSelec = GerenciadorDePessoas.getListaClientes().get(idCliente);

        if (clienteSelec == null) {
            System.out.println("ID DE CLIENTE INVALIDO.");
            return false;
        }

        System.out.println("Digite o numero da mesa: ");
        Long idMesa = inserirLong(sc);
        sc.nextLine(); // Limpa o buffer do scanner

        // Verifica se a mesa está disponível
        Mesa mesaSelec = null;
        for (Mesa mesinha : GerenciadorMesas.getListaMesas().values()) {
            if (Objects.equals(idMesa, mesinha.getId()) && !mesinha.isStatusMesa()) {
                mesaSelec = mesinha;
            }
        }

        if (mesaSelec == null) {
            System.out.println("ID DA MESA INVALIDA OU OCUPADA");
            return false;
        }

        // Solicita a data e hora da reserva
        System.out.println("Insira a Data (DD/MM/AAAA): ");
        String dataReserva = sc.nextLine();
        System.out.println("Insira o Horario (HH:MM): ");
        String horaReserva = sc.nextLine();

        Garcom g = reserva.escolherGarcom(); // Escolhe o garçom para a reserva
        if (g == null) {
            return false;
        }
        // Adiciona a nova reserva
        reserva.adicionarReserva(new Reserva(dataReserva, horaReserva, clienteSelec.getNome(), clienteSelec.getTelefone(), mesaSelec.getId(), g, idCliente));
        mesaSelec.reservar(); // Marca a mesa como reservada

        return true; // Retorna true indicando sucesso na reserva
    }


    // Menu de Mesas

    public void menuMesas(Scanner sc) {
        int opcaoMesa; // Variável para armazenar a opção do menu de mesas

        do {
            System.out.println("========== MESAS ==========");
            System.out.println("1 - Listar Mesas");
            System.out.println("2 - Adicionar Mesas");
            System.out.println("3 - Remover Mesas");
            System.out.println("4 - Modificar Mesa");
            System.out.println("0 - VOLTAR");
            System.out.println("===========================");

            opcaoMesa = inserirInt(sc);

            switch (opcaoMesa) {
                case 1:
                    // Listar Mesas se existir
                    if (GerenciadorMesas.getListaMesas().isEmpty()) {
                        System.out.println("Nenhuma Mesa Encontrada");
                    } else {
                        mesa.listar();
                    }
                    break;
                case 2:
                    // Adicionar Mesas
                    int capMesa; // Variável para a capacidade da mesa

                    System.out.println("========== Adicionar Mesa ==========");
                    System.out.println("Insira a capacidade da mesa (MAX 10 - MIN 2)");

                    // Valida a capacidade da mesa
                    do {
                        capMesa = inserirInt(sc);

                        if (capMesa < 2 || capMesa > 10) {
                            System.out.println("Numero invalido, insira novamente");
                        }
                    } while (capMesa < 2 || capMesa > 10);

                    mesa.adicionarMesa(new Mesa(capMesa)); // Adiciona a nova mesa
                    System.out.println("Mesa adicionada com sucesso");
                    break;
                case 3:
                    // Remover Mesas
                    mesa.listar();

                    System.out.println("========== Remover Mesa ==========");
                    System.out.println("Insira o ID da mesa: ");
                    Long canidMesa = inserirLong(sc);

                    mesa.removerMesa(canidMesa); // Remove a mesa
                    break;
                case 4:
                    // Modificar Mesa

                    if (GerenciadorMesas.getListaMesas().isEmpty()) {
                        System.out.println("Nenhuma Mesa Registrada");
                        break;
                    }

                    System.out.println("========== Modificar Mesa ==========");
                    mesa.listar();

                    System.out.println("\nInsira o ID da mesa: ");
                    Long modIdMesa = inserirLong(sc);

                    Mesa novaMesa = GerenciadorMesas.getListaMesas().get(modIdMesa);

                    if (novaMesa == null) {
                        System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
                        break;
                    }

                    int capTemp = novaMesa.getCapacidade();
                    Long idTemp = novaMesa.getId();

                    System.out.println("Insira a nova capacidade da Mesa (MAX 10 - MIN 2)");
                    int novaCapacidade;

                    do {
                        novaCapacidade = inserirInt(sc);

                        if (novaCapacidade < 2 || novaCapacidade > 10) {
                            System.out.println("Numero invalido, insira novamente");
                        }
                    } while (novaCapacidade < 2 || novaCapacidade > 10);

                    novaMesa.setCapacidade(novaCapacidade); // Define a nova capacidade da mesa
                    System.out.println("Alterando a capacidade da mesa " + idTemp + " de " + capTemp + " para " + novaMesa.getCapacidade());
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }
        } while (opcaoMesa != 0); // Continua no loop enquanto a opção não for 0
    }

    // Menu de Cadastros

    public void menuCadastros(Scanner sc) {
        int opcaoCadastros; // Variável para armazenar a opção do menu de cadastros

        do {
            System.out.println("========== Cadastros ==========");
            System.out.println("1 - Cliente");
            System.out.println("2 - Garcom");
            System.out.println("3 - Gerente");
            System.out.println("0 - Voltar");
            System.out.println("===============================");

            opcaoCadastros = inserirInt(sc);

            switch (opcaoCadastros) {
                case 1:
                    menuClientes(sc); // Chama o menu de clientes
                    break;
                case 2:
                    menuGarcom(sc); // Chama o menu de garçom
                    break;
                case 3:
                    menuGerente(sc); // Chama o menu de gerente
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }

        } while (opcaoCadastros != 0); // Continua no loop enquanto a opção não for 0
    }

    // Menu de Clientes

    public void menuClientes(Scanner sc) {
        int opcaoCliente; // Variável para armazenar a opção do menu de clientes

        do {
            System.out.println("========== CLIENTES ==========");
            System.out.println("1 - Listar Clientes");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Remover Cliente");
            System.out.println("4 - Modificar Cliente");
            System.out.println("0 - VOLTAR");
            System.out.println("==============================");

            opcaoCliente = inserirInt(sc);
            sc.nextLine(); // Limpa o buffer do scanner

            switch (opcaoCliente) {
                case 1:
                    // Listar Clientes se existir
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) {
                        System.out.println("Nenhum cliente registrado.");
                    } else {
                        pessoa.listarCliente();
                    }
                    break;
                case 2:
                    // Cadastrar Cliente
                    subMenuClienteCadastro(sc); // Chama o submenu de cadastro de cliente
                    break;
                case 3:
                    // Remover Cliente
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) {
                        System.out.println("Nenhum Cliente Registrado");
                        break;
                    }

                    System.out.println("========== Remover Cliente ==========");
                    pessoa.listarCliente();

                    System.out.println("\nInsira o ID do Cliente");
                    Long canIdCliente = inserirLong(sc); // Lê o ID do cliente

                    // Buscar o cliente diretamente no Map
                    Cliente canClienteSelec = GerenciadorDePessoas.getListaClientes().get(canIdCliente);

                    if (canClienteSelec == null) {
                        System.out.println("ID DE CLIENTE INVALIDO.");
                        break;
                    }

                    pessoa.removerCliente(canClienteSelec.getId()); // Remove o cliente
                    break;
                case 4:
                    // Modificar Cliente
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) {
                        System.out.println("Nenhum Cliente Registrado");
                        break;
                    }

                    System.out.println("========== Modificar Cliente ==========");
                    pessoa.listarCliente();

                    System.out.println("\nInsira o ID do Cliente");
                    Long modIdCliente = inserirLong(sc); // Lê o ID do cliente

                    // Buscar o cliente diretamente no Map
                    Cliente modClienteSelec = GerenciadorDePessoas.getListaClientes().get(modIdCliente);

                    if (modClienteSelec == null) {
                        System.out.println("ID DE CLIENTE INVALIDO.");
                        break;
                    }

                    // Solicita o que deseja modificar
                    System.out.println("O Que deseja modificar?");
                    System.out.println("1 - Nome\n2 - Endereco\n3 - Telefone\n4 - Data de Nascimento");

                    int modOpcao = inserirInt(sc); // Lê a opção escolhida
                    sc.nextLine(); // Limpa o buffer do scanner

                    switch (modOpcao) {
                        case 1:
                            System.out.print("Insira o novo nome: ");
                            String novoNome = sc.nextLine();
                            modClienteSelec.setNome(novoNome); // Modifica o nome do cliente
                            System.out.println("Nome modificado para: " + modClienteSelec.getNome());
                            break;
                        case 2:
                            System.out.print("Insira o novo endereco: ");
                            String novoEndereco = sc.nextLine();
                            modClienteSelec.setEndereco(novoEndereco); // Modifica o endereço do cliente
                            System.out.println("Endereço modificado para: " + modClienteSelec.getEndereco());
                            break;
                        case 3:
                            System.out.print("Insira o novo telefone: ");
                            String novoTelefone = sc.nextLine();
                            modClienteSelec.setTelefone(novoTelefone); // Modifica o telefone do cliente
                            System.out.println("Telefone modificado para: " + modClienteSelec.getTelefone());
                            break;
                        case 4:
                            System.out.print("Insira a nova data de nascimento: ");
                            String novaDataNasc = sc.nextLine();
                            modClienteSelec.setDataNasc(novaDataNasc); // Modifica a data de nascimento do cliente
                            System.out.println("Data de Nascimento modificado para: " + modClienteSelec.getDataNasc());
                            break;
                        default:
                            System.out.println("Opção inserida inválida."); // Opção inválida
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }
        } while (opcaoCliente != 0); // Continua no loop enquanto a opção não for 0
    }

    // SubMenu do Cliente (Cadastro)

    public Cliente subMenuClienteCadastro(Scanner sc) {
        Cliente cliente; // Cria um objeto Cliente

        System.out.println("========== Cadastrar Cliente ==========");
        System.out.println("Insira o nome do cliente: ");
        String cadNomeCliente = sc.nextLine();

        System.out.println("Insira o Endereco: ");
        String enderecoCliente = sc.nextLine();

        System.out.println("Insira o Telefone");
        String cadTelefoneCliente = sc.nextLine();

        System.out.println("Insira a Data de Nascimento");
        String nascCliente = sc.nextLine();

        // Cria o novo cliente com as informações fornecidas
        cliente = new Cliente(cadNomeCliente, enderecoCliente, cadTelefoneCliente, nascCliente);

        pessoa.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso");

        return cliente; // Retorna o objeto Cliente
    }


    // Menu do Garçom

    public void menuGarcom(Scanner sc) {
        int opcaoGarcom;

        do {
            System.out.println("========== GARCOM ==========");
            System.out.println("1 - Listar Garçons");
            System.out.println("2 - Cadastrar Garçom");
            System.out.println("3 - Remover Garçom");
            System.out.println("4 - Modificar Garçom");
            System.out.println("0 - VOLTAR");
            System.out.println("============================");

            opcaoGarcom = inserirInt(sc);
            sc.nextLine(); // Limpa o buffer do scanner

            switch (opcaoGarcom) {
                case 1:
                    // Listar Garçons
                    System.out.println("========== Listar Garçons ==========");
                    pessoa.listarGarcom(); // Lista os garçons
                    break;
                case 2:
                    // Cadastrar Garçom
                    System.out.println("========== Cadastrar Garçom ==========");
                    System.out.println("Insira o nome do Garçom: ");
                    String cadNomeGarcom = sc.nextLine();

                    System.out.println("Insira o Endereço: ");
                    String enderecoGarcom = sc.nextLine();

                    System.out.println("Insira o Telefone: ");
                    String cadTelefoneGarcom = sc.nextLine();

                    System.out.println("Insira o Salário: ");
                    float salario = inserirFloat(sc);

                    // Adiciona o novo garçom ao gerenciador de pessoas
                    pessoa.adicionarGarcom(new Garcom(cadNomeGarcom, enderecoGarcom, cadTelefoneGarcom, salario));
                    System.out.println("Garçom cadastrado com sucesso");
                    break;
                case 3:
                    // Remover Garçom
                    if (GerenciadorDePessoas.getListaGarcom().isEmpty()) {
                        System.out.println("Nenhum Garçom Registrado");
                        break;
                    }

                    System.out.println("========== Remover Garçom ==========");
                    pessoa.listarGarcom(); // Lista os garçons

                    System.out.println("\nInsira o ID do Garçom: ");
                    Long canIdGarcom = inserirLong(sc); // Lê o ID do garçom a ser removido

                    Garcom canGarcomSelec = GerenciadorDePessoas.getListaGarcom().get(canIdGarcom); // Busca o garçom pelo ID

                    if (canGarcomSelec == null) {
                        System.out.println("ID DE GARÇOM INVÁLIDO.");
                        break;
                    }

                    pessoa.removerGarcom(canIdGarcom); // Remove o garçom
                    break;
                case 4:
                    // Modificar Garçom
                    if (GerenciadorDePessoas.getListaGarcom().isEmpty()) {
                        System.out.println("Nenhum Garçom Registrado");
                        break;
                    }

                    System.out.println("========== Modificar Garçom ==========");
                    pessoa.listarGarcom(); // Lista os garçons

                    System.out.println("\nInsira o ID do Garçom: ");
                    Long modIdGarcom = inserirLong(sc); // Lê o ID do garçom a ser modificado

                    Garcom modGarcomSelec = GerenciadorDePessoas.getListaGarcom().get(modIdGarcom); // Busca o garçom pelo ID

                    if (modGarcomSelec == null) {
                        System.out.println("ID DE GARÇOM INVÁLIDO.");
                        break;
                    }

                    // Solicita o que deseja modificar
                    System.out.println("O Que deseja modificar?");
                    System.out.println("1 - Nome\n2 - Endereço\n3 - Telefone\n4 - Salário");

                    int modOpcao = inserirInt(sc); // Lê a opção de modificação
                    sc.nextLine(); // Limpa o buffer do scanner

                    switch (modOpcao) {
                        case 1:
                            System.out.print("Insira o novo nome: ");
                            String novoNome = sc.nextLine();
                            modGarcomSelec.setNome(novoNome); // Modifica o nome do garçom
                            System.out.println("Nome Modificado para: " + modGarcomSelec.getNome());
                            break;
                        case 2:
                            System.out.print("Insira o novo endereço: ");
                            String novoEndereco = sc.nextLine();
                            modGarcomSelec.setEndereco(novoEndereco); // Modifica o endereço do garçom
                            System.out.println("Endereço Modificado para: " + modGarcomSelec.getEndereco());
                            break;
                        case 3:
                            System.out.print("Insira o novo telefone: ");
                            String novoTelefone = sc.nextLine();
                            modGarcomSelec.setTelefone(novoTelefone); // Modifica o telefone do garçom
                            System.out.println("Telefone Modificado para: " + modGarcomSelec.getTelefone());
                            break;
                        case 4:
                            System.out.print("Insira o novo salário: ");
                            float novoSalario = inserirFloat(sc);
                            modGarcomSelec.setSalario(novoSalario); // Modifica o salário do garçom
                            System.out.println("Salário Modificado para: " + modGarcomSelec.getSalario());
                            break;
                        default:
                            System.out.println("Opção inserida inválida.");
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }

        } while (opcaoGarcom != 0); // Continua no loop enquanto a opção não for 0
    }

    // Menu do Gerente
    public void menuGerente(Scanner sc) {
        int opcaoGerente; // Variável para armazenar a opção do menu de gerente

        do {
            System.out.println("========== GERENTE ==========");
            System.out.println("1 - Listar Gerentes");
            System.out.println("2 - Cadastrar Gerente");
            System.out.println("3 - Remover Gerente");
            System.out.println("4 - Modificar Gerente");
            System.out.println("0 - VOLTAR");
            System.out.println("============================");

            opcaoGerente = inserirInt(sc);
            sc.nextLine(); // Limpa o buffer do scanner

            switch (opcaoGerente) {
                case 1:
                    // Listar Gerentes
                    System.out.println("========== Listar Gerentes ==========");
                    if (GerenciadorDePessoas.getListaGerente().isEmpty()) {
                        System.out.println("Não existe gerente cadastrado no momento.");
                    } else {
                        pessoa.listarGerente(); // Lista os gerentes
                    }
                    break;
                case 2:
                    // Cadastrar Gerente
                    Gerente gerente;
                    System.out.println("========== Cadastrar Gerente ==========");
                    System.out.println("Insira o nome do Gerente: ");
                    String cadNomeGerente = sc.nextLine();

                    System.out.println("Insira o Endereço: ");
                    String enderecoGerente = sc.nextLine();

                    System.out.println("Insira o Telefone: ");
                    String cadTelefoneGerente = sc.nextLine();

                    System.out.println("Insira o Salário: ");
                    float salarioGerente = inserirFloat(sc);

                    // Cria um novo gerente com as informações fornecidas
                    gerente = new Gerente(cadNomeGerente, enderecoGerente, cadTelefoneGerente, salarioGerente);

                    pessoa.adicionarGerente(gerente); // Adiciona o gerente ao gerenciador de pessoas
                    break;
                case 3:
                    // Remover Gerente
                    System.out.println("========== Remover Gerente ==========");
                    if (GerenciadorDePessoas.getListaGerente().isEmpty()) {
                        System.out.println("Não existe gerente cadastrado no momento.");
                    } else {
                        pessoa.listarGerente(); // Lista os gerentes
                    }

                    System.out.println("\nInsira o ID do Gerente: ");
                    Long canIdGerente = inserirLong(sc); // Lê o ID do gerente a ser removido

                    pessoa.removerGerente(canIdGerente); // Remove o gerente
                    break;
                case 4:
                    // Modificar Gerente
                    if (GerenciadorDePessoas.getListaGerente().isEmpty()) {
                        System.out.println("Nenhum Gerente Registrado");
                        break;
                    }

                    System.out.println("========== Modificar Gerente ==========");
                    pessoa.listarGerente(); // Lista os gerentes

                    System.out.println("\nInsira o ID do Gerente: ");
                    Long modIdGerente = inserirLong(sc); // Lê o ID do gerente a ser modificado

                    Gerente modGerente = GerenciadorDePessoas.getListaGerente().get(modIdGerente); // Busca o gerente pelo ID

                    if (modGerente == null) {
                        System.out.println("ID INVÁLIDO OU NÃO ENCONTRADO.");
                        break;
                    }

                    // Solicita o que deseja modificar
                    System.out.println("O Que deseja modificar?");
                    System.out.println("1 - Nome\n2 - Endereço\n3 - Telefone\n4 - Salário");

                    int modOpcao = inserirInt(sc);
                    sc.nextLine(); // Limpa o buffer do scanner

                    switch (modOpcao) {
                        case 1:
                            // Modificar nome
                            System.out.print("Insira o novo nome: ");
                            String novoNome = sc.nextLine();
                            modGerente.setNome(novoNome); // Define o novo nome do gerente
                            System.out.println("Nome Modificado para: " + modGerente.getNome());
                            break;
                        case 2:
                            // Modificar endereço
                            System.out.print("Insira o novo endereço: ");
                            String novoEndereco = sc.nextLine();
                            modGerente.setEndereco(novoEndereco); // Define o novo endereço do gerente
                            System.out.println("Endereço Modificado para: " + modGerente.getEndereco());
                            break;
                        case 3:
                            // Modificar telefone
                            System.out.print("Insira o novo telefone: ");
                            String novoTelefone = sc.nextLine();
                            modGerente.setTelefone(novoTelefone); // Define o novo telefone do gerente
                            System.out.println("Telefone Modificado para: " + modGerente.getTelefone());
                            break;
                        case 4:
                            // Modificar salário
                            System.out.print("Insira o novo salário: ");
                            float novoSalario = inserirFloat(sc);
                            modGerente.setSalario(novoSalario); // Define o novo salário do gerente
                            System.out.println("Salário Modificado para: " + modGerente.getSalario());
                            break;
                        default:
                            System.out.println("Opção inserida inválida. Tente novamente.");
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Animação de volta
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
            }
        } while (opcaoGerente != 0); // Continua no loop enquanto a opção não for 0
    }


    // Menu do Cardápio

    public void menuCardapio(Scanner sc) {
        int opcaoCardapio; // Variável para armazenar a opção do usuário

        do {
            System.out.println("========== CARDÁPIO ==========");
            System.out.println("1 - Mostrar Cardápio");
            System.out.println("2 - Adicionar Item");
            System.out.println("3 - Remover Item");
            System.out.println("0 - VOLTAR");
            System.out.println("==============================");

            opcaoCardapio = inserirInt(sc); // Lê a opção do usuário

            switch (opcaoCardapio) {
                case 1:
                    if (cardapio.getCardapio().isEmpty()) {
                        System.out.println("Cardapio vazio.");
                    } else {
                        cardapio.listar(); // Lista os itens do cardápio
                    }
                    break;
                case 2:
                    // Adicionar novo item ao cardápio
                    System.out.println("\n========== Adicionar Item ==========");
                    System.out.println("Escolha a categoria do item.");
                    imprimirCategorias();
                    System.out.println("0 - VOLTAR");
                    System.out.println("====================================");

                    int opAdicionarItem = inserirInt(sc); // Lê a opção de categoria
                    sc.nextLine();

                    if (opAdicionarItem == 0) {
                        voltandoAnimacao(); // Retorna ao menu anterior
                        break;
                    }

                    // Solicita informações para adicionar o item
                    System.out.println("Insira um nome para o item.");
                    String nome = sc.nextLine();
                    System.out.println("Insira um tamanho para o item");
                    String tamanho = sc.nextLine();
                    System.out.println("Insira uma descricao para o item");
                    String descricao = sc.nextLine();
                    System.out.println("Insira um preco para o item");
                    double preco = inserirDouble(sc);

                    // Adiciona o item à categoria escolhida
                    switch (opAdicionarItem) {
                        case 1:
                            cardapio.adicionarItem("Pratos Principais", new ItemCardapio(nome, tamanho, descricao, preco));
                            System.out.println("Item adicionado ao cardapio com sucesso.");
                            break;
                        case 2:
                            cardapio.adicionarItem("Acompanhamentos", new ItemCardapio(nome, tamanho, descricao, preco));
                            System.out.println("Item adicionado ao cardapio com sucesso.");
                            break;
                        case 3:
                            cardapio.adicionarItem("Bebidas", new ItemCardapio(nome, tamanho, descricao, preco));
                            System.out.println("Item adicionado ao cardapio com sucesso.");
                            break;
                        case 4:
                            cardapio.adicionarItem("Sobremesas", new ItemCardapio(nome, tamanho, descricao, preco));
                            System.out.println("Item adicionado ao cardapio com sucesso.");
                            break;
                        case 5:
                            cardapio.adicionarItem("Outros", new ItemCardapio(nome, tamanho, descricao, preco));
                            System.out.println("Item adicionado ao cardapio com sucesso.");
                            break;
                        default:
                            System.out.println("Opcao inserida invalida. Tente Novamente.");
                            break;
                    }
                    break;
                case 3:
                    // Remover item do cardápio
                    int canIdItem;
                    System.out.println("========== Remover Item ==========");
                    System.out.println("Escolha a categoria do item.");
                    imprimirCategorias();
                    System.out.println("0 - VOLTAR");
                    System.out.println("===================================");

                    int opRemoverItem = inserirInt(sc); // Lê a opção de categoria

                    if (opRemoverItem == 0) {
                        voltandoAnimacao(); // Retorna ao menu anterior
                        break;
                    }

                    // Exibe os itens da categoria escolhida e solicita o ID do item a ser removido
                    switch (opRemoverItem) {
                        case 1:
                            cardapio.imprimirItensCategoria("Pratos Principais", cardapio.getCardapio());
                            System.out.println("\nInsira o ID do Item");
                            canIdItem = inserirInt(sc);
                            cardapio.removerItem("Pratos Principais", canIdItem); // Remove o item do cardápio
                            break;
                        case 2:
                            cardapio.imprimirItensCategoria("Acompanhamentos", cardapio.getCardapio());
                            System.out.println("\nInsira o ID do Item");
                            canIdItem = inserirInt(sc);
                            cardapio.removerItem("Acompanhamentos", canIdItem); // Remove o item do cardápio
                            break;
                        case 3:
                            cardapio.imprimirItensCategoria("Bebidas", cardapio.getCardapio());
                            System.out.println("\nInsira o ID do Item");
                            canIdItem = inserirInt(sc);
                            cardapio.removerItem("Bebidas", canIdItem); // Remove o item do cardápio
                            break;
                        case 4:
                            cardapio.imprimirItensCategoria("Sobremesas", cardapio.getCardapio());
                            System.out.println("\nInsira o ID do Item");
                            canIdItem = inserirInt(sc);
                            cardapio.removerItem("Sobremesas", canIdItem); // Remove o item do cardápio
                            break;
                        case 5:
                            cardapio.imprimirItensCategoria("Outros", cardapio.getCardapio());
                            System.out.println("\nInsira o ID do Item");
                            canIdItem = inserirInt(sc);
                            cardapio.removerItem("Outros", canIdItem); // Remove o item do cardápio
                            break;
                        default:
                            System.out.println("Opcao inserida invalida. Tente Novamente.");
                            break;
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Retorna ao menu anterior
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
                    break;
            }
        } while (opcaoCardapio != 0); // Continua o loop enquanto a opção for diferente de zero
    }

    // Menu da Cozinha

    public void menuCozinha(Scanner sc) {
        int opcaoCozinha; // Variável para armazenar a opção do usuário

        do {
            System.out.println("========== COZINHA ==========");
            System.out.println("1 - Visualizar Pedidos Pendentes");
            System.out.println("2 - Consultar histórico de pedidos");
            System.out.println("3 - Criar Pedido");
            System.out.println("4 - Preparar Pedido");
            System.out.println("5 - Finalizar Pedido");
            System.out.println("6 - Consultar andamento do pedido");
            System.out.println("0 - VOLTAR");
            System.out.println("=============================");

            opcaoCozinha = inserirInt(sc); // Lê a opção do usuário

            switch (opcaoCozinha) {
                case 1:
                    if (cozinha.getPedidosPendentes().isEmpty()) {
                        System.out.println("Nao existem pedidos pendentes.");
                        break;
                    } else {
                        cozinha.mostrarPedidosPendentes(); // Mostra os pedidos pendentes
                    }
                    break;
                case 2:
                    if (pedidos.getListaPedidos().isEmpty()) {
                        System.out.println("Nao existem pedidos.");
                        break;
                    } else {
                        pedidos.listar(); // Lista todos os pedidos
                    }
                    break;
                case 3:
                    // Criar novo pedido na cozinha
                    if (GerenciadorDePessoas.getListaClientes().isEmpty()) {
                        System.out.println("Nao exitem clientes registrados");
                        break;
                    } else {
                        pessoa.listarCliente(); // Lista todos os clientes registrados
                    }

                    System.out.println("Insira o id do cliente.");

                    Long idCliente = inserirLong(sc); // Lê o ID do cliente

                    Cliente cliente = GerenciadorDePessoas.getListaClientes().get(idCliente);
                    if (cliente != null) {
                        System.out.println("Id encontrado.");
                    } else {
                        System.out.println("Id não encontrado");
                        break;
                    }

                    boolean verificarReserva = false;
                    for (Reserva r : GerenciadorReservas.getListaReservas().values()) {
                        if (Objects.equals(r.getIdCliente(), idCliente)) {
                            verificarReserva = true;
                            System.out.println("Reserva encontrada");
                            break;
                        }
                    }

                    if (!verificarReserva) {
                        System.out.println("Reserva não encontrada");
                        break;
                    }

                    int opcaoCategoria;
                    int continuar = 0;

                    // Loop para adicionar itens ao pedido
                    do {
                        System.out.println("Qual a categoria do pedido?");
                        imprimirCategorias();
                        opcaoCategoria = inserirInt(sc); // Lê a opção de categoria
                        sc.nextLine();

                        ItemPedido novoPedido;

                        switch (opcaoCategoria) {
                            case 1:
                                novoPedido = FazerPedido(cliente, sc, "Pratos Principais");
                                break;
                            case 2:
                                novoPedido = FazerPedido(cliente, sc, "Acompanhamentos");
                                break;
                            case 3:
                                novoPedido = FazerPedido(cliente, sc, "Bebidas");
                                break;
                            case 4:
                                novoPedido = FazerPedido(cliente, sc, "Sobremesas");
                                break;
                            case 5:
                                novoPedido = FazerPedido(cliente, sc, "Outros");
                                break;
                            default:
                                System.out.println("Opcao inserida invalida. Tente Novamente.");
                                continue; // Retorna ao início do loop para nova entrada
                        }

                        if (novoPedido != null) {
                            System.out.println("Deseja continuar (1) ou cancelar o pedido (2)?");
                            int continuar1 = inserirInt(sc);
                            sc.nextLine();

                            if (continuar1 == 1) {
                                cozinha.marcarPedidoEmPendente(novoPedido); // Marca o pedido como pendente na cozinha
                            } else if (continuar1 == 2) {
                                pedidos.removerItem(novoPedido.getId()); // Remove o item do pedido
                            }

                        }

                        System.out.println("Deseja adicionar mais itens? (1)Sim / (2)Nao");
                        continuar = inserirInt(sc); // Lê a opção de continuar
                        sc.nextLine();
                    } while (continuar == 1); // Continua o loop enquanto a opção for 1

                    break;
                case 4:
                    // Preparar pedido na cozinha
                    if (cozinha.getPedidosPendentes().isEmpty()) {
                        System.out.println("Nao exitem pedidos pendentes");
                        break;
                    } else {
                        cozinha.mostrarPedidosPendentes(); // Mostra os pedidos pendentes
                    }
                    boolean certoPrep = false;

                    System.out.println("Insira o id do pedido.");

                    int idPedidoPrep = inserirInt(sc);
                    sc.nextLine();

                    ItemPedido pedidoPreparar = null;
                    for (ItemPedido p : cozinha.getPedidosPendentes()) {
                        if (p.getId() == idPedidoPrep) {
                            certoPrep = true;
                            System.out.println("Id encontrado.");
                            pedidoPreparar = p;
                            break;
                        }
                    }

                    if (!certoPrep) {
                        System.out.println("Id nao encontrado");
                        break;
                    }

                    cozinha.marcarPedidoComoPreparando(pedidoPreparar); // Marca o pedido como preparando na cozinha
                    break;
                case 5:
                    // Finalizar pedido na cozinha
                    if (cozinha.getPedidosEmPreparacao().isEmpty()) {
                        System.out.println("Nao existem pedidos para serem finalizados");
                        break;
                    } else {
                        cozinha.mostrarPedidosEmPreparacao(); // Mostra os pedidos em preparação
                    }
                    boolean certoPronto = false;

                    System.out.println("Insira o id do pedido.");

                    int idPedidoPronto = inserirInt(sc);
                    sc.nextLine();

                    ItemPedido pedidoPronto = null;
                    for (ItemPedido p : cozinha.getPedidosEmPreparacao()) {
                        if (p.getId() == idPedidoPronto) {
                            certoPronto = true;
                            System.out.println("Id encontrado.");
                            pedidoPronto = p;
                            break;
                        }
                    }

                    if (!certoPronto) {
                        break;
                    }

                    cozinha.marcarPedidoComoPronto(pedidoPronto); // Marca o pedido como pronto na cozinha
                    break;
                case 6:
                    // Consultar andamento dos pedidos na cozinha
                    if (cozinha.getPedidosPendentes().isEmpty()) {
                        System.out.println("Nao exitem pedidos pendentes");
                    } else {
                        cozinha.mostrarPedidosPendentes(); // Mostra os pedidos pendentes
                        System.out.println();
                    }

                    if (cozinha.getPedidosEmPreparacao().isEmpty()) {
                        System.out.println("Nao exitem pedidos em preparação");
                    } else {
                        cozinha.mostrarPedidosEmPreparacao(); // Mostra os pedidos em preparação
                        System.out.println();
                    }

                    if (cozinha.getPedidosProntos().isEmpty()) {
                        System.out.println("Nao exitem pedidos prontos");
                    } else {
                        cozinha.mostrarPedidosProntos(); // Mostra os pedidos prontos
                    }
                    break;
                case 0:
                    voltandoAnimacao(); // Retorna ao menu anterior
                    break;
                default:
                    System.out.println("Opção inserida inválida. Tente novamente.");
                    break;
            }
        } while (opcaoCozinha != 0); // Continua o loop enquanto a opção for diferente de zero
    }

}
