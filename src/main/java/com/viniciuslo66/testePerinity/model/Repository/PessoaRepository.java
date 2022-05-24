package com.viniciuslo66.testePerinity.model.Repository;

import com.viniciuslo66.testePerinity.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
}
