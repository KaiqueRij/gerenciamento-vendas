package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
