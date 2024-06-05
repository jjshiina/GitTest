import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.webAutomation;
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
		
		//GET Method Applying the Concept of POJO Class(Deserialization)
		GetCourse gc = given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i = 0; i<apiCourses.size(); i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getCourseTitle());
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Get the course names of WebAutomation
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		ArrayList<String> a= new ArrayList<String>();
								
		List<webAutomation> w = gc.getCourses().getWebAutomation();
		
		for(int j = 0;j<w.size();j++)
		{
			a.add(w.get(j).getCourseTitle());
		}
				
		List<String> expectedList=	Arrays.asList(courseTitles);
				
		Assert.assertTrue(a.equals(expectedList));
				
		
	}

}
