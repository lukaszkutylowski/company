package com.company.demo.controller;

import com.company.demo.entity.Company;
import com.company.demo.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CompanyController {
    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping(path = "company/{id}")
    public ResponseEntity<Company> retrieveCompany(@PathVariable Long id) {
        return service.retrieveCompany(id)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/company")
    public ResponseEntity<Company> saveCompany(@RequestBody Company company) {
        Company savedEntity = service.saveUpdateCompany(company);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/company")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company updatedCompany = service.saveUpdateCompany(company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping(path = "/company/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        service.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
