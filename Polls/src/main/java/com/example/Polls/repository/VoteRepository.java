package com.example.Polls.repository;

import com.example.Polls.models.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends CrudRepository<Vote,Long> {
    @Query(value= "SELECT v.* " + "FROM Options o, Vote v " + "Where o.POLL_ID = ?1 " + "AND v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
    Iterable<Vote> findVotesByPoll(Long pollId);

}

