package br.com.albergue.tests.customer;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestingGetMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@LocalServerPort
	private int port;
	
	private URI uri;
	private Address address = new Address();
	private Customer customer = new Customer();

	@Before
	public void init() throws URISyntaxException {
		
		uri = new URI("/api/customers");

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
		
		addressRepository.save(address);
		customerRepository.save(customer);
	}

	@Test
	public void testListAllCustomersMethodWithoutParam() throws URISyntaxException {

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"name\":\"Washington\""));
	}
	
	@Test
	public void testListAllCustomersMethodByParam() throws URISyntaxException {
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Washington", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"name\":\"Washington\""));
	}
	
	@Test
	public void testListAllCustomersMethodById() throws URISyntaxException {
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri + "/2", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"name\":\"Washington\""));
	}
	
	@Test
	public void testListAllCustomersMethodWithWrongParam() throws URISyntaxException {

		ResponseEntity<String> result = restTemplate.getForEntity(uri + "?name=Washington222", String.class);

		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertFalse(result.getBody().contains("\"name\":\"Washington222\""));
	}

}