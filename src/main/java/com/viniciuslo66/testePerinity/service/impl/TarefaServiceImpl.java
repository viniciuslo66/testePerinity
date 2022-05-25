package com.viniciuslo66.testePerinity.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.viniciuslo66.testePerinity.error.RegraNegocioException;
import com.viniciuslo66.testePerinity.model.Repository.TarefaRepository;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.model.entity.Tarefa;
import com.viniciuslo66.testePerinity.service.TarefaService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarefaServiceImpl implements TarefaService {

  private TarefaRepository repository;

  public TarefaServiceImpl(TarefaRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Tarefa salvar(Tarefa tarefa) {
    validar(tarefa);
    tarefa.setFinalizado(false);
    return repository.save(tarefa);
  }

  @Override
  @Transactional
  public Tarefa atualizar(Tarefa tarefa) {
    validar(tarefa);
    Objects.requireNonNull(tarefa.getId());
    return repository.save(tarefa);
  }

  @Override
  @Transactional
  public Tarefa finalizarTarefa(Tarefa tarefa) {
    tarefa.setFinalizado(true);
    Objects.requireNonNull(tarefa.getId());
    return repository.save(tarefa);
  }

  @Override
  public void deletar(Tarefa tarefa) {
    Objects.requireNonNull(tarefa.getId());
    repository.delete(tarefa);
  }

  @Override
  public void validar(Tarefa tarefa) {

    LocalDate hoje = LocalDate.now();

    if (tarefa.getTitulo() == null || tarefa.getTitulo().trim().equals("")) {
      throw new RegraNegocioException("Informe um titulo válido.");
    }

    if (tarefa.getDescricao() == null || tarefa.getDescricao().trim().equals("")) {
      throw new RegraNegocioException("Informe uma descrição válida.");
    }

    if (tarefa.getPrazo() == null || tarefa.getPrazo().compareTo(hoje) <= 0) {
      throw new RegraNegocioException("Informe um prazo válido");
    }

    if (tarefa.getDepartamento() == null) {
      throw new RegraNegocioException("Informe um departamento.");
    }

    if (tarefa.getPessoa() != null && tarefa.getPessoa().getDepartamento().getId() != tarefa.getDepartamento().getId()) {
      throw new RegraNegocioException("Informe uma pessoa do mesmo departamento registrado.");
    }

    if (tarefa.getDuracao() == null || tarefa.getDuracao() < 0) {
      throw new RegraNegocioException("Informe uma duração válida.");
    }

    if (tarefa.isFinalizado() == true) {
      throw new RegraNegocioException("Não pode finalizar uma tarefa que acabou de criar");
    }
  }

  @Override
  public List<Tarefa> buscar(Tarefa tarefaFiltro) {
    Example example = Example.of(tarefaFiltro,
        ExampleMatcher.matching()
            .withIgnoreCase()
            .withStringMatcher(StringMatcher.CONTAINING));

    return repository.findAll(example);
  }

  @Override
  public Optional<Tarefa> obterPorId(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Integer obterPrazoPorUsuario(Pessoa pessoa) {
    List<Tarefa> tarefas = new ArrayList<>();
    Integer horasTrabalhadas = 0; 

    if (Objects.nonNull(pessoa)) {
      tarefas = repository.findByPessoa(pessoa);
      for (Tarefa tarefa : tarefas) {
        horasTrabalhadas += tarefa.getDuracao();
      }
    }

    return horasTrabalhadas;
  }

}
