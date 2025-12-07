package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // POST /api/v1/rides - Create a new ride (USER only)
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication authentication) {

        String username = authentication.getName();
        RideResponse response = rideService.createRide(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // POST /api/v1/rides/{rideId}/complete - Complete a ride (USER or DRIVER)
    @PostMapping("/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId,
            Authentication authentication) {

        String username = authentication.getName();
        RideResponse response = rideService.completeRide(rideId, username);
        return ResponseEntity.ok(response);
    }
}
