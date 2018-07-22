package com.jojuva.backend.controller;

import com.jojuva.backend.dto.SummaryDto;
import com.jojuva.backend.dto.TransactionDto;
import com.jojuva.backend.repository.TransactionRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BackendController {

	@Autowired
	private TransactionRepositoryService transactionRepositoryService;

	@RequestMapping(value = "/transactions")
	public ResponseEntity<Void> transactions(@RequestBody TransactionDto transactionDto) {
		boolean success = transactionRepositoryService.createTransaction(transactionDto.getAmount(), transactionDto.getTimestamp());
		if (success) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="/statistics", method=RequestMethod.GET)
	public SummaryDto statistics() {
		return transactionRepositoryService.getSummary();
	}

}
