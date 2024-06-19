//Esse código define uma interface GerenciamentoPessoas que especifica métodos para adicionar, remover e listar clientes, garçons e gerentes em um sistema de gerenciamento de pessoas

package com.artur.interfaces;

import com.artur.pessoas.Cliente;
import com.artur.pessoas.Garcom;
import com.artur.pessoas.Gerente;

public interface GerenciamentoPessoas {

    void adicionarCliente(Cliente cliente);

    void adicionarGarcom(Garcom garcom);

    void adicionarGerente(Gerente gerente);

    void removerCliente(Long idCliente);

    void removerGarcom(Long idCarcom);

    void removerGerente(Long idGerente);
    
    void listarCliente();

    void listarGarcom();

    void listarGerente();

}
