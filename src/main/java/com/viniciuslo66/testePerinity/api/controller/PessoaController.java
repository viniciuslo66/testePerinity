package com.viniciuslo66.testePerinity.api.controller;

import com.viniciuslo66.testePerinity.api.dto.PessoaDTO;
import com.viniciuslo66.testePerinity.error.RegraNegocioException;
import com.viniciuslo66.testePerinity.model.entity.Departamento;
import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import com.viniciuslo66.testePerinity.service.DepartamentoService;
import com.viniciuslo66.testePerinity.service.PessoaService;

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
@RequestMapping("/post/pessoas")
@RequiredArgsConstructor
public class PessoaController {

	private final PessoaService service;
	private final DepartamentoService departamentoService;

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody PessoaDTO dto) {
		try {
			Pessoa entidade = converter(dto);
			entidade = service.salvarPessoa(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody PessoaDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				Pessoa pessoa = converter(dto);
				pessoa.setId(entity.getId());
				service.atualizarPessoa(pessoa);
				return ResponseEntity.ok(pessoa);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Pessoa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletarPessoa(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Pessoa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}

	private Pessoa converter(PessoaDTO dto) {
		Pessoa Pessoa = new Pessoa();
		Pessoa.setId(dto.getId());
		Pessoa.setNome(dto.getNome());

		Departamento departamento = departamentoService
				.obterPorId(dto.getDepartamento())
				.orElseThrow(() -> new RegraNegocioException("Pessoa não encontrado para o Id informado."));

		Pessoa.setDepartamento(departamento);
		return Pessoa;
	}
}
