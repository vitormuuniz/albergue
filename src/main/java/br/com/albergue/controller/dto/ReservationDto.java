package br.com.albergue.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.albergue.domain.Payments;
import br.com.albergue.domain.Reservation;

public class ReservationDto {

	private Long id;
	private LocalDate reservationDate;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Payments payments;
	
	public ReservationDto() {
	}
	
	public ReservationDto(Reservation reservation) {
		this.id = reservation.getId();
		this.reservationDate = reservation.getReservationDate();
		this.checkinDate =  reservation.getCheckinDate();
		this.checkoutDate = reservation.getCheckoutDate();
		this.payments = reservation.getPayment();
	}

	public Long getId() {
		return id;
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
	
	public static List<ReservationDto> converter(List<Reservation> reservationsList) {
		//fazendo um map de topico para topicoDto
		//TopicoDto::new -> recebe o proprio construtor que recebe um topico como parametro
		//collect() -> transforma essa saida em uma lista
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	
		List<ReservationDto> listCustomersDto = new ArrayList<>();
		for(Reservation r : reservationsList) {
			listCustomersDto.add(new ReservationDto(r));
		}
		return listCustomersDto;
	}
}
