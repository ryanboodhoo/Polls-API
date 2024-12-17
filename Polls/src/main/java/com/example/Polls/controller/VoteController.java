package com.example.Polls.controller;

import com.example.Polls.models.Vote;
import com.example.Polls.repository.VoteRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;

@RestController

    public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteRepository voteRepository;

        @PostMapping(value="/polls/{pollId}/votes")
        public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote
                vote) {

            vote = voteRepository.save(vote);
            // Set the headers for the newly created resource
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(ServletUriComponentsBuilder.
                    fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
            logger.info("Creating vote: {}", vote);

            return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        }

    @RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
        logger.info("Getting all votes with pollId: {}", pollId);

        return voteRepository.findVotesByPoll(pollId);
    }
}


