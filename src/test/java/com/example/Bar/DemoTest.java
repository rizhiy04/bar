package com.example.Bar;

import com.example.Bar.dto.authentication.SignInResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class DemoTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDemo() throws Exception{
        //as client
        userSignUp();
        final String clientToken = userSignIn("denis@gmail.com");
        checkClientUserCard(clientToken);
        getMenu();
        getPizzasFromMenu();
        getEvents();
        reserveTable();

        //as waiter
        final String waiterToken = userSignIn("waiter@gmail.com");
        getReservations(waiterToken);
        getFreeTables(waiterToken);
        makeOrder(waiterToken);
        getFreeTablesAfterNewOrder(waiterToken);
        closeOrder(waiterToken);
        getClientCardInfoAfterOrderClosed(clientToken);

        //as admin
        final String adminToken = userSignIn("admin@gmail.com");
        getInventories(adminToken);
        changeInventoryCount(adminToken);
        addInventory(adminToken);
        deleteInventory(adminToken);
        getChangedInventories(adminToken);

        addEvent(adminToken);
        deleteEvent(adminToken);
        getChangedEvents();

        addMenuItem(adminToken);
        deleteMenuItem(adminToken);
        getChangedMenu();

        getOrders(adminToken);
        getRevenue(adminToken);
    }

    private void userSignUp() throws Exception{
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"denis@gmail.com\",\n" +
                        "  \"password\" : \"qwerty\",\n" +
                        "  \"name\" : \"Денис\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    private String userSignIn(final String email) throws Exception{
        final String response = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \""+ email +"\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return "Bearer " + objectMapper.readValue(response, SignInResponse.class).getToken();
    }

    private void checkClientUserCard(String token) throws Exception{
        mockMvc.perform(get("/discount-cards").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"email\" : \"denis@gmail.com\",\n" +
                        "  \"discount\" : 0.0,\n" +
                        "  \"allSpentMoney\" : 0.0\n" +
                        "}"));
    }

    private void getMenu() throws Exception{
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 2,\n" +
                        "  \"name\" : \"Kozel\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 3,\n" +
                        "  \"name\" : \"Heineken\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"name\" : \"Виски Jim Beam\",\n" +
                        "  \"category\" : \"whisky\",\n" +
                        "  \"description\" : \"Американский бурбон, номер 1 по продажам в мире. обладает янтарным цветом и приятным ароматом с ванильными и цветочными оттенками.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 5,\n" +
                        "  \"name\" : \"Gold Rum\",\n" +
                        "  \"category\" : \"rum\",\n" +
                        "  \"description\" : \"Классический пряный ром, идеальная основа для коктейлей\",\n" +
                        "  \"price\" : 4.4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 6,\n" +
                        "  \"name\" : \"Silver Tequila\",\n" +
                        "  \"category\" : \"tequila\",\n" +
                        "  \"description\" : \"Традиционный мексиканский крепкоалкогольный напиток, приготовляемый из голубой агавы.\",\n" +
                        "  \"price\" : 5.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 7,\n" +
                        "  \"name\" : \"Tequila Sauza Silver\",\n" +
                        "  \"category\" : \"tequila\",\n" +
                        "  \"description\" : \"Традиционный мексиканский алкогольный напиток чистого и прозрачного цвета с нотками голубой агавы, дуба, перца и грейпфрута и терпким миндальным послевкусием.\",\n" +
                        "  \"price\" : 7.0\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 8,\n" +
                        "  \"name\" : \"London Dry Gin\",\n" +
                        "  \"category\" : \"gin\",\n" +
                        "  \"description\" : \"Традиционный джин, отличная основа для коктейлей.\",\n" +
                        "  \"price\" : 4.4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 9,\n" +
                        "  \"name\" : \"Самбука\",\n" +
                        "  \"category\" : \"sambuca\",\n" +
                        "  \"description\" : \"Популярный итальянский ликер на основе вытяжки из звездчатого аниса и бузины.\",\n" +
                        "  \"price\" : 5.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 10,\n" +
                        "  \"name\" : \"Ликер Blue Curacao\",\n" +
                        "  \"category\" : \"liquor\",\n" +
                        "  \"description\" : \"Ликер голубого цвета на основе высушенных корок апельсинов особого сорта.\",\n" +
                        "  \"price\" : 6.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 11,\n" +
                        "  \"name\" : \"Ликер Егермайстер\",\n" +
                        "  \"category\" : \"liquor\",\n" +
                        "  \"description\" : \"Известный немецкий травяной ликер. В его состав входит 56 натуральных компонентов, среди которых разнообразные травы, пряности, фрукты.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Дисконтик\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Трехслойный коктейль с виски, вишневым сиропом и шампанским. Быстро и эффективно.\",\n" +
                        "  \"price\" : 2.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 13,\n" +
                        "  \"name\" : \"Отвертка шот\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Двухслойный коктейль на основе водки и апельсинного сока, для тех, кто не привык выдумывать сложных схем.\",\n" +
                        "  \"price\" : 2.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 14,\n" +
                        "  \"name\" : \"Лютый егерь\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Для любителей легендарного напитка Егермейстер с виски и вишневым послевкусием\",\n" +
                        "  \"price\" : 4.8\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 15,\n" +
                        "  \"name\" : \"Рыжая собака\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Острый двухслойный коктейль из анисового ликера Самбука и серебряной текилы. Остроту коктейлю придает дэш соуса табаско.\",\n" +
                        "  \"price\" : 5.1\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 16,\n" +
                        "  \"name\" : \"Опухоль мозга\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Устрашающе красивый коктейль, приготовленный из сухого вермута, сливочного ликера, традиционной водки и сиропа гренадин.\",\n" +
                        "  \"price\" : 5.1\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 17,\n" +
                        "  \"name\" : \"Coca-Cola\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 18,\n" +
                        "  \"name\" : \"Sprite\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 19,\n" +
                        "  \"name\" : \"Schweppes\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 20,\n" +
                        "  \"name\" : \"Сок апельсиновый\",\n" +
                        "  \"category\" : \"juice\",\n" +
                        "  \"description\" : \"0.2л\",\n" +
                        "  \"price\" : 1.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 21,\n" +
                        "  \"name\" : \"Сок ананасовый\",\n" +
                        "  \"category\" : \"juice\",\n" +
                        "  \"description\" : \"0.2л\",\n" +
                        "  \"price\" : 1.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 22,\n" +
                        "  \"name\" : \"Луковые кольца\",\n" +
                        "  \"category\" : \"snacks\",\n" +
                        "  \"description\" : \"Золотистые луковые колечки.\",\n" +
                        "  \"price\" : 2.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 23,\n" +
                        "  \"name\" : \"Гренки с чесноком\",\n" +
                        "  \"category\" : \"snacks\",\n" +
                        "  \"description\" : \"Обжаренные кусочки ржаного хлеба с чесноком\",\n" +
                        "  \"price\" : 2.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 24,\n" +
                        "  \"name\" : \"Пицца Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 25,\n" +
                        "  \"name\" : \"Пицца Маргарита\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 26,\n" +
                        "  \"name\" : \"Пицца гавайская\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 27,\n" +
                        "  \"name\" : \"Пицца с курицой и грибами\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.9\n" +
                        "}\n" +
                        "]"));
    }

    private void getPizzasFromMenu() throws Exception{
        mockMvc.perform(get("/menu/pizza"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 24,\n" +
                        "  \"name\" : \"Пицца Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 25,\n" +
                        "  \"name\" : \"Пицца Маргарита\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 26,\n" +
                        "  \"name\" : \"Пицца гавайская\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 27,\n" +
                        "  \"name\" : \"Пицца с курицой и грибами\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.9\n" +
                        "}\n" +
                        "]"));
    }

    private void getEvents() throws Exception{
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 3,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"28-03-2020 20:00\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"eventName\" : \"Концерт какой-то группы\",\n" +
                        "  \"description\" : \"Живая музыка, ламповая атмосфера\",\n" +
                        "  \"date\" : \"03-04-2020 20:00\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 5,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"06-04-2020 20:00\"\n" +
                        "}\n" +
                        "]"));
    }

    private void reserveTable() throws Exception{
        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"time\" : \"05-03-2020 19:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"№1\"\n" +
                        "}"));
    }

    private void getReservations(final String token) throws Exception{
        mockMvc.perform(get("/reservations").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"clientName\" : \"Денис\",\n" +
                        "  \"reserveTime\" : \"05-03-2020 19:00\",\n" +
                        "  \"tableNumber\" : 1\n" +
                        "}\n" +
                        "]"));
    }

    private void getFreeTables(final String token) throws Exception{
        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]\n" +
                        "}\n"));
    }

    private void makeOrder(final String token) throws Exception{
        mockMvc.perform(post("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"order\" : [\n" +
                        "{\n"+
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    private void getFreeTablesAfterNewOrder(final String token) throws Exception{
        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]\n" +
                        "}\n"));
    }

    private void closeOrder(final String token) throws Exception{
        mockMvc.perform(patch("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 4\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"money\" : \"25.00\",\n" +
                        "  \"currency\" : \"BYN\"\n" +
                        "}"));
    }

    private void getClientCardInfoAfterOrderClosed(final String token) throws Exception{
        mockMvc.perform(get("/discount-cards").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"email\" : \"denis@gmail.com\",\n" +
                        "  \"discount\" : 0.0,\n" +
                        "  \"allSpentMoney\" : 25.00\n" +
                        "}"));
    }

    private void getInventories(final String token) throws Exception{
        mockMvc.perform(get("/inventories").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Рюмка 50 мл\",\n" +
                        "  \"category\" : \"glass\",\n" +
                        "  \"count\" : 45\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 2,\n" +
                        "  \"name\" : \"Стол\",\n" +
                        "  \"category\" : \"table\",\n" +
                        "  \"count\" : 15\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 3,\n" +
                        "  \"name\" : \"Бокал 500мл\",\n" +
                        "  \"category\" : \"glass\",\n" +
                        "  \"count\" : 20\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"name\" : \"Баррель Zatecky Gus\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 5,\n" +
                        "  \"name\" : \"Баррель Kozel\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 6,\n" +
                        "  \"name\" : \"Баррель Heineken\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 7,\n" +
                        "  \"name\" : \"Виски Jim Beam 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 8,\n" +
                        "  \"name\" : \"Gold Rum 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 9,\n" +
                        "  \"name\" : \"Silver Tequila 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 10,\n" +
                        "  \"name\" : \"Tequila Sauza Silver 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 11,\n" +
                        "  \"name\" : \"London Dry Gin 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Самбука 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 13,\n" +
                        "  \"name\" : \"Ликер Blue Curacao 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 14,\n" +
                        "  \"name\" : \"Ликер Егермайстер 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 15,\n" +
                        "  \"name\" : \"Томаты 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 16,\n" +
                        "  \"name\" : \"Сыр моцарелла 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 17,\n" +
                        "  \"name\" : \"Колбаса Пепперони\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 18,\n" +
                        "  \"name\" : \"Куриное филе 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 10\n" +
                        "}\n" +
                        "]"));
    }

    private void changeInventoryCount(final String token) throws Exception{
        mockMvc.perform(patch("/inventories").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 50\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    private void addInventory(final String token) throws Exception{
        mockMvc.perform(post("/inventories").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бокал 500 мл\",\n" +
                        "  \"category\" : \"wineglass\",\n" +
                        "  \"count\" : 30\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    private void deleteInventory(final String token) throws Exception{
        mockMvc.perform(delete("/inventories/3").header("Authorization", token))
                .andExpect(status().isOk());
    }

    private void getChangedInventories(final String token) throws Exception{
        mockMvc.perform(get("/inventories").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Рюмка 50 мл\",\n" +
                        "  \"category\" : \"glass\",\n" +
                        "  \"count\" : 50\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 2,\n" +
                        "  \"name\" : \"Стол\",\n" +
                        "  \"category\" : \"table\",\n" +
                        "  \"count\" : 15\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"name\" : \"Баррель Zatecky Gus\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 5,\n" +
                        "  \"name\" : \"Баррель Kozel\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 6,\n" +
                        "  \"name\" : \"Баррель Heineken\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 7,\n" +
                        "  \"name\" : \"Виски Jim Beam 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 8,\n" +
                        "  \"name\" : \"Gold Rum 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 9,\n" +
                        "  \"name\" : \"Silver Tequila 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 10,\n" +
                        "  \"name\" : \"Tequila Sauza Silver 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 11,\n" +
                        "  \"name\" : \"London Dry Gin 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Самбука 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 13,\n" +
                        "  \"name\" : \"Ликер Blue Curacao 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 14,\n" +
                        "  \"name\" : \"Ликер Егермайстер 0,7л\",\n" +
                        "  \"category\" : \"alcohol\",\n" +
                        "  \"count\" : 6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 15,\n" +
                        "  \"name\" : \"Томаты 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 16,\n" +
                        "  \"name\" : \"Сыр моцарелла 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 17,\n" +
                        "  \"name\" : \"Колбаса Пепперони\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 18,\n" +
                        "  \"name\" : \"Куриное филе 1кг\",\n" +
                        "  \"category\" : \"food\",\n" +
                        "  \"count\" : 10\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 19,\n" +
                        "  \"name\" : \"Бокал 500 мл\",\n" +
                        "  \"category\" : \"wineglass\",\n" +
                        "  \"count\" : 30\n" +
                        "}\n" +
                        "]"));
    }

    private void addEvent(final String token) throws Exception{
        mockMvc.perform(post("/events").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-05-2020 20:00\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    private void deleteEvent(final String token) throws Exception{
        mockMvc.perform(delete("/events/5").header("Authorization", token))
                .andExpect(status().isOk());
    }

    private void getChangedEvents() throws Exception{
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 3,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"28-03-2020 20:00\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"eventName\" : \"Концерт какой-то группы\",\n" +
                        "  \"description\" : \"Живая музыка, ламповая атмосфера\",\n" +
                        "  \"date\" : \"03-04-2020 20:00\"\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 6,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-05-2020 20:00\"\n" +
                        "}\n" +
                        "]"));
    }

    private void addMenuItem(final String token) throws Exception{
        mockMvc.perform(post("/menu").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бургер\",\n" +
                        "  \"category\" : \"burger\",\n" +
                        "  \"description\" : \"Большой и вкусный\",\n" +
                        "  \"price\" : 5\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    private void deleteMenuItem(final String token) throws Exception{
        mockMvc.perform(delete("/menu/2").header("Authorization", token))
                .andExpect(status().isOk());
    }

    private void getChangedMenu() throws Exception{
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 3,\n" +
                        "  \"name\" : \"Heineken\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 4,\n" +
                        "  \"name\" : \"Виски Jim Beam\",\n" +
                        "  \"category\" : \"whisky\",\n" +
                        "  \"description\" : \"Американский бурбон, номер 1 по продажам в мире. обладает янтарным цветом и приятным ароматом с ванильными и цветочными оттенками.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 5,\n" +
                        "  \"name\" : \"Gold Rum\",\n" +
                        "  \"category\" : \"rum\",\n" +
                        "  \"description\" : \"Классический пряный ром, идеальная основа для коктейлей\",\n" +
                        "  \"price\" : 4.4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 6,\n" +
                        "  \"name\" : \"Silver Tequila\",\n" +
                        "  \"category\" : \"tequila\",\n" +
                        "  \"description\" : \"Традиционный мексиканский крепкоалкогольный напиток, приготовляемый из голубой агавы.\",\n" +
                        "  \"price\" : 5.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 7,\n" +
                        "  \"name\" : \"Tequila Sauza Silver\",\n" +
                        "  \"category\" : \"tequila\",\n" +
                        "  \"description\" : \"Традиционный мексиканский алкогольный напиток чистого и прозрачного цвета с нотками голубой агавы, дуба, перца и грейпфрута и терпким миндальным послевкусием.\",\n" +
                        "  \"price\" : 7.0\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 8,\n" +
                        "  \"name\" : \"London Dry Gin\",\n" +
                        "  \"category\" : \"gin\",\n" +
                        "  \"description\" : \"Традиционный джин, отличная основа для коктейлей.\",\n" +
                        "  \"price\" : 4.4\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 9,\n" +
                        "  \"name\" : \"Самбука\",\n" +
                        "  \"category\" : \"sambuca\",\n" +
                        "  \"description\" : \"Популярный итальянский ликер на основе вытяжки из звездчатого аниса и бузины.\",\n" +
                        "  \"price\" : 5.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 10,\n" +
                        "  \"name\" : \"Ликер Blue Curacao\",\n" +
                        "  \"category\" : \"liquor\",\n" +
                        "  \"description\" : \"Ликер голубого цвета на основе высушенных корок апельсинов особого сорта.\",\n" +
                        "  \"price\" : 6.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 11,\n" +
                        "  \"name\" : \"Ликер Егермайстер\",\n" +
                        "  \"category\" : \"liquor\",\n" +
                        "  \"description\" : \"Известный немецкий травяной ликер. В его состав входит 56 натуральных компонентов, среди которых разнообразные травы, пряности, фрукты.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Дисконтик\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Трехслойный коктейль с виски, вишневым сиропом и шампанским. Быстро и эффективно.\",\n" +
                        "  \"price\" : 2.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 13,\n" +
                        "  \"name\" : \"Отвертка шот\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Двухслойный коктейль на основе водки и апельсинного сока, для тех, кто не привык выдумывать сложных схем.\",\n" +
                        "  \"price\" : 2.6\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 14,\n" +
                        "  \"name\" : \"Лютый егерь\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Для любителей легендарного напитка Егермейстер с виски и вишневым послевкусием\",\n" +
                        "  \"price\" : 4.8\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 15,\n" +
                        "  \"name\" : \"Рыжая собака\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Острый двухслойный коктейль из анисового ликера Самбука и серебряной текилы. Остроту коктейлю придает дэш соуса табаско.\",\n" +
                        "  \"price\" : 5.1\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 16,\n" +
                        "  \"name\" : \"Опухоль мозга\",\n" +
                        "  \"category\" : \"shots\",\n" +
                        "  \"description\" : \"Устрашающе красивый коктейль, приготовленный из сухого вермута, сливочного ликера, традиционной водки и сиропа гренадин.\",\n" +
                        "  \"price\" : 5.1\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 17,\n" +
                        "  \"name\" : \"Coca-Cola\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 18,\n" +
                        "  \"name\" : \"Sprite\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 19,\n" +
                        "  \"name\" : \"Schweppes\",\n" +
                        "  \"category\" : \"lemonade\",\n" +
                        "  \"description\" : \"розлив, 0.2л\",\n" +
                        "  \"price\" : 1.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 20,\n" +
                        "  \"name\" : \"Сок апельсиновый\",\n" +
                        "  \"category\" : \"juice\",\n" +
                        "  \"description\" : \"0.2л\",\n" +
                        "  \"price\" : 1.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 21,\n" +
                        "  \"name\" : \"Сок ананасовый\",\n" +
                        "  \"category\" : \"juice\",\n" +
                        "  \"description\" : \"0.2л\",\n" +
                        "  \"price\" : 1.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 22,\n" +
                        "  \"name\" : \"Луковые кольца\",\n" +
                        "  \"category\" : \"snacks\",\n" +
                        "  \"description\" : \"Золотистые луковые колечки.\",\n" +
                        "  \"price\" : 2.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 23,\n" +
                        "  \"name\" : \"Гренки с чесноком\",\n" +
                        "  \"category\" : \"snacks\",\n" +
                        "  \"description\" : \"Обжаренные кусочки ржаного хлеба с чесноком\",\n" +
                        "  \"price\" : 2.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 24,\n" +
                        "  \"name\" : \"Пицца Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 25,\n" +
                        "  \"name\" : \"Пицца Маргарита\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.\",\n" +
                        "  \"price\" : 7.3\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 26,\n" +
                        "  \"name\" : \"Пицца гавайская\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 27,\n" +
                        "  \"name\" : \"Пицца с курицой и грибами\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.\",\n" +
                        "  \"price\" : 9.9\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 28,\n" +
                        "  \"name\" : \"Бургер\",\n" +
                        "  \"category\" : \"burger\",\n" +
                        "  \"description\" : \"Большой и вкусный\",\n" +
                        "  \"price\" : 5.0\n" +
                        "}\n" +
                        "]"));
    }

    private void getOrders(final String token) throws Exception{
        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"tableNumber\" : 2,\n" +
//                        "  \"timeOpen\" : \"14-03-2020 20:00\",\n" +
//                        "  \"timeClose\" : null,\n" +
                        "  \"order\" : \n" +
                        "[\n"+
                        "{\n" +
                        "  \"menuItem\" : \n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]"));
    }

    private void getRevenue(final String token) throws Exception{
        mockMvc.perform(get("/orders/revenue").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"date\" : \"14-03-2019 00:00\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"money\" : \"25.00\",\n" +
                        "  \"currency\" : \"BYN\"\n" +
                        "}"));
    }
}
