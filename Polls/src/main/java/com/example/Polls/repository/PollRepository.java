package com.example.Polls.repository;

import com.example.Polls.models.Poll;
 import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll,Long> {
}
