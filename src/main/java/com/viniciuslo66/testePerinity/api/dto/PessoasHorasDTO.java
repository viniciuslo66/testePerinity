package com.viniciuslo66.testePerinity.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoasHorasDTO {
 
  private String nome;
  private String departamento;
  private Integer horasTrabalhadas; 

}
