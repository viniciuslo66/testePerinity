package com.viniciuslo66.testePerinity.api.controller;

import java.util.List;

import com.viniciuslo66.testePerinity.api.dto.AlocarPessoaDTO;
import com.viniciuslo66.testePerinity.api.dto.TarefaDTO;
import com.viniciuslo66.testePerinity.error.RegraNegocioException;
import com.viniciuslo66.testePerinity.model.entity.Departamento;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.model.entity.Tarefa;
import com.viniciuslo66.testePerinity.service.DepartamentoService;
import com.viniciuslo66.testePerinity.service.PessoaService;
import com.viniciuslo66.testePerinity.service.TarefaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tarefas")
@AllArgsConstructor
public class TarefaController {

  private final TarefaService service;
  private final DepartamentoService departamentoService;
  private final PessoaService pessoaService;

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody TarefaDTO dto) {
    try {
      Tarefa entidade = converter(dto);
      entidade = service.salvar(entidade);
      return new ResponseEntity(entidade, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/alocar/{id}")
  public ResponseEntity<?> alocarPessoa(@PathVariable("id") Long id, @RequestBody AlocarPessoaDTO idPessoa) {
    return service.obterPorId(id).map(entity -> {

      Pessoa pessoa = pessoaService.obterPorId(idPessoa.getId())
          .orElseThrow(() -> new RegraNegocioException("Pessoa não encontrada para o Id informado."));

      try {
        entity.setPessoa(pessoa);
        service.atualizar(entity);
        return ResponseEntity.ok(entity);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Tarefa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  @PutMapping("/finalizar/{id}")
  public ResponseEntity finalizar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entity -> {

      try {
        service.finalizarTarefa(entity);
        return ResponseEntity.ok(entity);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Tarefa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  @GetMapping
  public ResponseEntity buscar(
      @RequestParam(value = "titulo", required = false) String titulo) {

    Tarefa tarefaFiltro = new Tarefa();
    tarefaFiltro.setTitulo(titulo);

    List<Tarefa> tarefas = service.buscar(tarefaFiltro);
    return ResponseEntity.ok(tarefas);
  }

  private Tarefa converter(TarefaDTO dto) {
    Tarefa tarefa = new Tarefa();
    tarefa.setId(dto.getId());
    tarefa.setTitulo(dto.getTitulo());
    tarefa.setDescricao(dto.getDescricao());
    tarefa.setPrazo(dto.getPrazo());
    tarefa.setDuracao(dto.getDuracao());
    tarefa.setFinalizado(dto.isFinalizado());

    Departamento departamento = departamentoService
        .obterPorId(dto.getDepartamento())
        .orElseThrow(() -> new RegraNegocioException("departamento não encontrado para o Id informado."));
    tarefa.setDepartamento(departamento);

    if (dto.getPessoa() != null) {
      Pessoa pessoa = pessoaService
          .obterPorId(dto.getPessoa())
          .orElseThrow(() -> new RegraNegocioException("Pessoa não encontrada para o Id informado."));
      tarefa.setPessoa(pessoa);
    }

    return tarefa;
  }
}
