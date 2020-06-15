package br.com.albergue.tests;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.controller.form.LoginForm;
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class Teste {

	@Autowired
	CustomerRepository customerRespository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	private URI uri;
	private HttpHeaders headers = new HttpHeaders();
	private Address address = new Address();
	private Customer customer = new Customer();
	private LoginForm login = new LoginForm();
 
	@Before
	public void init() throws JsonProcessingException, Exception {
		uri = new URI("/api/customers");
		
		//setting login variables to autenticate
		login.setEmail("aluno@email.com");
		login.setPassword("123456");

		// setting address to put into the customer parameters
		address.setAddressName("rua x");
		address.setCity("Amparo");
		address.setCountry("Brasil");
		address.setState("SP");
		address.setZipCode("13900-000");

		// setting customer
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setLastName("Ferrolho");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");
		
		//posting on /auth to get token
		MvcResult result = mockMvc
				.perform(post("/auth")
				.content(objectMapper.writeValueAsString(login)).contentType("application/json"))
				.andReturn();	
			
		String contentAsString = result.getResponse().getContentAsString();

		TokenDto response = objectMapper.readValue(contentAsString, TokenDto.class);
		
		// seting header to put on post and delete request parameters
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + response.getToken());
	}

	@Test
	public void shouldAutenticateAndDeleteOneCustomerWithId2() throws Exception {
		addressRepository.save(address);
		customerRespository.save(customer);

		mockMvc
			.perform(delete(uri+"/2")
			.headers(headers))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();	
	}
	
	@Test
	public void shouldAutenticateAndCreateOneCustomerAndReturnStatusCreated() throws Exception {

		MvcResult result = 
				mockMvc
					.perform(post(uri)
					.headers(headers)
					.content(objectMapper.writeValueAsString(customer))
					.contentType("application/json"))
					.andDo(print())
					.andExpect(status().isCreated())
					.andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();

		CustomerDto customerObjResponse = objectMapper.readValue(contentAsString, CustomerDto.class);

		assertEquals(customerObjResponse.getName(), "Washington");
		assertEquals(customerObjResponse.getAddress().getCity(), "Amparo");
	}
}