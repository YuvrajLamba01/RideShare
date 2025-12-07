package org.example.rideshare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.RideRepository;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final AuthService authService;

    public RideService(RideRepository rideRepository, AuthService authService) {
        this.rideRepository = rideRepository;
        this.authService = authService;
    }

    // Create a new ride request (for USER)
    public RideResponse createRide(CreateRideRequest request, String username) {
        User user = authService.findByUsername(username);

        Ride ride = new Ride(
                user.getId(),
                request.getPickupLocation(),
                request.getDropLocation()
        );

        Ride savedRide = rideRepository.save(ride);
        return new RideResponse(savedRide);
    }

    // Get all rides for a user
    public List<RideResponse> getUserRides(String username) {
        User user = authService.findByUsername(username);
        List<Ride> rides = rideRepository.findByUserId(user.getId());

        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    // Get all pending ride requests (for DRIVER)
    public List<RideResponse> getPendingRides() {
        List<Ride> rides = rideRepository.findByStatus("REQUESTED");

        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    // Accept a ride (for DRIVER)
    public RideResponse acceptRide(String rideId, String driverUsername) {
        User driver = authService.findByUsername(driverUsername);

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus("ACCEPTED");

        Ride updatedRide = rideRepository.save(ride);
        return new RideResponse(updatedRide);
    }

    // Complete a ride (for USER or DRIVER)
    public RideResponse completeRide(String rideId, String username) {
        User user = authService.findByUsername(username);

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride must be ACCEPTED before completing");
        }

        // Verify that either the user (passenger) or driver is completing the ride
        boolean isPassenger = ride.getUserId().equals(user.getId());
        boolean isDriver = ride.getDriverId() != null && ride.getDriverId().equals(user.getId());

        if (!isPassenger && !isDriver) {
            throw new BadRequestException("You are not authorized to complete this ride");
        }

        ride.setStatus("COMPLETED");

        Ride updatedRide = rideRepository.save(ride);
        return new RideResponse(updatedRide);
    }
}
