package br.com.albergue.controller.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import br.com.albergue.domain.Payments;
import br.com.albergue.domain.Reservation;

public class ReservationDto {

	private LocalDate reservationDate;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Payments payments;
	
	public ReservationDto(Reservation reservation) {
		this.reservationDate = reservation.getReservationDate();
		this.checkinDate =  reservation.getCheckinDate();
		this.checkoutDate = reservation.getCheckoutDate();
		this.payments = reservation.getPayment();
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
	
	public Payments getPayments() {
		return payments;
	}

	public void setPayments(Payments payments) {
		this.payments = payments;
	}
	
	public static Page<ReservationDto> converter(Page<Reservation> reservation) {
		//fazendo um map de topico para topicoDto
		//TopicoDto::new -> recebe o proprio construtor que recebe um topico como parametro
		//collect() -> transforma essa saida em uma lista
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	
		return reservation.map(ReservationDto::new);
	}
}
