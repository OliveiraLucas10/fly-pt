package com.oliveiralucaspro.flypt.repositories;

import org.springframework.data.repository.CrudRepository;

import com.oliveiralucaspro.flypt.domain.Request;

public interface RequestRepository extends CrudRepository<Request, String> {

}
