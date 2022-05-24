package com.viniciuslo66.testePerinity.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarefas", schema = "gerenciamentotarefas")
public class Tarefa {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "descricao")
  private String descricao;

  @Column(name = "prazo")
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate prazo;

  @ManyToOne
  @JoinColumn(name = "id_departamento")
  private Departamento departamento;

  @Column(name = "duracao")
  private Integer duracao;

  @ManyToOne
  @JoinColumn(name = "id_pessoa")
  private Pessoa pessoa;

  @Column(name = "finalizado")
  private boolean finalizado;

}
