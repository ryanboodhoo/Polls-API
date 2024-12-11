package com.example.Polls.controller;

import com.example.Polls.models.Poll;
import com.example.Polls.repository.PollRepository;
import com.example.Polls.service.PollService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        return pollService.createPoll(poll);
    }


    @GetMapping(value="/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
   return pollService.getPoll(pollId);

    }

    @PutMapping(value="/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
    return pollService.updatePoll(poll,pollId);
    }

    @DeleteMapping(value="/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        return pollService.deletePoll(pollId);
    }
}


