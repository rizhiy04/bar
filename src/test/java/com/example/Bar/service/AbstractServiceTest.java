package com.example.Bar.service;

import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.ReservationRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class AbstractServiceTest {

    @MockBean
    protected ReservationRepository reservationRepository;
    @MockBean
    protected InventoryRepository inventoryRepository;
}
