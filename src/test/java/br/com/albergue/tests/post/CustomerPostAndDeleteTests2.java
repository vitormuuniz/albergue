package br.com.albergue.tests.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.albergue.controller.AutenticationController;
import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerPostAndDeleteTests2 {

	@MockBean
	CustomerRepository customerRepository;
	
	@MockBean
	AddressRepository addressRepository;
	
	@MockBean
	private AutenticationController autentication;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@Value("${forum.jwt.expiration}") // @value serve para pegar a propriedade do application.properties
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;

	private URI uri;
	private HttpHeaders headers = new HttpHeaders();
	private Address address = new Address();
	private Customer customer = new Customer();
	private String token;
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/customers");

		// setting address to put into the customer paramseters
		address.setAddressName("rua tal");
		address.setCity("alguma");
		address.setCountry("algum");
		address.setState("XXX");
		address.setZipCode("xxxx-xxx");
		address.setId(1L);
		
		// setting customer
		customer.setAddress(address);
		customer.setBirthday(LocalDate.of(1900, 12, 12));
		customer.setEmail("washington@orkut.com");
		customer.setName("Washington");
		customer.setLastName("Ferrolho");
		customer.setTitle("MRS.");
		customer.setPassword("1234567");
		customer.setId(1L);
		
		//generating token to autentication
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		token = Jwts.builder()
				.setIssuer("API do Albergue") // quem fez a geração do token
				.setSubject(customer.getId().toString()) // usuario a quem esse token pertence
				.setIssuedAt(hoje) // data de geração
				.setExpiration(dataExpiracao) // data de expiração
				.signWith(SignatureAlgorithm.HS256, secret) // usar a senha do application.properties / algoritmo de
															// criptografia
				.compact();
		
		//mocking autenticate return
		Mockito.when(autentication.autenticate(Mockito.any()))
				.thenReturn(ResponseEntity.ok(new TokenDto(token, "Bearer")));
		
		//seting header to put on post and delete request parameters
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
	}

	@Test
	public void test() throws Exception {
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(addressRepository.save(Mockito.any())).thenReturn(address);
		
		MvcResult result = mockMvc.perform(post(uri)
						.headers(headers)
						.content(objectMapper.writeValueAsString(customer)))
						.andDo(print())
						.andExpect(status().isCreated())
						.andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("\"name\":\"Washington\""));

	}

//	@Test
//	public void testDeleteCustomerMethod() throws Exception {
//			mockMvc.perform(delete("/api/rooms/1")
//				.headers(headers))
//	            .andExpect(status().isOk())
//	            .andReturn();
//	}
}