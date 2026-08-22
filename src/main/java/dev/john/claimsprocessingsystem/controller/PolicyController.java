package dev.john.claimsprocessingsystem.controller;

import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    @Autowired
    private PolicyService service;


    @PostMapping
    public ResponseEntity<Policy> registerPolicy (@RequestBody Policy policy){
        Policy createPolicy = service.registerNewPolicy(policy);
        return new ResponseEntity<>(createPolicy, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable long id){
         service.deletePolicy(id);

         return ResponseEntity.noContent().build();
    }

    @GetMapping("/allPolicy")
    public  ResponseEntity<List<Policy>> allPolicies(){
        List<Policy> policies= service.allPolicy();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/summary/{policyNumber}")
    public ResponseEntity<Policy.PolicySummary> getPolicySummary(@PathVariable String policyNumber) {
        Policy.PolicySummary summary = service.getSummaryByPolicyNumber(policyNumber);
        return ResponseEntity.ok(summary);
    }

    @PatchMapping("/{policyNumber}")
    public ResponseEntity<Policy> editPolicy( @PathVariable String policyNumber, @RequestBody Policy updatedData){
        Policy updatedPolicy = service.editPolicy(policyNumber, updatedData);
        return ResponseEntity.ok(updatedPolicy);
    }



}
