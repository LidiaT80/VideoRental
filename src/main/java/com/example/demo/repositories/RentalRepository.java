package com.example.demo.repositories;

import com.example.demo.entities.Rental;
import com.example.demo.entities.compositeKeys.RentalId;
import org.springframework.data.repository.CrudRepository;

public interface RentalRepository extends CrudRepository<Rental, RentalId> {
}
