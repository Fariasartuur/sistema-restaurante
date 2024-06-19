//Este código Java define uma interface MetodosPagamento que declara métodos para diferentes formas de
// pagamento utilizando um GerenciadorMenu e um Scanner como parâmetros.

package com.artur.interfaces;

import com.artur.gerenciamento.GerenciadorMenu;

import java.util.Scanner;

public interface MetodosPagamento {

    void pagarComDinheiro(GerenciadorMenu menu, Scanner sc);

    void pagarComCartao(GerenciadorMenu menu, Scanner sc);

    void pagarComCarteiraDigital(GerenciadorMenu menu, Scanner sc);

    void pagarComPix(GerenciadorMenu menu, Scanner sc);


}
