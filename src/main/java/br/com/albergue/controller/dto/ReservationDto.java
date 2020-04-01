package br.com.albergue.controller.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import br.com.albergue.domain.Reservation;

public class ReservationDto {

	private LocalDate reservationDate;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public ReservationDto(Reservation reservation) {
		this.reservationDate = reservation.getReservationDate();
		this.checkinDate =  reservation.getCheckinDate();
		this.checkoutDate = reservation.getCheckoutDate();
	}

	
	public ReservationDto(Set<Reservation> reservations) {
		this.reservations = reservations;
	}


	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	
	public static Page<ReservationDto> converter(Page<Reservation> reservation) {
		//fazendo um map de topico para topicoDto
		//TopicoDto::new -> recebe o proprio construtor que recebe um topico como parametro
		//collect() -> transforma essa saida em uma lista
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	
		return reservation.map(ReservationDto::new);
	}
}
