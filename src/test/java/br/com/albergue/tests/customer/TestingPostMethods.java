package br.com.albergue.tests.customer;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.controller.form.LoginForm;
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestingPostMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	int port = 8080;
	
	private String baseUrl = "http://localhost:" + port;
	private URI uri;
	private ResponseEntity<TokenDto> auth;
	private HttpHeaders headers;
	private Address address = new Address();
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI(baseUrl + "/api/customers");

		//login
		LoginForm login = new LoginForm();
		login.setEmail("aluno@email.com");
		login.setPassword("123456");
		auth = restTemplate.postForEntity(baseUrl + "/auth", login, TokenDto.class);
		
		//getting authorization
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ auth.getBody().getToken());
		
		//setting address to put into the customer parameters
		address.setAddressName("rua tal");
		address.setCity("alguma");
		address.setCountry("algum");
		address.setState("XXX");
		address.setZipCode("xxxx-xxx");
	}
	
	@Test
	public void testRegisterCustomerMethod() {
		Customer customer = new Customer();
		customer.setLastName("aaaaaaaaaa");
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");

		HttpEntity<?> entity = new HttpEntity<Object>(customer, headers);
		
		ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
//
//		System.out.println(result.getBody());
		// Verify request succeed
		Assert.assertEquals(200, auth.getStatusCodeValue());
		Assert.assertEquals(201, result.getStatusCodeValue());
	}
}