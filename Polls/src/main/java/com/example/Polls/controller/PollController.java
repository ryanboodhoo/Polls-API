package com.example.Polls.controller;

import com.example.Polls.PollsApplication;
import com.example.Polls.exception.ResourceNotFoundException;
import com.example.Polls.models.Poll;
import com.example.Polls.repository.PollRepository;
import com.example.Polls.service.PollService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class PollController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired // replaced for @inject
    private PollRepository pollRepository;

    @Autowired
    private PollService pollService;

    @GetMapping(value = "/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        return pollService.getAllPolls();
    }

    @PostMapping(value="/polls" )
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(poll.getId()).toUri();
        responseHeaders.setLocation(newPollUri);

        logger.info("Creating Poll: {}", poll);


        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if(poll == null) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        logger.info("Verifying if poll exist with ID: {}", pollId);

    }
    @GetMapping(value="/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        Poll p = pollRepository.findById(pollId).orElse(null);
        logger.info("Getting a poll by it's ID: {}",  pollId);
        return new ResponseEntity<> (p, HttpStatus.OK);

    }

    @PutMapping(value="/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);
        logger.info("Updating poll with id: {}, new data: {}", pollId, poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        logger.info("Deleting poll with ID: {}",  pollId);

        return new ResponseEntity<>(HttpStatus.OK);
    }






}


