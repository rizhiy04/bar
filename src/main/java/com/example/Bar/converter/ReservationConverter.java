package com.example.Bar.converter;

import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.entity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public ReservationDTO convertToDTO(final ReservationEntity reservationEntity){
        final ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservationEntity.getId());
        reservationDTO.setClientName(reservationEntity.getName());
        reservationDTO.setTableNumber(reservationEntity.getTableNumber());
        reservationDTO.setReserveTime(reservationEntity.getTime());

        return reservationDTO;
    }
}
