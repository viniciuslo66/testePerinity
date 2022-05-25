package com.viniciuslo66.testePerinity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.viniciuslo66.testePerinity.api.dto.PessoasHorasDTO;
import com.viniciuslo66.testePerinity.model.Repository.PessoaRepository;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.service.PessoaService;
import com.viniciuslo66.testePerinity.service.TarefaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PessoaServiceImpl implements PessoaService {

  private PessoaRepository repository;
  private TarefaService tarefaService;

  public PessoasHorasDTO builderPessoasHorasDTO(Pessoa pessoa) {
    return PessoasHorasDTO.builder().nome(pessoa.getNome()).departamento(pessoa.getDepartamento().getTitulo())
        .horasTrabalhadas(tarefaService.obterPrazoPorUsuario(pessoa)).build();
  }

  @Override
  @Transactional
  public Pessoa salvarPessoa(Pessoa pessoa) {
    return repository.save(pessoa);
  }

  @Override
  @Transactional
  public Pessoa atualizarPessoa(Pessoa pessoa) {
    Objects.requireNonNull(pessoa.getId());
    return repository.save(pessoa);
  }

  @Override
  public void deletarPessoa(Pessoa pessoa) {
    Objects.requireNonNull(pessoa.getId());
    repository.delete(pessoa);
  }

  @Override
  public Optional<Pessoa> obterPorId(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<PessoasHorasDTO> buscar() {
    List<Pessoa> listaPessoa = repository.findAll();
    List<PessoasHorasDTO> pessoasHorasDTO = new ArrayList<>();
    listaPessoa.forEach(pessoa -> pessoasHorasDTO.add(builderPessoasHorasDTO(pessoa)));
    return pessoasHorasDTO;
  }
}
