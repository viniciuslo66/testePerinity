package com.viniciuslo66.testePerinity.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciuslo66.testePerinity.model.Repository.PessoaRepository;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

  private PessoaRepository repository;

  public PessoaServiceImpl(PessoaRepository repository) {
    this.repository = repository;
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
}
