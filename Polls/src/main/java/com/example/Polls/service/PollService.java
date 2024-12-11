package com.example.Polls.service;

import com.example.Polls.controller.PollController;
import com.example.Polls.exception.ResourceNotFoundException;
import com.example.Polls.models.Poll;
import com.example.Polls.repository.PollRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class PollService {

    @Autowired
    PollRepository pollRepository;

    private static final Logger logger = LoggerFactory.getLogger(PollService.class);



    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();

        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    public ResponseEntity<?> createPoll(Poll poll) {

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
        pollRepository.existsById(pollId);

        Poll poll = pollRepository.findById(pollId).orElse(null);
        if(poll == null) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        logger.info("Verifying if poll exist with ID: {}", pollId);

    }


    public ResponseEntity<?> getPoll( Long pollId) {
        pollRepository.findById(pollId);
        Poll p = pollRepository.findById(pollId).orElse(null);
        logger.info("Getting a poll by it's ID: {}",  pollId);
        return new ResponseEntity<> (p, HttpStatus.OK);

    }

    public ResponseEntity<?> updatePoll( Poll poll, Long pollId) {
        pollRepository.existsById(pollId);
        pollRepository.save(poll);
        logger.info("Updating poll with id: {}, new data: {}", pollId, poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deletePoll(Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        logger.info("Deleting poll with ID: {}",  pollId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
