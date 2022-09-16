package com.integration.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.integration.model.h2model.JournalH2Datamodel;
import com.integration.repository.JournalRepository;

@RestController()
@RequestMapping("/journals")
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
	
	@PutMapping("/{id}")
	@ResponseBody
	public JournalH2Datamodel updateJournal(@PathVariable String id, @RequestParam String paramType, @RequestBody JournalH2Datamodel journal) {
		if(!paramType.equalsIgnoreCase("message")) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot update specified parameter. Please supply a different query param and try again");
		} else {
			Optional<JournalH2Datamodel> optionalJournal = this.journalRepo.findById(id);
			if(optionalJournal.isPresent()) {
				JournalH2Datamodel journalToUpdate = optionalJournal.get();
				journalToUpdate.setMessage(journal.getMessage());
				this.journalRepo.save(journalToUpdate);
				return this.journalRepo.findById(id).get();
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Journal found with supplied id. Please try again with different id");
			}
		}
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public JournalH2Datamodel deleteJournal(@PathVariable("id") String id) {
		Optional<JournalH2Datamodel> optionalJournalToDelete = this.journalRepo.findById(id);
		if(optionalJournalToDelete.isPresent()) {
			this.journalRepo.delete(optionalJournalToDelete.get());
			return optionalJournalToDelete.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Journal found with supplied id. Please try again with different id");
		}
	}
}
