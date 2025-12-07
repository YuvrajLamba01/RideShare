package org.example.rideshare.repository;

import java.util.List;

import org.example.rideshare.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {

    List<Ride> findByUserId(String userId);

    List<Ride> findByStatus(String status);

    List<Ride> findByDriverId(String driverId);
}
