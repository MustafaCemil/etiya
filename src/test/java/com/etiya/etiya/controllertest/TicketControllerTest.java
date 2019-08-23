package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Ticket;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TicketControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public TicketControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Ticket> response = restTemplate.getForEntity("http://localhost:8080/bilet/1", Ticket.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getPnr(), Matchers.equalTo("PNRmk5"));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/bilet", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> pnrName = body.stream().map(e->e.get("pnrName")).collect(Collectors.toList());

        MatcherAssert.assertThat(pnrName, Matchers.containsInAnyOrder("PNRmk5", "PNRtt9"));
    }

    @Test
    public void testEkleme() {
        Ticket ticket = new Ticket();
        ticket.setPnr("PNRmk5");

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/bilet/ekle", ticket);

        Ticket ticket1 = restTemplate.getForObject(location, Ticket.class);

        MatcherAssert.assertThat(ticket1.getPnr(), Matchers.equalTo(ticket.getPnr()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Ticket ticket = restTemplate.getForObject("http://localhost:8080/bilet/guncelle/1", Ticket.class);

        MatcherAssert.assertThat(ticket.getPnr(), Matchers.equalTo("PNRmk5"));

        ticket.setPnr("PNRmk7");
        restTemplate.put("http://localhost:8080/bilet/guncelle/1", ticket);
        ticket = restTemplate.getForObject("http://localhost:8080/bilet/guncelle/1", Ticket.class);

        MatcherAssert.assertThat(ticket.getPnr(), Matchers.equalTo("PNRmk7"));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/bilet/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/bilet/silme/1", Ticket.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
