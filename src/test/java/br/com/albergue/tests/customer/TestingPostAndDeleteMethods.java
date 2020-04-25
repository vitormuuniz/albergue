package br.com.albergue.tests.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.controller.form.LoginForm;
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.JVM) //rodar os testes na ordem de escrita
public class TestingPostAndDeleteMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	CustomerRepository customerRepository;

	@LocalServerPort
	int port = 8080;

	private String baseUrl = "http://localhost:" + port;
	private URI uri;
	private ResponseEntity<TokenDto> auth;
	private HttpHeaders headers;
	private Address address = new Address();
	private Customer customer = new Customer();

	@Before
	public void init() throws URISyntaxException {
		uri = new URI(baseUrl + "/api/customers");

		// login
		LoginForm login = new LoginForm();
		login.setEmail("aluno@email.com");
		login.setPassword("123456");
		auth = restTemplate.postForEntity(baseUrl + "/auth", login, TokenDto.class);

		// getting authorization
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + auth.getBody().getToken());

		// setting address to put into the customer paramseters
		address.setAddressName("rua tal");
		address.setCity("alguma");
		address.setCountry("algum");
		address.setState("XXX");
		address.setZipCode("xxxx-xxx");

		//setting customer
		customer.setLastName("aaaaaaaaaa");
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");
	}

	@Test
	public void testRegisterCustomerMethod() {

		HttpEntity<?> entity = new HttpEntity<Object>(customer, headers);
		ResponseEntity<CustomerDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, CustomerDto.class);
//
		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());
		assertEquals("Washington", result.getBody().getName());
	}
	
	@Test
	public void testDeleteCustomerMethod() {
		
		HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
		ResponseEntity<String> result = restTemplate.exchange(uri+"/1", HttpMethod.DELETE, entity, String.class);
		
		Optional<Customer> findById = customerRepository.findById(1L);
		
		assertEquals(findById.isPresent(), false);
		assertEquals(200, result.getStatusCodeValue());

	}
}