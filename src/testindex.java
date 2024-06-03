import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethod;
import files.payload;



public class testindex {
	@Test
	public void TestAPI() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//given - all input details 
		//when - Submit the API - resource, http method(post, get, put, delete)
		//then - validate the response
		
		//POST Request
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
			.body(payload.addPlace())
			.when().post("maps/api/place/add/json")
			.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
			.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
			
		System.out.println(response);
		
		//Parsing json Response
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		//PUT Request
		String newAddress = "Tondodo Manila";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
			.body("{\r\n"
					+ "\"place_id\":\"" + placeId + "\",\r\n"
					+ "\"address\":\"" + newAddress + "\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}")
			.when().put("maps/api/place/update/json")
			.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
				
		//GET Request
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
			.queryParam("place_id", placeId)
			.when().get("maps/api/place/get/json")
			.then().assertThat().log().all().statusCode(200).extract().asString();
				
		JsonPath js1 = ReusableMethod.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"asdfasd", "asdfaf"}, {"asefefe", "asdfeeeee"}};
	}

}
