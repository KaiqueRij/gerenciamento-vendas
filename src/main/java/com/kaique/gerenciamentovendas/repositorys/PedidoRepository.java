package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
