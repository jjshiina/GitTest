import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Pojo.Location;
import Pojo.addPlace;
import io.restassured.RestAssured;

public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		
		String response = given().log().all()
			.queryParam("key", "qaclick123")
			.body(p)
			.when().post("maps/api/place/add/json")
			.then().log().all().assertThat().statusCode(200)
			.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);

	}

}
