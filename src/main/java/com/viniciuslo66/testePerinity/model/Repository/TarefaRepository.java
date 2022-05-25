package com.viniciuslo66.testePerinity.model.Repository;

import java.util.List;

import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.model.entity.Tarefa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

  List<Tarefa> findByPessoa(Pessoa pessoa);
}
