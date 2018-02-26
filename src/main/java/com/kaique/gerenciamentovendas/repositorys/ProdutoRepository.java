package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
