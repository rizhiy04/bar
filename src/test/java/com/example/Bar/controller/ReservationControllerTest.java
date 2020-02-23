package com.example.Bar.controller;

import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.repository.ReservationRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationControllerTest extends AbstractControllerTest{

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testReserveTableIsCreated() throws Exception{
        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"time\" : \"04-03-2020 19:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Ваш столик №1\"\n" +
                        "}"));

        final List<ReservationEntity> testReservationEntity = reservationRepository.findAll();
        reservationRepository.delete(testReservationEntity.get(testReservationEntity.size() - 1));
    }

    @Test
    public void testGetReservationIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        reservationRepository.deleteAll();
        final ReservationEntity testReservationEntity = getReservationEntity();
        reservationRepository.save(testReservationEntity);

        mockMvc.perform(get("/reservations").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testReservationEntity.getId() +",\n" +
                        "  \"clientName\" : \"Денис\",\n" +
                        "  \"reserveTime\" : \"04-03-2020 19:00\",\n" +
                        "  \"tableNumber\" : 2\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetReservationAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/reservations").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

//    @Test
//    public void testGetFreeTablesIsOk() throws Exception{
//        final String token = signIn(Roles.WAITER);
//
//        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\n" +
//                        "  \"tableNumbers\" : [1, 3]\n" +
//                        "}\n"));
//    }

    @Test
    public void testGetFreeTablesAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    private ReservationEntity getReservationEntity(){
        final ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setName("Денис");
        reservationEntity.setTableNumber(2);
        reservationEntity.setTime(LocalDateTime.of(2020,3,4,19,0));

        return reservationEntity;
    }

}