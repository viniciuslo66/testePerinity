package com.viniciuslo66.testePerinity.service;

import java.util.Optional;

import com.viniciuslo66.testePerinity.model.entity.Pessoa;

public interface PessoaService {
  Pessoa salvarPessoa(Pessoa Pessoa);

  Pessoa atualizarPessoa(Pessoa pessoa);

  void deletarPessoa(Pessoa pessoa);

  Optional<Pessoa> obterPorId(Long id);
}
