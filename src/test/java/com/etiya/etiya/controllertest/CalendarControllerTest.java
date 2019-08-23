package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Calendar;
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
public class CalendarControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public CalendarControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Calendar> response = restTemplate.getForEntity("http://localhost:8080/takvim/1", Calendar.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getPrice(), Matchers.equalTo(100.0));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/takvim", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> cleander = body.stream().map(e->e.get("price")).collect(Collectors.toList());

        MatcherAssert.assertThat(cleander, Matchers.containsInAnyOrder(100.0, 200.0));
    }

    @Test
    public void testEkleme() {
        Calendar calendar = new Calendar();
        calendar.setSeatNumber(50);

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/takvim/ekle", calendar);

        Calendar calendar1 = restTemplate.getForObject(location, Calendar.class);

        MatcherAssert.assertThat(calendar1.getSeatNumber(), Matchers.equalTo(calendar.getSeatNumber()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Calendar calendar = restTemplate.getForObject("http://localhost:8080/takvim/guncelle/1", Calendar.class);

        MatcherAssert.assertThat(calendar.getSeatNumber(), Matchers.equalTo(80));

        calendar.setSeatNumber(50);
        restTemplate.put("http://localhost:8080/takvim/guncelle/1", calendar);
        calendar = restTemplate.getForObject("http://localhost:8080/takvim/guncelle/1", Calendar.class);

        MatcherAssert.assertThat(calendar.getSeatNumber(), Matchers.equalTo(50));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/takvim/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/takvim/silme/1", Calendar.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
