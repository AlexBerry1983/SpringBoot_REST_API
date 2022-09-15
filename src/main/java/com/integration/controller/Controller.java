package com.integration.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.integration.model.h2model.JournalH2Datamodel;
import com.integration.repository.JournalRepository;

@RestController()
@RequestMapping("/content")
public class Controller {
	
	private final JournalRepository journalRepo;
	
	public Controller(final JournalRepository journalRepo) {
		this.journalRepo = journalRepo;
	}
	
	@GetMapping()
	@ResponseBody
	public Iterable<JournalH2Datamodel> getAllJournals() {
		return journalRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public JournalH2Datamodel getJournalById(@PathVariable String id) {
		// change to lookup with UUID
		Optional<JournalH2Datamodel> foundJournal = journalRepo.findById(id);
		if(foundJournal.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Journal found with supplied id. Please try again with different id");
		}
		return foundJournal.get();
	}
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ResponseBody
	public JournalH2Datamodel postJournalContent(@RequestBody JournalH2Datamodel journal) {
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		String id = UUID.randomUUID().toString();
		journal.setId(id);
		journal.setStatus("To Process");
		journal.setCreatedOn(LocalDateTime.now().toString());
		journal.setLastModified(LocalDateTime.now().toString());
		this.journalRepo.save(journal);
		Optional<JournalH2Datamodel> newlySavedJournal = this.journalRepo.findById(id);
		if(newlySavedJournal.isPresent()) {
			return newlySavedJournal.get();
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to upload content at this time");
		}
		
	}
}
