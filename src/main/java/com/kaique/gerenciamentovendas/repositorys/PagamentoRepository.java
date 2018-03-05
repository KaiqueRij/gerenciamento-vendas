package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
