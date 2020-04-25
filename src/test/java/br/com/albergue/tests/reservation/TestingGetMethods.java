package br.com.albergue.tests.reservation;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.albergue.domain.CashPayment;
import br.com.albergue.domain.Payments;
import br.com.albergue.domain.Reservation;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.PaymentsRepository;
import br.com.albergue.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingGetMethods {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private PaymentsRepository paymentsRepository;

	private URI uri;
	private Reservation reservation = new Reservation();
	private CashPayment cash = new CashPayment();
	
	@Before
	public void init() throws URISyntaxException {
		uri = new URI("/api/reservations");
		
		cash.setAmountTendered(5000.0);
		cash.setAmount(5000.0);
		cash.setDate(LocalDateTime.now());
		paymentsRepository.save(cash);
		
		reservation.setCheckinDate(LocalDate.of(2012, 12, 12));
		reservation.setCheckoutDate(LocalDate.of(2012, 12, 17));
		reservation.setReservationDate(LocalDate.now());
		reservation.setNumberOfGuests(2);
		reservation.setPayment(cash);
		
		reservationRepository.save(reservation);
	}
	
	@Test
	public void testListAllReservationsMethodWithoutParam() throws URISyntaxException {

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		System.out.println(result.getBody());
		
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("\"amount\":5000"));

	}
}