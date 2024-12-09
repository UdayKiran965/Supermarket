package com.uday.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.Repository.MydairyRepository;
import com.uday.binding.Mydairy;
@Service
public class MydairyService {
	 @Autowired
	    private MydairyRepository repository;

	    public List<Mydairy> getAllEntries() {
	    	
	    	return repository.findAll();
	    	
	    }
	    
	    public Mydairy getEntry(Long id) {
	    	return repository.findById(id).orElse(null);
	    	}
	    
	    public Mydairy saveEntry(Mydairy entry) {
	    	return repository.save(entry); 
	    	}
	    
	    public void deleteEntry(Long id) { 
	    	repository.deleteById(id); 
	    	}
	}
