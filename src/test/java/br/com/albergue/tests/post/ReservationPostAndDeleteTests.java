package br.com.albergue.tests.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

import br.com.albergue.controller.dto.ReservationDto;
import br.com.albergue.controller.dto.TokenDto;
import br.com.albergue.controller.form.LoginForm;
import br.com.albergue.controller.form.ReservationForm;
import br.com.albergue.domain.CashPayment;
import br.com.albergue.domain.CheckPayment;
import br.com.albergue.domain.CreditCardPayment;
import br.com.albergue.repository.PaymentsRepository;
import br.com.albergue.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class ReservationPostAndDeleteTests {

	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	PaymentsRepository paymentsRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private URI uri;
	private HttpHeaders headers = new HttpHeaders();
	private LoginForm login = new LoginForm();
	private ReservationForm reservationForm = new ReservationForm();
	private CheckPayment checkPayment = new CheckPayment();
	private CashPayment cashPayment = new CashPayment();
	private CreditCardPayment creditCardPayment = new CreditCardPayment();
	
	@Before
	public void init() throws JsonProcessingException, Exception {
		uri = new URI("/api/reservations/");
		
		//setting login variables to autenticate
		login.setEmail("aluno@email.com");
		login.setPassword("123456");

		//posting on /auth to get token
		MvcResult resultAuth = mockMvc
				.perform(post("/auth")
				.content(objectMapper.writeValueAsString(login)).contentType("application/json"))
				.andReturn();	
			
		String contentAsString = resultAuth.getResponse().getContentAsString();

		TokenDto loginObjResponse = objectMapper.readValue(contentAsString, TokenDto.class);
		
		// seting header to put on post and delete request parameters
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + loginObjResponse.getToken());
		
		//setting reservation object
		reservationForm.setCheckinDate(LocalDate.of(2019, 04, 01));
		reservationForm.setCheckoutDate(LocalDate.of(2019, 04, 04));
		reservationForm.setReservationDate(LocalDate.of(2019, 04, 01));
		reservationForm.setCustomer_ID(1L);
		
	}
	
	@Test
	public void shouldAutenticateAndDeleteOneReservationWithId1() throws Exception {
		checkPayment.setAmount(5000);
		checkPayment.setDate(LocalDateTime.of(LocalDate.of(2020, 01, 25), LocalTime.of(21, 30)));
		checkPayment.setBankId("01");
		checkPayment.setBankName("Banco do Brasil");
		checkPayment.setBranchNumber("1234-5");
		
		reservationForm.setPayments(checkPayment);
		
		reservationRepository.save(reservationForm.returnReservation(paymentsRepository));

		mockMvc
			.perform(delete(uri + "1")
			.headers(headers))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();	
	}
	
	@Test
	public void shouldAutenticateAndCreateOneReservationByCheckPaymentAndReturnStatusCreated() throws Exception {
		checkPayment.setAmount(3000);
		checkPayment.setDate(LocalDateTime.of(LocalDate.of(2020, 01, 25), LocalTime.of(21, 31)));
		checkPayment.setBankId("01");
		checkPayment.setBankName("Banco do Brasil");
		checkPayment.setBranchNumber("1234-5");
		
		reservationForm.setPayments(checkPayment);
		
		reservationRepository.save(reservationForm.returnReservation(paymentsRepository));
		
		MvcResult result = 
				mockMvc
					.perform(post(uri)
					.headers(headers)
					.content(objectMapper.writeValueAsString(reservationForm)))
					.andDo(print())
					.andExpect(status().isCreated())
					.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		ReservationDto reservationObjResponse = objectMapper.readValue(contentAsString, ReservationDto.class);

		assertEquals(reservationObjResponse.getCheckinDate(), LocalDate.of(2019, 04, 01));
		assertEquals(reservationObjResponse.getPayments().getAmount(), 3000);
	}
	
	@Test
	public void shouldAutenticateAndCreateOneReservationByCashPaymentAndReturnStatusCreated() throws Exception {
		cashPayment.setAmount(4000);
		cashPayment.setAmountTendered(5000);
		cashPayment.setDate(LocalDateTime.of(LocalDate.of(2020,01,25), LocalTime.of(21, 32)));
		
		reservationForm.setPayments(cashPayment);
		
		reservationRepository.save(reservationForm.returnReservation(paymentsRepository));
		
		MvcResult result = 
				mockMvc
					.perform(post(uri)
					.headers(headers)
					.content(objectMapper.writeValueAsString(reservationForm)))
					.andDo(print())
					.andExpect(status().isCreated())
					.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		ReservationDto reservationObjResponse = objectMapper.readValue(contentAsString, ReservationDto.class);

		assertEquals(reservationObjResponse.getCheckinDate(), LocalDate.of(2019, 04, 01));
		assertEquals(reservationObjResponse.getPayments().getAmount(), 4000);
	}
	
	@Test
	public void shouldAutenticateAndCreateOneReservationByCreditCardPaymentAndReturnStatusCreated() throws Exception {
		creditCardPayment.setAmount(5000);
		creditCardPayment.setDate(LocalDateTime.of(LocalDate.of(2020,01,25), LocalTime.of(21, 33)));
		creditCardPayment.setIssuer("VISA");
		creditCardPayment.setNameOnCard("1234 5678 9101 1121");
		creditCardPayment.setNumber("1234");
		creditCardPayment.setExpirationDate(LocalDate.of(2020, 05, 01));
		creditCardPayment.setSecurityCode("123");
		
		reservationForm.setPayments(creditCardPayment);
		
		reservationRepository.save(reservationForm.returnReservation(paymentsRepository));
		
		MvcResult result = 
				mockMvc
				.perform(post(uri)
						.headers(headers)
						.content(objectMapper.writeValueAsString(reservationForm)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		
		ReservationDto reservationObjResponse = objectMapper.readValue(contentAsString, ReservationDto.class);
		
		assertEquals(reservationObjResponse.getCheckinDate(), LocalDate.of(2019, 04, 01));
		assertEquals(reservationObjResponse.getPayments().getAmount(), 5000);
	}
}