package com.company.demo.service;

import com.company.demo.entity.Company;
import com.company.demo.entity.Department;
import com.company.demo.entity.Project;
import com.company.demo.entity.Team;
import com.company.demo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<Company> retrieveCompany(Long id) {
        return companyRepository.findById(id);
    }

    public Company saveUpdateCompany(Company company) {
        for (Department department : company.getDepartments()) {
            department.setCompany(company);
            for (Team team : department.getTeams()) {
                team.setDepartment(department);
                for (Project project : team.getProjects()) {
                    project.setTeam(team);
                }
            }
        }
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
