package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Customers;
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
public class CustomersControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public CustomersControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Customers> response = restTemplate.getForEntity("http://localhost:8080/musteri/1", Customers.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Cemil"));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/musteri", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> firstName = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstName, Matchers.containsInAnyOrder("Mustafa","Cemil"));
    }

    @Test
    public void testEkleme() {
        Customers customers = new Customers();
        customers.setFirstName("cemil");

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/musteri/ekle", customers);

        Customers customers1 = restTemplate.getForObject(location, Customers.class);

        MatcherAssert.assertThat(customers1.getFirstName(), Matchers.equalTo(customers.getFirstName()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Customers customers = restTemplate.getForObject("http://localhost:8080/musteri/guncelle/1", Customers.class);

        MatcherAssert.assertThat(customers.getFirstName(), Matchers.equalTo("Cemil"));

        customers.setFirstName("Mustafa");
        restTemplate.put("http://localhost:8080/musteri/guncelle/1", customers);
        customers = restTemplate.getForObject("http://localhost:8080/musteri/guncelle/1", Customers.class);

        MatcherAssert.assertThat(customers.getFirstName(), Matchers.equalTo("Mustafa"));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/musteri/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/musteri/silme/1", Customers.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
