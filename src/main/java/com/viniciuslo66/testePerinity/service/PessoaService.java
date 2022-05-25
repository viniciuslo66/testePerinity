package com.viniciuslo66.testePerinity.service;

import java.util.List;
import java.util.Optional;

import com.viniciuslo66.testePerinity.api.dto.PessoasHorasDTO;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;

public interface PessoaService {
  Pessoa salvarPessoa(Pessoa Pessoa);

  Pessoa atualizarPessoa(Pessoa pessoa);

  List<PessoasHorasDTO> buscar();

  void deletarPessoa(Pessoa pessoa);

  Optional<Pessoa> obterPorId(Long id);
}
