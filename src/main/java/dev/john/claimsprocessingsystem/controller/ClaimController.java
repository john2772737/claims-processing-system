package dev.john.claimsprocessingsystem.controller;

import dev.john.claimsprocessingsystem.entity.Claim;
import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    @Autowired
    private ClaimService service;

    @PostMapping()
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim){
         Claim newClaim = service.createClaim(claim);
         return new ResponseEntity<>(newClaim, HttpStatus.CREATED);
    }

    @GetMapping("/allClaims")
    public ResponseEntity<List<Claim>> allClaims(){
        List<Claim> claims = service.allClaims();

        return ResponseEntity.ok(claims);
    }

    @GetMapping("/summary/{claimNumber}")
    public ResponseEntity<Claim.ClaimSummary> getClaimSummary(@PathVariable String claimNumber) {
        return ResponseEntity.ok(service.getClaimByClaimNumber(claimNumber));
    }

    @PatchMapping("/{claimNumber}")
    public ResponseEntity<Claim> updateClaim(@PathVariable String claimNumber,@RequestBody Claim updatedData){
        Claim updatedClaim = service.updateClaim(claimNumber, updatedData);
        return ResponseEntity.ok(updatedClaim);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable long id){
        service.deleteClaim(id);

        return ResponseEntity.noContent().build();
    }

}
