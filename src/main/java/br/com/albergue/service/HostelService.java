package br.com.albergue.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.controller.dto.ReservationDto;
import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.controller.form.CustomerForm;
import br.com.albergue.controller.form.ReservationForm;
import br.com.albergue.controller.form.RoomForm;
import br.com.albergue.domain.Customer;
import br.com.albergue.domain.Reservation;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.AddressRepository;
import br.com.albergue.repository.CustomerRepository;
import br.com.albergue.repository.PaymentsRepository;
import br.com.albergue.repository.ReservationRepository;
import br.com.albergue.repository.RoomRepository;

@Service
public class HostelService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private PaymentsRepository paymentsRepository;
	
	
	public ResponseEntity<CustomerDto> registerCustomer(CustomerForm form, UriComponentsBuilder uriBuilder){
		Customer customer = form.returnCustomer(addressRepository);
		customerRepository.save(customer);

		// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
		// completo)
		// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
		// toUri para transformar na uri completa
		URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(uri).body(new CustomerDto(customer));
	}
	
	public ResponseEntity<List<CustomerDto>> listAllCustomers(String name, Pageable pagination){
		List<CustomerDto> response = new ArrayList<>();
		
		if (name == null)
			response = CustomerDto.converter(customerRepository.findAll());
		else
			response = CustomerDto.converter(customerRepository.findByName(name));

		if (response.isEmpty() || response == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<CustomerDto> listOneCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent())
			return ResponseEntity.ok(new CustomerDto(customer.get()));
		else
			return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> deleteCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			customerRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

	public ResponseEntity<ReservationDto> registerReservation(ReservationForm form, UriComponentsBuilder uriBuilder) {

		Optional<Customer> customerOp = form.returnCustomer(customerRepository);

		if (customerOp.isPresent()) {
			Reservation reservation = form.returnReservation(paymentsRepository);
			reservationRepository.save(reservation);

			Customer customer = customerOp.get();
			customer.addReservation(reservation);
			customerRepository.save(customer);

			// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
			// completo)
			// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
			// toUri para transformar na uri completa
			URI uri = uriBuilder.path("/reservations/{id}").buildAndExpand(customer.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReservationDto(reservation));
		} else
			return ResponseEntity.notFound().build();
	}

	public ResponseEntity<List<ReservationDto>> listAllReservations(String name, Pageable pagination) {

		List<ReservationDto> response = new ArrayList<>();

		if (name == null)
			response = ReservationDto.converter(reservationRepository.findAll());
		else {
			List<Customer> customerList = customerRepository.findByName(name);
			if (!customerList.isEmpty()) {
				List<Reservation> reservations = customerList.get(0).getReservations().stream()
						.collect(Collectors.toList());

				response = ReservationDto.converter(reservations);
			}
		}

		if (response.isEmpty())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(response);
	}

	public ResponseEntity<ReservationDto> listOneReservation(Long id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);

		if (reservation.isPresent())
			return ResponseEntity.ok(new ReservationDto(reservation.get()));
		else
			return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> deleteReservation(Long id) {
		Optional<Reservation> reservation = reservationRepository.findById(id);

		if (reservation.isPresent()) {
			reservationRepository.deleteById(id);

			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

	public ResponseEntity<RoomDto> registerRoom(RoomForm form, UriComponentsBuilder uriBuilder) {
		Room room = form.returnRoom();
		roomRepository.save(room);

		// path indica o caminho do recurso sendo chamado (pra nao passar o caminho
		// completo)
		// buildAndExpend serve para pegar e substituir o id em {id} dinamicamente
		// toUri para transformar na uri completa
		URI uri = uriBuilder.path("/rooms/{id}").buildAndExpand(room.getId()).toUri();
		return ResponseEntity.created(uri).body(new RoomDto(room));
	}


	public ResponseEntity<List<RoomDto>> listAllRooms(Integer number, Pageable pagination) {

		List<RoomDto> response = new ArrayList<>();

		if (!(number instanceof Integer))
			response = RoomDto.converter(roomRepository.findAll());
		else
			response = RoomDto.converter(roomRepository.findByNumber(number));

		if (response.isEmpty() || response == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(response);
	}

	public ResponseEntity<RoomDto> listOneRoom(Long id) {
		Optional<Room> room = roomRepository.findById(id);
		if (room.isPresent())
			return ResponseEntity.ok(new RoomDto(room.get()));
		else
			return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> deleteRoom(Long id) {
		Optional<Room> room = roomRepository.findById(id);
		if (room.isPresent()) {
			roomRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}
}
