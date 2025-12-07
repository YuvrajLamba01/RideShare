package org.example.rideshare.controller;

import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final RideService rideService;

    public DriverController(RideService rideService) {
        this.rideService = rideService;
    }

    // GET /api/v1/driver/rides/requests - View all pending ride requests
    @GetMapping("/rides/requests")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<List<RideResponse>> getPendingRides() {
        List<RideResponse> rides = rideService.getPendingRides();
        return ResponseEntity.ok(rides);
    }

    // POST /api/v1/driver/rides/{rideId}/accept - Accept a ride
    @PostMapping("/rides/{rideId}/accept")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable String rideId,
            Authentication authentication) {

        String username = authentication.getName();
        RideResponse response = rideService.acceptRide(rideId, username);
        return ResponseEntity.ok(response);
    }
}