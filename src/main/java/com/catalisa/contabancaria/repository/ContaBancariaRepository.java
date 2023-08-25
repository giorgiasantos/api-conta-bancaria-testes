package com.catalisa.contabancaria.repository;

import com.catalisa.contabancaria.model.ContaBancariaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaBancariaRepository extends CrudRepository<ContaBancariaModel, Long> {
}
