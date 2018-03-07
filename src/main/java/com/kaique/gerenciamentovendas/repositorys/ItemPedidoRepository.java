package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
