import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Pojo.Location;
import Pojo.addPlace;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecsBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		addPlace p = new addPlace();
		p.setAccuracy(50);
		p.setName("Kay Aling Cely");
		p.setPhone_number("093312344 imy");
		p.setAddress("dito sa kanto ng Tondo");
		p.setWebsite("www.po**hub.com");
		p.setLanguage("Bisakol");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shab*");
		myList.add("tindahan");
		
		p.setTypes(myList);
		Location l = new Location();
		l.setLat(-30.444);
		l.setLng(30.4444);
		p.setLocation(l);
		
		RequestSpecification req =  new RequestSpecBuilder()
									.setBaseUri("https://rahulshettyacademy.com")
									.addQueryParam("key", "qaclick123")
									.setContentType(ContentType.JSON).build();
		
		ResponseSpecification res = new ResponseSpecBuilder()
									.expectStatusCode(200)
									.expectContentType(ContentType.JSON).build();
		
		Response response = given().spec(req)
			.body(p)
			.when().post("maps/api/place/add/json")
			.then().spec(res).extract().response();
		
		String responseString = response.asString();
		
		System.out.println(responseString);

	}

}
