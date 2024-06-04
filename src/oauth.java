import static io.restassured.RestAssured.*;

import files.ReusableMethod;
import io.restassured.path.json.JsonPath;


public class oauth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//POST Method - Get the access token
		String response = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		JsonPath js = ReusableMethod.rawToJson(response);
		String accessToken = js.getString("access_token");
		
		//GET Method
		String getResponse = given()
		.queryParam("access_token", accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		
		System.out.println(getResponse);
		
	}

}
