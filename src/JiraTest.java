import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import files.ReusableMethod;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;


public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="http://localhost:8080";
		
		//Login Scenario, Cookie-based authentication
		SessionFilter session=new SessionFilter();
		given().header("Content-Type", "application/json")
				.body("{ \"username\": \"jjshiina17\", \"password\": \"!qazxsw2\" }")
				.log().all().filter(session)
				.when().post("rest/auth/1/session")
				.then().log().all().extract().response().asString();
		//Add Comment
		String expectedMessage="Hello This is a Test123";
		String addComment = given().pathParam("key", "10002").log().all().header("Content-Type", "application/json").body(
				"{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session)
		.when().post("/rest/api/2/issue/{key}/comment").then().log().all()
		.assertThat().statusCode(201).extract().response().asString();
	
		JsonPath js = ReusableMethod.rawToJson(addComment);
		String commentId = js.get("id").toString();
		
		//Add Attachment
		File attachmentFile = new File("C:\\Users\\MY_PC\\eclipse-workspace\\RestAssuredDemo\\jira.txt");
		
		given().header("X-Atlassian-Token", "no-check")
		.filter(session)
		.pathParam("key", "10002")
		.header("Content-Type", "multipart/form-data")
		.multiPart("file", attachmentFile)
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all()
		.assertThat().statusCode(200);

		//Get Issue
		String issueDetails = given()
		.filter(session)
		.pathParam("key", "10002")
		.queryParam("fields", "comment")
		.log().all()
		.when().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();		
		System.out.println(issueDetails);
		
		JsonPath js1 = ReusableMethod.rawToJson(issueDetails);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
		for(int i=0; i < commentsCount; i++) 
		{
			String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)) 
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
		
		
		System.out.println("GITGITGIT");
		
		//asdfsdfddd
		
	}
	

}
