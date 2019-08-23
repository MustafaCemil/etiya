package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Cleander;
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
public class CleanderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public CleanderControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Cleander> response = restTemplate.getForEntity("http://localhost:8080/takvim/1", Cleander.class);
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
        Cleander cleander = new Cleander();
        cleander.setSeatNumber(50);

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/takvim/ekle", cleander);

        Cleander cleander1 = restTemplate.getForObject(location, Cleander.class);

        MatcherAssert.assertThat(cleander1.getSeatNumber(), Matchers.equalTo(cleander.getSeatNumber()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Cleander cleander = restTemplate.getForObject("http://localhost:8080/takvim/guncelle/1", Cleander.class);

        MatcherAssert.assertThat(cleander.getSeatNumber(), Matchers.equalTo(80));

        cleander.setSeatNumber(50);
        restTemplate.put("http://localhost:8080/takvim/guncelle/1", cleander);
        cleander = restTemplate.getForObject("http://localhost:8080/takvim/guncelle/1", Cleander.class);

        MatcherAssert.assertThat(cleander.getSeatNumber(), Matchers.equalTo(50));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/takvim/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/takvim/silme/1", Cleander.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
