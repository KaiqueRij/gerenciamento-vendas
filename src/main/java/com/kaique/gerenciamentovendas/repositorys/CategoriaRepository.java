package com.kaique.gerenciamentovendas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaique.gerenciamentovendas.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
