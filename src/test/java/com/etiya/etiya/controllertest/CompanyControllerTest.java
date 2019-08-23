package com.etiya.etiya.controllertest;

import com.etiya.etiya.entity.Company;
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
public class CompanyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public CompanyControllerTest(TestRestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @Before//spring security bilgileri icin
    public void setUp() {
        restTemplate = restTemplate.withBasicAuth("", "");
    }

    @Test
    public void testKayitBul() {
        ResponseEntity<Company> response = restTemplate.getForEntity("http://localhost:8080/sirket/1", Company.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getCompanyName(), Matchers.equalTo("THY"));
    }


    @Test
    public void testListeleme() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/sirket", List.class);
        List<Map<String,String>> body = response.getBody();

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<String> companyName = body.stream().map(e->e.get("companyName")).collect(Collectors.toList());

        MatcherAssert.assertThat(companyName, Matchers.containsInAnyOrder("THY", "PG"));
    }

    @Test
    public void testEkleme() {
        Company company = new Company();
        company.setCompanyName("THY");


        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation("http://localhost:8080/sirket/ekle", company);

        Company company1 = restTemplate.getForObject(location, Company.class);

        MatcherAssert.assertThat(company1.getCompanyName(), Matchers.equalTo(company.getCompanyName()));
    }

    @Test
    public void testGuncelleme() {
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8080/sirket/guncelle/1", Company.class);

        MatcherAssert.assertThat(company.getCompanyName(), Matchers.equalTo("AnadoluJet"));

        company.setCompanyName("Pegasus");
        restTemplate.put("http://localhost:8080/sirket/guncelle/1", company);
        company = restTemplate.getForObject("http://localhost:8080/sirket/guncelle/1", Company.class);

        MatcherAssert.assertThat(company.getCompanyName(), Matchers.equalTo("Pegasus"));
    }

    @Test
    public void testSilme() {
        restTemplate.delete("http://localhost:8080/sirket/silme/1");

        try {
            restTemplate.getForEntity("http://localhost:8080/sirket/silme/1", Company.class);
            Assert.fail("");
        } catch (HttpClientErrorException ex) {
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
