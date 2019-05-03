package br.pucrs.controller.persondata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ApplicationConfig;
import com.example.entities.persondata.Email;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmailControllerTest {

	private final static long INVALID_ID = -1;

	private final static long VALID_ID = 194490L;

	private static final String ENDPOINT = "/person/email/";

	@Autowired
	private TestRestTemplate testRestTemplate;

	private HttpEntity<Email> getRequestEntity(final Email obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Email>(obj, headers);
	}

	private HttpEntity<Email> getRequestEntityOnlyHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Email>(headers);
	}

	@Test
	public void getAll() {
		final Email entity = new Email();
		final ResponseEntity<Email[]> response = testRestTemplate.exchange(ENDPOINT, HttpMethod.GET,
			getRequestEntity(entity), Email[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().length).isGreaterThan(0);
	}

	@Test
	public void getAllFiltedByEmail() {
		final Email entity = new Email();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ENDPOINT).queryParam(
			"email", "@gmail.com");

		final ResponseEntity<Email[]> response = testRestTemplate.exchange(
			builder.build().encode().toUri(), HttpMethod.GET, getRequestEntity(entity),
			Email[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().length).isGreaterThan(0);

		builder = UriComponentsBuilder.fromUriString(ENDPOINT).queryParam("email", "@@@");

		final ResponseEntity<Email[]> response2 = testRestTemplate.exchange(
			builder.build().encode().toUri(), HttpMethod.GET, getRequestEntity(entity),
			Email[].class);

		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response2.getBody().length).isEqualTo(0);
	}

	@Test
	public void getOne() {
		final ResponseEntity<Email> response = testRestTemplate.exchange("/email/{id}",
			HttpMethod.GET, getRequestEntityOnlyHeader(), Email.class, VALID_ID);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOneNotFound() {
		final ResponseEntity<Email> response = testRestTemplate.exchange("/email/{id}",
			HttpMethod.GET, this.getRequestEntityOnlyHeader(), Email.class, INVALID_ID);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
