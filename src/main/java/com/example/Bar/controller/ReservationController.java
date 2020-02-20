package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.ReservationService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
@Api(value = "Reservation system")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View reservations", notes = "Use this method, if you want to view reservations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get reservations"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<ReservationDTO> getReservation(){
        return reservationService.getReservations();
    }

    @GetMapping("/free/{hours}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View free tables", notes = "Use this method, if you want to view free tables")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get free tables"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public FreeTablesDTO getFreeTables(@PathVariable("hours") final Integer hours) throws NoSuchElementException {
        return reservationService.getFreeTables(hours);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Reserve table", notes = "Use this method to reserve table")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Result of reservation"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public TextResponse reserveTable(@ApiParam(value = "Reservation data")
            @Valid @RequestBody final ReservationRequestDTO reserve) throws NoSuchElementException {
        return reservationService.reserveTable(reserve);
    }

}
