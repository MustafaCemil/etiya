package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Airplane;
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
public class AirplaneControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public AirplaneControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Airplane> response = restTemplate.getForEntity("http://localhost:8080/ucak/1", Airplane.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getAirplaneName(), Matchers.equalTo("THY"));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/ucak", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> airplaneName = body.stream().map(e->e.get("airplaneName")).collect(Collectors.toList());

        MatcherAssert.assertThat(airplaneName, Matchers.containsInAnyOrder("THY", "PG"));
    }

    @Test
    public void testEkleme() {
        Airplane airplane = new Airplane();
        airplane.setAirplaneName("THY");
        airplane.setSeatNumber(50);

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/ucak/ekle", airplane);

        Airplane airplane1 = restTemplate.getForObject(location, Airplane.class);

        MatcherAssert.assertThat(airplane1.getAirplaneName(), Matchers.equalTo(airplane.getAirplaneName()));
        MatcherAssert.assertThat(airplane1.getSeatNumber(), Matchers.equalTo(airplane.getSeatNumber()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Airplane airplane = restTemplate.getForObject("http://localhost:8080/ucak/guncelle/1", Airplane.class);

        MatcherAssert.assertThat(airplane.getAirplaneName(), Matchers.equalTo("AnadoluJet"));

        airplane.setAirplaneName("Pegasus");
        restTemplate.put("http://localhost:8080/ucak/guncelle/1", airplane);
        airplane = restTemplate.getForObject("http://localhost:8080/ucak/guncelle/1", Airplane.class);

        MatcherAssert.assertThat(airplane.getAirplaneName(), Matchers.equalTo("Pegasus"));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/ucak/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/ucak/silme/1", Airplane.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }

}
