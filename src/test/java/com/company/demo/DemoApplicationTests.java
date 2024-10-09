package com.company.demo;

import com.company.demo.entity.Company;
import com.company.demo.entity.Department;
import com.company.demo.entity.Project;
import com.company.demo.entity.Team;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void controllerTest() throws IOException {
		final Long COMPANY_ID;
		String url = "http://localhost:" + port + "/api/v1/company";

		// POST
		ClassPathResource resource = new ClassPathResource("post.json");
		Company company = objectMapper.readValue(resource.getFile(), Company.class);
		ResponseEntity<Company> responsePost = restTemplate.postForEntity(url, company, Company.class);

		assertEquals(HttpStatus.OK, responsePost.getStatusCode());

		COMPANY_ID = Objects.requireNonNull(responsePost.getBody()).getId();

		assertEquals("Example Company Name 1", Objects.requireNonNull(responsePost.getBody()).getName());
		assertEquals(1, Objects.requireNonNull(responsePost.getBody()).getDepartments().size());

		Department department = responsePost.getBody().getDepartments().getFirst();
		assertEquals("Example Department Name 1", department.getName());
		assertEquals(1, department.getTeams().size());

		Team team = department.getTeams().getFirst();
		assertEquals("Example Team Name 1", team.getName());
		assertEquals(1, team.getProjects().size());

		Project project = team.getProjects().getFirst();
		assertEquals("Example contactInformation 1", project.getManager().getContactInformation());

		// GET
		ResponseEntity<Company> responseGet = restTemplate.getForEntity(url + "/" + COMPANY_ID, Company.class);

		assertEquals(HttpStatus.OK, responseGet.getStatusCode());

		assertEquals(responsePost.getBody().getId(), COMPANY_ID);
		assertEquals("Example Company Name 1", Objects.requireNonNull(responseGet.getBody()).getName());
		assertEquals(1, Objects.requireNonNull(responseGet.getBody()).getDepartments().size());

		Department departmentGet = responseGet.getBody().getDepartments().getFirst();
		assertEquals(department.getId(), departmentGet.getId());
		assertEquals("Example Department Name 1", departmentGet.getName());
		assertEquals(1, departmentGet.getTeams().size());

		Team teamGet = departmentGet.getTeams().getFirst();
		assertEquals(team.getId(), teamGet.getId());
		assertEquals("Example Team Name 1", teamGet.getName());
		assertEquals(1, teamGet.getProjects().size());

		Project projectGet = teamGet.getProjects().getFirst();
		assertEquals(project.getManager().getId(), projectGet.getManager().getId());
		assertEquals("Example contactInformation 1", projectGet.getManager().getContactInformation());

		// PUT
		ClassPathResource modifiedResource = new ClassPathResource("put.json");
		Company modifiedCompany = objectMapper.readValue(modifiedResource.getFile(), Company.class);
		modifiedCompany.setId(COMPANY_ID);
		ResponseEntity<Company> responsePut = restTemplate.postForEntity(url, modifiedCompany, Company.class);

		assertEquals(HttpStatus.OK, responsePut.getStatusCode());

		assertEquals("Change Example Company Name 2", Objects.requireNonNull(responsePut.getBody()).getName());
		assertEquals(1, Objects.requireNonNull(responsePut.getBody()).getDepartments().size());

		Department departmentPut = responsePut.getBody().getDepartments().getFirst();
		assertEquals("Change Example Department Name 2", departmentPut.getName());
		assertEquals(1, departmentPut.getTeams().size());

		Team teamPut = departmentPut.getTeams().getFirst();
		assertEquals("Example Team Name 1", teamPut.getName());
		assertEquals(1, teamPut.getProjects().size());

		Project projectPut = teamPut.getProjects().getFirst();
		assertEquals("Change Example contactInformation 2", projectPut.getManager().getContactInformation());

		// DELETE
		restTemplate.delete(url + "/" + COMPANY_ID);
		ResponseEntity<Company> responseGetAfterDelete = restTemplate.getForEntity(url + "/" + COMPANY_ID, Company.class);
		assertEquals(HttpStatus.NOT_FOUND, responseGetAfterDelete.getStatusCode());
	}
}
