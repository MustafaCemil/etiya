package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Airport;
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
public class AirportControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    public AirportControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Airport> response = restTemplate.getForEntity("http://localhost:8080/havalimani/1", Airport.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getAirportName(), Matchers.equalTo("Sabiha Gokcen"));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/havalimani", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> airportname = body.stream().map(e->e.get("airportname")).collect(Collectors.toList());

        MatcherAssert.assertThat(airportname, Matchers.containsInAnyOrder("Sabiha Gokcen", "Ataturk Havalimani"));
    }

    @Test
    public void testEkleme() {
        Airport airport = new Airport();
        airport.setAirportName("Sabiha Gokcen");
        airport.setAirportCity("Istanbul");

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/havalimani/ekle", airport);

        Airport airport1 = restTemplate.getForObject(location, Airport.class);

        MatcherAssert.assertThat(airport1.getAirportName(), Matchers.equalTo(airport.getAirportName()));
        MatcherAssert.assertThat(airport1.getAirportCity(), Matchers.equalTo(airport.getAirportCity()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Airport airport = restTemplate.getForObject("http://localhost:8080/havalimani/guncelle/1", Airport.class);

        MatcherAssert.assertThat(airport.getAirportName(), Matchers.equalTo("Esenboga"));

        airport.setAirportName("Sabiha Gokcen");
        restTemplate.put("http://localhost:8080/havalimani/guncelle/1", airport);
        airport = restTemplate.getForObject("http://localhost:8080/havalimani/guncelle/1", Airport.class);

        MatcherAssert.assertThat(airport.getAirportName(), Matchers.equalTo("Sabiha Gokcen"));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/havalimani/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/havalimani/silme/1", Airport.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }

}
