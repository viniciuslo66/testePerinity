package com.viniciuslo66.testePerinity.service;

import java.util.List;
import java.util.Optional;

import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.model.entity.Tarefa;

public interface TarefaService {
  Tarefa salvar(Tarefa Tarefa);

  Tarefa atualizar(Tarefa Tarefa);

  Tarefa finalizarTarefa(Tarefa tarefa);

  void deletar(Tarefa Tarefa);

  List<Tarefa> buscar(Tarefa TarefaFiltro);

  public void validar(Tarefa tarefa);

  Optional<Tarefa> obterPorId(Long id);

  Integer obterPrazoPorUsuario(Pessoa pessoa);
}
