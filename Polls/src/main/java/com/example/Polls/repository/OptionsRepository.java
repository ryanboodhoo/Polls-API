package com.example.Polls.repository;

import com.example.Polls.models.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionsRepository extends CrudRepository<Option,Long> {
}