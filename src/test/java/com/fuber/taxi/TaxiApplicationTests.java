package com.fuber.taxi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TaxiApplicationTests {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void shouldRegisterTaxi() throws Exception {
		this.mockMvc.perform(post("/registertaxi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + 
						"	\"vehicleModel\": \"Maruti\",\n" + 
						"	\"vehicleNumber\": \"KA01\",\n" + 
						"	\"driverName\": \"abc\",\n" + 
						"	\"taxiType\": \"pink\"\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().is(202))
		.andExpect(jsonPath("$.statusMessage").value("Taxi Registered Successfully"))
		;
	}
	
	@Test
	public void shouldRegisterTaxiDuplicateVehicleModelEntry() throws Exception {
		this.mockMvc.perform(post("/registertaxi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + 
						"	\"vehicleModel\": \"Maruti\",\n" + 
						"	\"vehicleNumber\": \"KA01\",\n" + 
						"	\"driverName\": \"abc\",\n" + 
						"	\"taxiType\": \"pink\"\n" + 
						"}")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().is(502))
		.andExpect(jsonPath("$.statusMessage").value("Error occured while trying to register taxi!"))
		;
	}

}
