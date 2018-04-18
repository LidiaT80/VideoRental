package com.example.demo.repositories;

import com.example.demo.entities.ReturnedMovie;
import com.example.demo.entities.compositeKeys.ReturnId;
import org.springframework.data.repository.CrudRepository;

public interface ReturnRepository extends CrudRepository<ReturnedMovie, ReturnId> {
}
