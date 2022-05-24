package com.viniciuslo66.testePerinity.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciuslo66.testePerinity.model.Repository.DepartamentoRepository;
import com.viniciuslo66.testePerinity.model.entity.Departamento;
import com.viniciuslo66.testePerinity.service.DepartamentoService;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
  
  private DepartamentoRepository repository;

  public DepartamentoServiceImpl(DepartamentoRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Departamento salvarDepartamento(Departamento departamento) {
    return repository.save(departamento);
  }

  @Override
  public Departamento atualizarDepartamento(Departamento departamento) {
    Objects.requireNonNull(departamento.getId());
    return repository.save(departamento);
  }

  @Override
  public void deletarDepartamento(Departamento departamento) {
    Objects.requireNonNull(departamento.getId());
    repository.delete(departamento);
  }

  @Override
  public Optional<Departamento> obterPorId(Long id) {
    return repository.findById(id);
  }

}
