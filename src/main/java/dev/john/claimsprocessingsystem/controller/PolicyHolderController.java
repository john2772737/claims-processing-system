package dev.john.claimsprocessingsystem.controller;


import dev.john.claimsprocessingsystem.entity.PolicyHolder;
import dev.john.claimsprocessingsystem.service.PolicyHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-holders")
public class PolicyHolderController {

    @Autowired
    private PolicyHolderService service;

    @PostMapping
    public ResponseEntity<PolicyHolder> registerPolicyHolder(@RequestBody PolicyHolder holder) {
        PolicyHolder createdHolder = service.registerPolicyHolder(holder);
        return new ResponseEntity<>(createdHolder, HttpStatus.CREATED); // Returns 201 Created
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicyHolder(@PathVariable Long id) {

        service.deletePolicyHolder(id);

        // Returns HTTP Status 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<PolicyHolder> editPolicyHolder(@PathVariable Long id, @RequestBody PolicyHolder updatedData){
        PolicyHolder editHolder = service.editPolicyHolder(id,updatedData );
        return ResponseEntity.ok(editHolder);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<PolicyHolder.PolicyHolderSummary> getSummaryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSummaryById(id));
    }

    @GetMapping("/policyHolders")
    public ResponseEntity<List<PolicyHolder>> allPolicyHolder(){
        List<PolicyHolder> policyHolders = service.allPolicyHolder();
        return ResponseEntity.ok(policyHolders);
    }
}
