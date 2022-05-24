package com.viniciuslo66.testePerinity.api.dto;

import java.time.LocalDate;

import javax.persistence.Convert;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDTO {

  private Long id;
  private String titulo;
  private String descricao;

  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate prazo;
  
  private Long departamento;
  private Integer duracao;
  private Long pessoa;
  private boolean finalizado;

}