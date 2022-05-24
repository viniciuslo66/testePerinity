package com.viniciuslo66.testePerinity.model.Repository;

import com.viniciuslo66.testePerinity.model.entity.Tarefa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
}
