package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservation(){
        return reservationService.getReservations();
    }

    @GetMapping("/free/{hours}")
    @ResponseStatus(HttpStatus.OK)
    public FreeTablesDTO getFreeTables(@PathVariable("hours") final Integer hours) throws NoSuchElementException {
        return reservationService.getFreeTables(hours);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse reserveTable(@Valid @RequestBody final ReservationRequestDTO reserve) throws NoSuchElementException {
        return reservationService.reserveTable(reserve);
    }

}
