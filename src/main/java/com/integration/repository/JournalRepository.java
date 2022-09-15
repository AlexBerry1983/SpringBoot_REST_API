package com.integration.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.integration.model.h2model.JournalH2Datamodel;

@Repository
public interface JournalRepository extends CrudRepository<JournalH2Datamodel,Integer>{
	
	Optional<JournalH2Datamodel> findById(String id);
}
