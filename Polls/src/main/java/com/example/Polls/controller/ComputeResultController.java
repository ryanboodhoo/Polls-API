package com.example.Polls.controller;

import com.example.Polls.Dto.OptionCount;
import com.example.Polls.Dto.VoteResult;
import com.example.Polls.models.Vote;
import com.example.Polls.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ComputeResultController {

    private static final Logger logger = LoggerFactory.getLogger(ComputeResultController.class);

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping(value="/computeresult")
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findVotesByPoll(pollId);

        int totalVotes = 0;
        Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
        for(Vote v : allVotes) {
            totalVotes ++;
            // Get the OptionCount corresponding to this Options
            OptionCount optionCount = tempMap.get(v.getOption().getId());
            if(optionCount == null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount()+1);
        }
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());
        logger.info("Getting total votes of poll: {}",  pollId);


        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);

    }

}
