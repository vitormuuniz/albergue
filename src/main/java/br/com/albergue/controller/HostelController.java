package br.com.albergue.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.albergue.controller.dto.CustomerDto;
import br.com.albergue.controller.dto.ReservationDto;
import br.com.albergue.controller.dto.RoomDto;
import br.com.albergue.controller.form.CustomerForm;
import br.com.albergue.controller.form.ReservationForm;
import br.com.albergue.controller.form.RoomForm;
import br.com.albergue.service.HostelService;

@RestController
@RequestMapping("/api")
public class HostelController {

	@Autowired
	private HostelService service;

	@PostMapping("/customers") 
	public ResponseEntity<CustomerDto> registerCustomer(@RequestBody @Valid CustomerForm form,
			UriComponentsBuilder uriBuilder) {
		
		return this.service.registerCustomer(form, uriBuilder);
	}

	@GetMapping("/customers") 
	public ResponseEntity<List<CustomerDto>> listAllCustomers(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination)
			throws URISyntaxException {
		
		return this.service.listAllCustomers(name, pagination);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDto> listOneCustomer(@PathVariable Long id) {
		
		return this.service.listOneCustomer(id);
	}

	@DeleteMapping("/customers/{id}")
	@Transactional
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		
		return this.service.deleteCustomer(id);
	}

	@PostMapping("/reservations") 
	public ResponseEntity<ReservationDto> registerReservation(@RequestBody @Valid ReservationForm form,
			UriComponentsBuilder uriBuilder) {
		
		return this.service.registerReservation(form, uriBuilder);
	}

	@GetMapping("/reservations") 
	public ResponseEntity<List<ReservationDto>> listAllReservations(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {
		
		return this.service.listAllReservations(name, pagination);
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<ReservationDto> listOneReservation(@PathVariable Long id) {
		
		return this.service.listOneReservation(id);
	}

	@DeleteMapping("/reservations/{id}")
	@Transactional
	public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
		
		return this.service.deleteReservation(id);
	}

	@PostMapping("/rooms") 
	public ResponseEntity<RoomDto> registerRoom(@RequestBody @Valid RoomForm form, UriComponentsBuilder uriBuilder) {
		
		return this.service.registerRoom(form, uriBuilder);
	}

	@GetMapping("/rooms") 
	public ResponseEntity<List<RoomDto>> listAllRooms(@RequestParam(required = false) Integer number,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {

		return this.service.listAllRooms(number, pagination);
	}

	@GetMapping("/rooms/{id}")
	public ResponseEntity<RoomDto> listOneRoom(@PathVariable Long id) {
		
		return this.service.listOneRoom(id);
	}

	@DeleteMapping("/rooms/{id}")
	@Transactional
	public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
		
		return this.service.deleteRoom(id);
	}
}