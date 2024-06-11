package com.artur.gerenciamento;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.artur.controle.*;
import com.artur.pessoas.Cliente;
import com.artur.pessoas.Garcom;
import com.artur.pessoas.Gerente;

//OBS: as funções inserirInt, inserirDouble e inserirFloat, são funções que pega a entrada do usuário verifica se é um int ou double e retorna ela, se não for ele pede
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
		this.mesa = new GerenciadorMesas();
		this.reserva = new GerenciadorReservas();
		this.pessoa = new GerenciadorDePessoas();
		this.caixa = new Pagamento();

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
			System.out.println("\nOla!! Bem vindo ao " + restaurante.getNOME_RESTAURANTE()
			+ ". Para iniciar o atendimento, precisamos fazer seu cadastro.");
			System.out.println("Se voce ja tem um cadastro, pressione (1).");
			System.out.println("Se voce deseja fazer um cadastro, pressione (2)");
			System.out.println("Se deseja voltar, pressione (0)");

			opcao1 = inserirInt(sc);
			sc.nextLine();

			if (opcao1 == 0) {
				System.out.println("Voltanto...");
				return;
			}

			switch (opcao1) {
			case 1:

				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nenhum Cliente Encontrado. Deseja cadastrar um Cliente? (1)Sim/(2)Nao");
					int opcaoCliRe = inserirInt(sc);
					sc.nextLine();

					if (opcaoCliRe == 1) {
						cliente = subMenuClienteCadastro(sc);
						opcao1 = 0;
						break;
					} else if (opcaoCliRe == 2) {
						break;
					} else {
						System.out.println("Opção invalida. Voltando para o menu anterior...");
						break;
					}
				} else {
					opcao1 = 0;
					break;
				}

			case 2:
				cliente = subMenuClienteCadastro(sc);
				opcao1 = 0;
				break;
			default:
				System.out.println("Opcao inserida invalida. Tente Novamente.");
			}
		} while (opcao1 != 0);

		System.out.println("Agora, Vamos fazer sua reserva!");
		if (subMenuReservaFazerReserva(sc)) {
			System.out.println("Reserva feita com sucesso!");
		} else {
			System.out.println("Erro na reserva.");
			return;
		}

		int opcao2;

		System.out.println("Agora, seu pedido.");

		do {
			System.out.println("1 - Mostrar o Cardapio");
			System.out.println("2 - Fazer o Pedido");
			System.out.println("3 - Ir Para o Pagamento");

			System.out.println("0 - Cancelar");

			opcao2 = inserirInt(sc);
			sc.nextLine();

			if (opcao2 == 0) {
				System.out.println("Cancelando...");
				return;
			}

			switch (opcao2) {
			case 1:
				cardapio.listar();
				break;
			case 2:
				menuFazerPedido(cliente, sc);
				break;
			case 3:
				// Fazer Logica de Pagamento
				if (cozinha.getPedidosProntos().isEmpty()) {
					System.out.println("Nenhum pedido foi realizado.");
					break;
				} else {
					subMenuPagamentos(sc, cliente);
					System.out.println("O atendimento foi finalizado. Obrigado por escolher "
							+ restaurante.getNOME_RESTAURANTE() + "!");
					caixa.getPedidosPagos().addAll(cozinha.getPedidosProntos());
					Cliente finalCliente = cliente;
					cozinha.getPedidosProntos().removeIf(pedido -> pedido.getIdCliente() == finalCliente.getId());

					opcao2 = 0;
				}

				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcao2 != 0);
	}

	// Sub Menu Pagamentos

	public void subMenuPagamentos(Scanner sc, Cliente cliente) {
		caixa.listarPedidosParaPagar(sc, pessoa, cozinha, cliente.getId());
		caixa.fazerPagamento(sc);
	}

	// Menu Fazer Pedido

	protected void menuFazerPedido(Cliente cliente, Scanner sc) {
		int opcaoCategoria;
		int continuar = 0;

		do {
			System.out.println("Qual a categoria do pedido?");
			imprimirCategorias();
			opcaoCategoria = inserirInt(sc);
			sc.nextLine();

			ItemPedido novoPedido = null;

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
				continue;
			}

			if (novoPedido != null) {
				System.out.println("Deseja continuar (1) ou cancelar o pedido (2)?");
				int continuar1 = inserirInt(sc);
				sc.nextLine();

				if (continuar1 == 1) {
					cozinha.preparar(novoPedido);
				} else if (continuar1 == 2) {
					pedidos.removerItem(novoPedido.getId());
				}

			}

			System.out.println("Deseja adicionar mais itens? (1)Sim / (2)Nao");
			continuar = inserirInt(sc);
			sc.nextLine();
		} while (continuar == 1);
	}

	// Fazer Pedido

	public ItemPedido FazerPedido(Cliente cliente, Scanner sc, String categoria) {
		ItemPedido novoPedido = null;
		int quantidade;
		int idItem;

		List<ItemCardapio> itens = cardapio.getCardapio().get(categoria);

		if (itens.isEmpty()) {
			System.out.println("Nao ha itens na categoria: " + categoria);
			return null;
		}

		if (cardapio.getCardapio().containsKey(categoria)) {
			if (cardapio.getCardapio().get(categoria).isEmpty()) {
				System.out.println("Desculpe, não há " + categoria + " disponível no momento.");
				return null;
			} else {
				cardapio.imprimirItensCategoria(categoria, cardapio.getCardapio());
			}
		}

		System.out.println("Digite o ID do item:");
		idItem = inserirInt(sc);

		boolean encontrado = false;
		for (ItemCardapio item : itens) {
			if (item.getId() == idItem) {
				encontrado = true;
				System.out.println("Digite a quantidade:");
				quantidade = inserirInt(sc);
				sc.nextLine();

				novoPedido = new ItemPedido(item.getId(), item.getNome(), quantidade, (float) item.getPreco(), cliente);
				pedidos.adicionarItem(novoPedido);
				break;
			}
		}

		if (!encontrado) {
			System.out.println("Item não encontrado na categoria selecionada.");
			return null;
		}

		return novoPedido;
	}

	// Menu de Administração

	public void menuAdministracao(Scanner sc) {
		int menuAdministracao;

		do {
			System.out.println("========== ADMINISTRACAO ==========");
			System.out.println("1 - Recepcao");
			System.out.println("2 - Cadastros");
			System.out.println("3 - Cardapio");
			System.out.println("4 - Cozinha");
			System.out.println("0 - Voltar");
			System.out.println("===================================");

			menuAdministracao = inserirInt(sc);

			if (menuAdministracao == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (menuAdministracao) {
			case 1:
				menuRecepcao(sc);
				break;
			case 2:
				menuCadastros(sc);
				break;
			case 3:
				menuCardapio(sc);
				break;
			case 4:
				menuCozinha(sc);
				break;
			default:
				System.out.println("Opcao inserida invalida. Tente Novamente.");
			}

		} while (menuAdministracao != 0);
	}

	// Menu da Recepção

	public void menuRecepcao(Scanner sc) {
		int opcaoRecepcao;

		do {
			System.out.println("========== RECEPÇÃO ==========");
			System.out.println("1 - Caixa");
			System.out.println("2 - Reservas");
			System.out.println("3 - Mesas");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoRecepcao = inserirInt(sc);

			if (opcaoRecepcao == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoRecepcao) {
			case 1:
				menuCaixa(sc);
				break;
			case 2:
				menuReservas(sc);
				break;
			case 3:
				menuMesas(sc);
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcaoRecepcao != 0);
	}

	// Menu do Caixa

	public void menuCaixa(Scanner sc) {
		int opcaoCaixa;

		do {
			System.out.println("========== CAIXA ==========");
			System.out.println("1 - Consultar listas dos pedidos");
			System.out.println("2 - Registrar pagamento");
			System.out.println("3 - Relatório de vendas");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoCaixa = inserirInt(sc);

			if (opcaoCaixa == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoCaixa) {
			case 1:
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
				pessoa.listarCliente();

				List<ItemPedido> listaPedidos = new ArrayList<>();

				System.out.println("Insira o ID do cliente: ");
				int idCliente = inserirInt(sc);
				sc.nextLine();

				if (!cozinha.getPedidosProntos().isEmpty()) {
					for (ItemPedido p : cozinha.getPedidosProntos()) {
						if (p.getIdCliente() == idCliente) {
							listaPedidos.add(p);
							break;
						}
					}
				} else {
					System.out.println("Nao existem pedidos com pagamentos pendentes.");
				}

				if (!listaPedidos.isEmpty()) {
					caixa.listarPedidosParaPagar(sc, pessoa, cozinha, idCliente);
					caixa.fazerPagamento(sc);
					caixa.getPedidosPagos().addAll(cozinha.getPedidosProntos());
					cozinha.getPedidosProntos().removeIf(p -> p.getIdCliente() == idCliente);
				}

				break;
			case 3:
				if (caixa.getPedidosPagos().isEmpty()) {
					System.out.println("Nao existem pedidos pagos.");
				} else {
					caixa.listar();
				}

				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcaoCaixa != 0);

	}

	// Menu de Reservas

	public void menuReservas(Scanner sc) {
		int opcaoReserva;
		int temp;

		do {
			System.out.println("========== RESERVAS ==========");
			System.out.println("1 - Listar Reservas");
			System.out.println("2 - Fazer Reserva");
			System.out.println("3 - Cancelar Reserva");
			System.out.println("4 - Modificar Reserva");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoReserva = inserirInt(sc);

			if (opcaoReserva == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoReserva) {
			case 1:
				// Listar Reservas

				if (reserva.getListaReservas().isEmpty()) {
					System.out.println("Nenhuma reserva registrada.");
				} else {
					reserva.listar();
				}
				break;
			case 2:
				// Fazer Reserva

				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nenhum Cliente Encontrado. Deseja cadastrar um Cliente? (1)Sim/(2)Nao");
					int opcaoCliRe = inserirInt(sc);

					if (opcaoCliRe == 1) {
						menuClientes(sc);
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
				if (reserva.getListaReservas().isEmpty()) {
					System.out.println("Nenhuma Reserva Registrada");
					break;
				}

				System.out.println("========== Cancelar Reserva ==========");
				reserva.listar();

				System.out.println("\nInsira o ID da reserva ou digite (0) para voltar: ");
				int canIdReserva = inserirInt(sc);

				if (canIdReserva == 0) {
					System.out.println("Voltando...");
					break;
				}

				reserva.cancelarReserva(canIdReserva, mesa.getListaMesas(), pessoa.getListaGarcom());
				break;
			case 4:
				temp = 0;

				if (reserva.getListaReservas().isEmpty()) {
					System.out.println("Nenhuma Reserva Registrada");
					break;
				}

				System.out.println("========== Modificar Reseva ==========");
				reserva.listar();

				System.out.println("\nInsira o ID da Reserva ou digite (0) para voltar: ");
				int modIdReserva = inserirInt(sc);

				if (modIdReserva == 0) {
					System.out.println("Voltando...");
					break;
				}

				Reserva novaReserva = null;
				for (Reserva r : reserva.getListaReservas()) {
					if (modIdReserva == r.getId()) {
						temp = temp + 1;
						novaReserva = r;
					}
				}

				if (temp == 0) {
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
					int mesaTemp = 0;

					System.out.print("Insira o numero da nova mesa: ");
					int novaMesaNum = inserirInt(sc);

					// Liberar a mesa anterior
					Mesa mesaAtual = null;
					for (Mesa mesinha : mesa.getListaMesas()) {
						if (mesinha.getId() == novaReserva.getNumMesa()) {
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
					for (Mesa mesinha : mesa.getListaMesas()) {
						if (mesinha.getId() == novaMesaNum) {
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

						novaReserva.setNumMesa(mesaNova.getId());
						mesaNova.setStatusMesa(true);
						System.out.println("Mesa " + mesaTemp + " alterada com sucesso para mesa " + mesaNova.getId());
					} else {
						mesaAtual.setStatusMesa(true);
						System.out.println("ID da nova mesa inválido ou a mesa está ocupada.");
					}

					break;
				case 2:
					String nomeTemp = novaReserva.getNomeCliente();

					System.out.print("Insira o novo nome do cliente: ");
					String novoNome = sc.nextLine();

					novaReserva.setNomeCliente(novoNome);

					System.out
					.println("Alterando nome do cliente " + nomeTemp + " para " + novaReserva.getNomeCliente());

					break;
				case 3:
					String telefoneTemp = novaReserva.getTelefoneCliente();

					System.out.print("Insira o novo telefone do cliente: ");
					String novoTelefone = sc.nextLine();

					novaReserva.setTelefoneCliente(novoTelefone);

					System.out.println("Alterando telefone do cliente " + telefoneTemp + " para "
							+ novaReserva.getTelefoneCliente());

					break;
				case 4:
					String dataTemp = novaReserva.getDataReserva();

					System.out.print("Insira a nova data: ");
					String novaData = sc.nextLine();

					novaReserva.setDataReserva(novaData);

					System.out.println("Alterando a data " + dataTemp + " para " + novaReserva.getDataReserva());

					break;
				case 5:
					String horaTemp = novaReserva.getHoraReserva();

					System.out.print("Insira a nova hora: ");
					String novaHora = sc.nextLine();

					novaReserva.setHoraReserva(novaHora);

					System.out.println("Alterando a hora " + horaTemp + " para " + novaReserva.getHoraReserva());

					break;
				default:
					System.out.println("Opção inserida inválida. Tente novamente.");
				}
			}
		} while (opcaoReserva != 0);
	}

	// SubMenu de Reservas (Fazer Reservas)

	public boolean subMenuReservaFazerReserva(Scanner sc) {

		System.out.println("========== Clientes e Mesas ==========\n");
		pessoa.listarCliente();

		System.out.print("\n");
		mesa.listar();

		System.out.println("\n========== Fazer Reserva ==========");
		System.out.println("Digite o id do cliente ou digite (0) para voltar: ");
		int idCliente = inserirInt(sc);

		if (idCliente == 0) {
			System.out.println("Voltando...");
			return false;
		}

		Cliente clienteSelec = null;
		for (Cliente id : pessoa.getListaClientes()) {
			if (id.getId() == idCliente) {
				clienteSelec = id;
			}
		}

		if (clienteSelec == null) {
			System.out.println("ID DE CLIENTE INVALIDO.");
			return false;
		}

		System.out.println("Digite o numero da mesa: ");
		int numMesa = inserirInt(sc);
		sc.nextLine();

		Mesa mesaSelec = null;
		for (Mesa mesinha : mesa.getListaMesas()) {
			if (numMesa == mesinha.getId() && !mesinha.isStatusMesa()) {
				mesaSelec = mesinha;
			}
		}

		if (mesaSelec == null) {
			System.out.println("ID DA MESA INVALIDA OU OCUPADA");
			return false;
		}

		System.out.println("Insira a Data (DD/MM/AAAA): ");
		String dataReserva = sc.nextLine();
		System.out.println("Insira o Horario (HH:MM): ");
		String horaReserva = sc.nextLine();

		Garcom g = reserva.escolherGarcom(pessoa);
		if (g == null) {
			return false;
		}
		reserva.adicionarReserva(new Reserva(dataReserva, horaReserva, clienteSelec.getNome(),
				clienteSelec.getTelefone(), mesaSelec.getId(), g));
		mesaSelec.reservar();

		return true;
	}

	// Menu de Mesas

	public void menuMesas(Scanner sc) {
		int opcaoMesa;
		int temp;

		do {
			System.out.println("========== MESAS ==========");
			System.out.println("1 - Listar Mesas");
			System.out.println("2 - Adicionar Mesas");
			System.out.println("3 - Remover Mesas");
			System.out.println("4 - Modificar Mesa");
			System.out.println("0 - VOLTAR");
			System.out.println("===========================");

			opcaoMesa = inserirInt(sc);

			if (opcaoMesa == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoMesa) {
			case 1:
				if (mesa.getListaMesas().isEmpty()) {
					System.out.println("Nenhuma Mesa Encontrada");
					break;
				} else {
					mesa.listar();
				}
				break;
			case 2:
				int capMesa;
				System.out.println("========== Adicionar Mesa ==========");
				System.out.println("Insira a capacidade da mesa (MAX 10 - MIN 2)");
				do {
					capMesa = inserirInt(sc);

					if (capMesa < 2 || capMesa > 10) {
						System.out.println("Numero invalido, insira novamente");
					}
				} while (capMesa < 2 || capMesa > 10);

				mesa.adicionarMesa(new Mesa(capMesa));
				System.out.println("Mesa adicionada com sucesso");
				break;
			case 3:
				mesa.listar();
				System.out.println("========== Remover Mesa ==========");
				System.out.println("Insira o ID da mesa ou digite (0) para voltar: ");
				int canidMesa = inserirInt(sc);

				if (canidMesa == 0) {
					System.out.println("Voltando...");
					break;
				}

				mesa.removerMesa(canidMesa, mesa.getListaMesas());
				break;
			case 4:
				temp = 0;

				if (mesa.getListaMesas().isEmpty()) {
					System.out.println("Nenhuma Mesa Registrada");
					break;
				}

				System.out.println("========== Modificar Mesa ==========");
				mesa.listar();

				System.out.println("\nInsira o ID da mesa ou digite (0) para voltar: ");
				int modIdMesa = inserirInt(sc);

				if (modIdMesa == 0) {
					System.out.println("Voltando...");
					break;
				}

				Mesa novaMesa = null;
				for (Mesa m : mesa.getListaMesas()) {
					if (modIdMesa == m.getId()) {
						temp = temp + 1;
						novaMesa = m;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				int capTemp = novaMesa.getCapacidade();
				int idTemp = novaMesa.getId();

				System.out.println("Insira a nova capacidade da Mesa (MAX 10 - MIN 2)");
				int novaCapacidade;

				do {
					novaCapacidade = inserirInt(sc);

					if (novaCapacidade < 2 || novaCapacidade > 10) {
						System.out.println("Numero invalido, insira novamente");
					}
				} while (novaCapacidade < 2 || novaCapacidade > 10);

				novaMesa.setCapacidade(novaCapacidade);
				System.out.println("Alterando a capacidade da mesa " + idTemp + " de " + capTemp + " para "
						+ novaMesa.getCapacidade());
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcaoMesa != 0);
	}

	// Menu de Cadastros

	public void menuCadastros(Scanner sc) {
		int opcaoCadastros;

		do {
			System.out.println("========== Cadastros ==========");
			System.out.println("1 - Cliente");
			System.out.println("2 - Garcom");
			System.out.println("3 - Gerente");
			System.out.println("0 - Voltar");
			System.out.println("===============================");

			opcaoCadastros = inserirInt(sc);

			if (opcaoCadastros == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoCadastros) {
			case 1:
				menuClientes(sc);
				break;
			case 2:
				menuGarcom(sc);
				break;
			case 3:
				menuGerente(sc);
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}

		} while (opcaoCadastros != 0);
	}

	// Menu de Clientes

	public void menuClientes(Scanner sc) {
		int opcaoCliente;
		int temp;

		do {
			System.out.println("========== CLIENTES ==========");
			System.out.println("1 - Listar Clientes");
			System.out.println("2 - Cadastrar Cliente");
			System.out.println("3 - Remover Cliente");
			System.out.println("4 - Modificar Cliente");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoCliente = inserirInt(sc);
			sc.nextLine();

			if (opcaoCliente == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoCliente) {
			case 1:
				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nenhum cliente registrado.");
				} else {
					pessoa.listarCliente();
				}
				break;
			case 2:
				subMenuClienteCadastro(sc);
				break;
			case 3:
				temp = 0;

				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nenhum Cliente Registrado");
					break;
				}

				System.out.println("========== Remover Cliente ==========");
				pessoa.listarCliente();

				System.out.println("\nInsira o ID do Cliente ou digite (0) para voltar: ");
				int canIdCliente = inserirInt(sc);

				if (canIdCliente == 0) {
					System.out.println("Voltando...");
					break;
				}

				for (Cliente c : pessoa.getListaClientes()) {
					if (canIdCliente == c.getId()) {
						temp = temp + 1;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				pessoa.removerCliente(canIdCliente);
				break;
			case 4:
				temp = 0;

				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nenhum Cliente Registrado");
					break;
				}

				System.out.println("========== Modificar Cliente ==========");
				pessoa.listarCliente();

				System.out.println("\nInsira o ID do Cliente ou digite (0) para voltar: ");
				int modIdCliente = inserirInt(sc);

				if (modIdCliente == 0) {
					System.out.println("Voltando...");
					break;
				}

				for (Cliente c : pessoa.getListaClientes()) {
					if (modIdCliente == c.getId()) {
						temp = temp + 1;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				System.out.println("O Que deseja modificar?");
				System.out.println("1 - Nome\n2 - Endereco\n3 - Telefone\n4 - Data de Nascimento");

				int modOpcao = inserirInt(sc);
				sc.nextLine();

				switch (modOpcao) {
				case 1:
					System.out.print("Insira o novo nome: ");
					String novoNome = sc.nextLine();

					for (Cliente c : pessoa.getListaClientes()) {
						if (modIdCliente == c.getId()) {
							String nomeTemp = c.getNome();
							c.setNome(novoNome);
							System.out.println("Nome: " + nomeTemp + " Modificado para: " + c.getNome());
							break;
						}
					}

					break;
				case 2:
					System.out.print("Insira o novo endereco: ");
					String novoEndereco = sc.nextLine();

					for (Cliente c : pessoa.getListaClientes()) {
						if (modIdCliente == c.getId()) {
							String enderecoTemp = c.getEndereco();
							c.setEndereco(novoEndereco);
							System.out.println("Nome: " + enderecoTemp + " Modificado para: " + c.getEndereco());
							break;
						}
					}

					break;
				case 3:
					System.out.print("Insira o novo telefone: ");
					String novoTelefone = sc.nextLine();

					for (Cliente c : pessoa.getListaClientes()) {
						if (modIdCliente == c.getId()) {
							String telefoneTemp = c.getTelefone();
							c.setTelefone(novoTelefone);
							System.out.println("Nome: " + telefoneTemp + " Modificado para: " + c.getTelefone());
							break;
						}
					}

					break;
				case 4:
					System.out.print("Insira a nova data de nascimento: ");
					String novaDataNasc = sc.nextLine();

					for (Cliente c : pessoa.getListaClientes()) {
						if (modIdCliente == c.getId()) {
							String dataNascTemp = c.getDataNasc();
							c.setDataNasc(novaDataNasc);
							System.out.println("Nome: " + dataNascTemp + " Modificado para: " + c.getDataNasc());
							break;
						}
					}

					break;
				default:
					System.out.println("Opção inserida inválida.");
				}
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcaoCliente != 0);
	}

	// SubMenu do Cliente (Cadastro)

	public Cliente subMenuClienteCadastro(Scanner sc) {

		Cliente cliente = null;
		System.out.println("========== Cadastrar Cliente ==========");
		System.out.println("Insira o nome do cliente: ");
		String cadNomeCliente = sc.nextLine();
		System.out.println("Insira o Endereco: ");
		String enderecoCliente = sc.nextLine();
		System.out.println("Insira o Telefone");
		String cadTelefoneCliente = sc.nextLine();
		System.out.println("Insira a Data de Nascimento");
		String nascCliente = sc.nextLine();

		cliente = new Cliente(cadNomeCliente, enderecoCliente, cadTelefoneCliente, nascCliente);
		pessoa.adicionarCliente(cliente);
		System.out.println("Cliente cadastrado com sucesso");

		return cliente;
	}

	// Menu do Garçom

	public void menuGarcom(Scanner sc) {
		int opcaoGarcom;
		int temp;

		do {
			System.out.println("========== GARCOM ==========");
			System.out.println("1 - Listar Garcons");
			System.out.println("2 - Cadastrar Garcom");
			System.out.println("3 - Remover Garcom");
			System.out.println("4 - Modificar Garcom");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoGarcom = inserirInt(sc);
			sc.nextLine();

			if (opcaoGarcom == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoGarcom) {
			case 1:
				System.out.println("========== Listar Garcons ==========");
				pessoa.listarGarcom();
				break;
			case 2:
				System.out.println("========== Cadastrar Garcom ==========");
				System.out.println("Insira o nome do Garcom: ");
				String cadNomeGarcom = sc.nextLine();
				System.out.println("Insira o Endereco: ");
				String enderecoGarcom = sc.nextLine();
				System.out.println("Insira o Telefone");
				String cadTelefoneGarcom = sc.nextLine();
				System.out.println("Insira o Salario");
				float salario = inserirFloat(sc);

				pessoa.adicionarGarcom(new Garcom(cadNomeGarcom, enderecoGarcom, cadTelefoneGarcom, salario));
				System.out.println("Garcom cadastrado com sucesso");
				break;
			case 3:
				temp = 0;

				if (pessoa.getListaGarcom().isEmpty()) {
					System.out.println("Nenhum Garcom Registrado");
					break;
				}

				System.out.println("========== Remover Garcom ==========");
				pessoa.listarGarcom();

				System.out.println("\nInsira o ID do Garcom ou digite (0) para voltar: ");
				int canIdGarcom = inserirInt(sc);

				if (canIdGarcom == 0) {
					System.out.println("Voltando...");
					break;
				}

				for (Garcom g : pessoa.getListaGarcom()) {
					if (canIdGarcom == g.getId()) {
						temp = temp + 1;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				pessoa.removerGarcom(canIdGarcom);
				break;
			case 4:
				temp = 0;

				if (pessoa.getListaGarcom().isEmpty()) {
					System.out.println("Nenhum Garcom Registrado");
					break;
				}

				System.out.println("========== Modificar Garcom ==========");
				pessoa.listarGarcom();

				System.out.println("\nInsira o ID do Garcom ou digite (0) para voltar: ");
				int modIdGarcom = inserirInt(sc);

				if (modIdGarcom == 0) {
					System.out.println("Voltando...");
					break;
				}

				for (Garcom g : pessoa.getListaGarcom()) {
					if (modIdGarcom == g.getId()) {
						temp = temp + 1;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				System.out.println("O Que deseja modificar?");
				System.out.println("1 - Nome\n2 - Endereco\n3 - Telefone\n4 - Salario");

				int modOpcao = inserirInt(sc);
				sc.nextLine();

				switch (modOpcao) {
				case 1:
					System.out.print("Insira o novo nome: ");
					String novoNome = sc.nextLine();

					for (Garcom g : pessoa.getListaGarcom()) {
						if (modIdGarcom == g.getId()) {
							String nomeTemp = g.getNome();
							g.setNome(novoNome);
							System.out.println("Nome: " + nomeTemp + " Modificado para: " + g.getNome());
							break;
						}
					}

					break;
				case 2:
					System.out.print("Insira o novo endereco: ");
					String novoEndereco = sc.nextLine();

					for (Garcom g : pessoa.getListaGarcom()) {
						if (modIdGarcom == g.getId()) {
							String enderecoTemp = g.getEndereco();
							g.setEndereco(novoEndereco);
							System.out.println("Endereco: " + enderecoTemp + " Modificado para: " + g.getEndereco());
							break;
						}
					}

					break;
				case 3:
					System.out.print("Insira o novo telefone: ");
					String novoTelefone = sc.nextLine();

					for (Garcom g : pessoa.getListaGarcom()) {
						if (modIdGarcom == g.getId()) {
							String telefoneTemp = g.getTelefone();
							g.setTelefone(novoTelefone);
							System.out.println("Telefone: " + telefoneTemp + " Modificado para: " + g.getTelefone());
							break;
						}
					}

					break;
				case 4:
					System.out.print("Insira o novo salario: ");
					float novoSalario = inserirFloat(sc);

					for (Garcom g : pessoa.getListaGarcom()) {
						if (modIdGarcom == g.getId()) {
							float novoSalarioTemp = g.getSalario();
							g.setSalario(novoSalario);
							System.out.println("Salario: " + novoSalarioTemp + " Modificado para: " + g.getSalario());
							break;
						}
					}

					break;
				default:
					System.out.println("Opção inserida inválida.");
				}
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}

		} while (opcaoGarcom != 0);
	}

	// Menu do Gerente

	public void menuGerente(Scanner sc) {
		int opcaoGerente;

		do {
			System.out.println("========== GERENTE ==========");
			System.out.println("1 - Listar Gerente");
			System.out.println("2 - Cadastrar Gerente");
			System.out.println("3 - Remover Gerente");
			System.out.println("4 - Modificar Gerente");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoGerente = inserirInt(sc);
			sc.nextLine();

			if (opcaoGerente == 0) {
				System.out.println("Voltando...");
			}

			switch (opcaoGerente) {
			case 1:
				System.out.println("========== Listar Gerente ==========");
				if (pessoa.getListaGerente().isEmpty()) {
					System.out.println("Não existe gerente cadastrado no momento.");
				} else {
					pessoa.listarGerente();
				}

				break;
			case 2:
				Gerente gerente = null;
				System.out.println("========== Cadastrar Gerente ==========");
				System.out.println("Insira o nome do Gerente: ");
				String cadNomeGerente = sc.nextLine();
				System.out.println("Insira o Endereco: ");
				String enderecoGerente = sc.nextLine();
				System.out.println("Insira o Telefone");
				String cadTelefoneGerente = sc.nextLine();
				System.out.println("Insira o salario");
				float salarioGerente = inserirFloat(sc);

				gerente = new Gerente(cadNomeGerente, enderecoGerente, cadTelefoneGerente, salarioGerente);
				pessoa.adicionarGerente(gerente);
				System.out.println("Gerente cadastrado com sucesso");
				break;
			case 3:
				System.out.println("========== Remover Gerente ==========");
				if (pessoa.getListaGerente().isEmpty()) {
					System.out.println("Não existe gerente cadastrado no momento.");
				} else {
					pessoa.listarGerente();
				}

				System.out.println("\nInsira o ID do Gerente ou digite (0) para voltar: ");
				int canIdGerente = inserirInt(sc);

				if (canIdGerente == 0) {
					System.out.println("Voltando...");
					break;
				}

				pessoa.removerGerente(canIdGerente);

				break;
			case 4:
				int temp = 0;

				if (pessoa.getListaGerente().isEmpty()) {
					System.out.println("Nenhum Gerente Registrado");
					break;
				}

				System.out.println("========== Modificar Gerente ==========");
				pessoa.listarGerente();

				System.out.println("\nInsira o ID do Gerente ou digite (0) para voltar: ");
				int modIdGerente = inserirInt(sc);

				if (modIdGerente == 0) {
					System.out.println("Voltando...");
					break;
				}

				for (Gerente g : pessoa.getListaGerente()) {
					if (modIdGerente == g.getId()) {
						temp = temp + 1;
					}
				}

				if (temp == 0) {
					System.out.println("ID INVALIDO OU NAO ENCONTRADO.");
					break;
				}

				System.out.println("O Que deseja modificar?");
				System.out.println("1 - Nome\n2 - Endereco\n3 - Telefone\n4 - Salario");

				int modOpcao = inserirInt(sc);
				sc.nextLine();

				switch (modOpcao) {
				case 1:
					System.out.print("Insira o novo nome: ");
					String novoNome = sc.nextLine();

					for (Gerente g : pessoa.getListaGerente()) {
						if (modIdGerente == g.getId()) {
							String nomeTemp = g.getNome();
							g.setNome(novoNome);
							System.out.println("Nome: " + nomeTemp + " Modificado para: " + g.getNome());
							break;
						}
					}

					break;
				case 2:
					System.out.print("Insira o novo endereco: ");
					String novoEndereco = sc.nextLine();

					for (Gerente g : pessoa.getListaGerente()) {
						if (modIdGerente == g.getId()) {
							String enderecoTemp = g.getEndereco();
							g.setEndereco(novoEndereco);
							System.out.println("Endereço : " + enderecoTemp + " Modificado para: " + g.getEndereco());
							break;
						}
					}

					break;
				case 3:
					System.out.print("Insira o novo telefone: ");
					String novoTelefone = sc.nextLine();

					for (Gerente g : pessoa.getListaGerente()) {
						if (modIdGerente == g.getId()) {
							String telefoneTemp = g.getTelefone();
							g.setTelefone(novoTelefone);
							System.out.println("Telefone: " + telefoneTemp + " Modificado para: " + g.getTelefone());
							break;
						}
					}

					break;
				case 4:
					System.out.print("Insira o novo salario: ");
					float novoSalario = inserirFloat(sc);

					for (Gerente g : pessoa.getListaGerente()) {
						if (modIdGerente == g.getId()) {
							float novoSalarioTemp = g.getSalario();
							g.setSalario(novoSalario);
							System.out.println("Salario: " + novoSalarioTemp + " Modificado para: " + g.getSalario());
							break;
						}
					}
					break;
				default:
					System.out.println("Opção inserida inválida. Tente novamente.");
				}
			}
		} while (opcaoGerente != 0);
	}

	// Menu do Cardapio

	public void menuCardapio(Scanner sc) {
		int opcaoCardapio;

		do {
			System.out.println("========== CARDÁPIO ==========");
			System.out.println("1 - Mostrar Cardápio");
			System.out.println("2 - Adicionar Item");
			System.out.println("3 - Remover Item");
			System.out.println("4 - Modificar Cardapio");
			System.out.println("0 - VOLTAR");
			System.out.println("==============================");

			opcaoCardapio = inserirInt(sc);

			if (opcaoCardapio == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoCardapio) {
			case 1:
				if (cardapio.getCardapio().isEmpty()) {
					System.out.println("Cardapio vazio.");
				} else {
					cardapio.listar();
				}
				break;
			case 2:
				System.out.println("\n========== Adicionar Item ==========");
				System.out.println("Escolha a categoria do item.");
				imprimirCategorias();
				System.out.println("0 - VOLTAR");
				System.out.println("====================================");

				int opAdicionarItem = inserirInt(sc);
				sc.nextLine();

				if (opAdicionarItem == 0) {
					System.out.println("Voltando...");
					break;
				}

				System.out.println("Insira um nome para o item.");
				String nome = sc.nextLine();
				System.out.println("Insira um tamanho para o item");
				String tamanho = sc.nextLine();
				System.out.println("Insira uma descricao para o item");
				String descricao = sc.nextLine();
				System.out.println("Insira um preco para o item");
				double preco = inserirDouble(sc);

				if (opAdicionarItem == 1) {
					cardapio.adicionarItem("Pratos Principais", new ItemCardapio(nome, tamanho, descricao, preco));
					System.out.println("Item adicionado ao cardapio com sucesso.");
				} else if (opAdicionarItem == 2) {
					cardapio.adicionarItem("Acompanhamentos", new ItemCardapio(nome, tamanho, descricao, preco));
					System.out.println("Item adicionado ao cardapio com sucesso.");
				} else if (opAdicionarItem == 3) {
					cardapio.adicionarItem("Bebidas", new ItemCardapio(nome, tamanho, descricao, preco));
					System.out.println("Item adicionado ao cardapio com sucesso.");
				} else if (opAdicionarItem == 4) {
					cardapio.adicionarItem("Sobremesas", new ItemCardapio(nome, tamanho, descricao, preco));
					System.out.println("Item adicionado ao cardapio com sucesso.");
				} else if (opAdicionarItem == 5) {
					cardapio.adicionarItem("Outros", new ItemCardapio(nome, tamanho, descricao, preco));
					System.out.println("Item adicionado ao cardapio com sucesso.");
				} else {
					System.out.println("Opcao inserida invalida. Tente Novamente.");
				}
				break;
			case 3:
				System.out.println("========== Remover Item ==========");
				System.out.println("Escolha a categoria do item.");
				imprimirCategorias();
				System.out.println("0 - VOLTAR");
				System.out.println("===================================");

				int opRemoverItem = inserirInt(sc);

				if (opRemoverItem == 0) {
					System.out.println("Voltando...");
					break;
				}

				if (opRemoverItem == 1) {
					cardapio.imprimirItensCategoria("Pratos Principais", cardapio.getCardapio());
					System.out.println("\nInsira o ID do Item");
					int canIdItem = inserirInt(sc);
					cardapio.removerItem("Pratos Principais", canIdItem);
				} else if (opRemoverItem == 2) {
					cardapio.imprimirItensCategoria("Acompanhamentos", cardapio.getCardapio());
					System.out.println("\nInsira o ID do Item");
					int canIdItem = inserirInt(sc);
					cardapio.removerItem("Acompanhamentos", canIdItem);
				} else if (opRemoverItem == 3) {
					cardapio.imprimirItensCategoria("Bebidas", cardapio.getCardapio());
					System.out.println("\nInsira o ID do Item");
					int canIdItem = inserirInt(sc);
					cardapio.removerItem("Bebidas", canIdItem);
				} else if (opRemoverItem == 4) {
					cardapio.imprimirItensCategoria("Sobremesas", cardapio.getCardapio());
					System.out.println("\nInsira o ID do Item");
					int canIdItem = inserirInt(sc);
					cardapio.removerItem("Sobremesas", canIdItem);
				} else if (opRemoverItem == 5) {
					cardapio.imprimirItensCategoria("Outros", cardapio.getCardapio());
					System.out.println("\nInsira o ID do Item");
					int canIdItem = inserirInt(sc);
					cardapio.removerItem("Outros", canIdItem);
				} else {
					System.out.println("Opcao inserida invalida. Tente Novamente.");
				}
				break;
			default:
				System.out.println("Opção inserida inválida. Tente novamente.");
			}
		} while (opcaoCardapio != 0);
	}

	// Menu da Cozinha

	public void menuCozinha(Scanner sc) {
		int opcaoCozinha;

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

			opcaoCozinha = inserirInt(sc);

			if (opcaoCozinha == 0) {
				System.out.println("Voltando...");
				break;
			}

			switch (opcaoCozinha) {
			case 1:
				if (cozinha.getPedidosPendentes().isEmpty()) {
					System.out.println("Nao existem pedidos pendentes.");
					break;
				} else {
					cozinha.mostrarPedidosPendentes();
				}
				break;
			case 2:
				if (pedidos.getListaPedidos().isEmpty()) {
					System.out.println("Nao existem pedidos.");
					break;
				} else {
					pedidos.listar();
				}
				break;
			case 3:
				if (pessoa.getListaClientes().isEmpty()) {
					System.out.println("Nao exitem clientes registrados");
					break;
				} else {
					pessoa.listarCliente();
				}
				boolean certoC = false;

				System.out.println("Insira o id do cliente.");

				int idCliente = inserirInt(sc);
				sc.nextLine();

				Cliente cliente = null;
				for (Cliente c : pessoa.getListaClientes()) {
					if (c.getId() == idCliente) {
						certoC = true;
						cliente = c;
						System.out.println("Id encontrado.");
						break;
					}
				}

				if (!certoC) {
					break;
				}

				int opcaoCategoria;
				int continuar = 0;

				do {
					System.out.println("Qual a categoria do pedido?");
					imprimirCategorias();
					opcaoCategoria = inserirInt(sc);
					sc.nextLine();

					ItemPedido novoPedido = null;

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
						continue;
					}

					if (novoPedido != null) {
						System.out.println("Deseja continuar (1) ou cancelar o pedido (2)?");
						int continuar1 = inserirInt(sc);
						sc.nextLine();

						if (continuar1 == 1) {
							cozinha.adicionarPedidoEmPendente(novoPedido);
						} else if (continuar1 == 2) {
							pedidos.removerItem(novoPedido.getId());
						}

					}

					System.out.println("Deseja adicionar mais itens? (1)Sim / (2)Nao");
					continuar = inserirInt(sc);
					sc.nextLine();
				} while (continuar == 1);
				break;
			case 4:
				if (cozinha.getPedidosPendentes().isEmpty()) {
					System.out.println("Nao exitem pedidos pendentes");
					break;
				} else {
					cozinha.mostrarPedidosPendentes();
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

				cozinha.marcarPedidoComoPreparando(pedidoPreparar);
				break;
			case 5:
				if (cozinha.getPedidosEmPreparacao().isEmpty()) {
					System.out.println("Nao existem pedidos para serem finalizados");
					break;
				} else {
					cozinha.mostrarPedidosEmPreparacao();
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

				cozinha.marcarPedidoComoPronto(pedidoPronto);
				break;
			case 6:
				if (cozinha.getPedidosPendentes().isEmpty()) {
					System.out.println("Nao exitem pedidos pendentes");
				} else {
					cozinha.mostrarPedidosPendentes();
					System.out.println();
				}

				if (cozinha.getPedidosEmPreparacao().isEmpty()) {
					System.out.println("Nao exitem pedidos em preparação");
				} else {
					cozinha.mostrarPedidosEmPreparacao();
					System.out.println();
				}

				if (cozinha.getPedidosProntos().isEmpty()) {
					System.out.println("Nao exitem pedidos prontos");
				} else {
					cozinha.mostrarPedidosProntos();
				}
				break;
			}
		} while (opcaoCozinha != 0);
	}

}