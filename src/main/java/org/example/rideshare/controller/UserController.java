package org.example.rideshare.controller;

import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final RideService rideService;

    public UserController(RideService rideService) {
        this.rideService = rideService;
    }

    // GET /api/v1/user/rides - Get all rides for the logged-in user
    @GetMapping("/rides")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<RideResponse>> getMyRides(Authentication authentication) {
        String username = authentication.getName();
        List<RideResponse> rides = rideService.getUserRides(username);
        return ResponseEntity.ok(rides);
    }
}