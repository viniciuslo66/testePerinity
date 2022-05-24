package com.viniciuslo66.testePerinity.api.controller;

import com.viniciuslo66.testePerinity.api.dto.DepartamentoDTO;
import com.viniciuslo66.testePerinity.error.RegraNegocioException;
import com.viniciuslo66.testePerinity.model.entity.Departamento;
import com.viniciuslo66.testePerinity.service.DepartamentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departamento")
@RequiredArgsConstructor
public class DepartamentoController {

  private final DepartamentoService service;

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody DepartamentoDTO dto) {

    Departamento departamento = Departamento.builder().titulo(dto.getTitulo()).build();
    try {
      Departamento departamentoSalvo = service.salvarDepartamento(departamento);
      return new ResponseEntity(departamentoSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody DepartamentoDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Departamento departamento = converter(dto);
        departamento.setId(entity.getId());
        service.atualizarDepartamento(departamento);
        return ResponseEntity.ok(departamento);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Departamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entidade -> {
      service.deletarDepartamento(entidade);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> new ResponseEntity("Departamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  private Departamento converter(DepartamentoDTO dto) {
    Departamento departamento = new Departamento();
    departamento.setId(dto.getId());
    departamento.setTitulo(dto.getTitulo());
    return departamento;
  }
}
