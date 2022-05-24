package com.viniciuslo66.testePerinity.service;

import java.util.Optional;

import com.viniciuslo66.testePerinity.model.entity.Departamento;

public interface DepartamentoService {
  Departamento salvarDepartamento(Departamento Departamento);

  Departamento atualizarDepartamento(Departamento pessoa);

  void deletarDepartamento(Departamento pessoa);

  Optional<Departamento> obterPorId(Long id);
}
