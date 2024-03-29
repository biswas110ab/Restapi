package JiraTestCase;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

public class JiraTest {

	@Test
	public void Jiracode() {
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://localhost:8080";
		//Login session
		//we will use session filter to store session and in order 
		//to use it we will pass filter and inside that pass session before when
		//relaxhttpsvalidation for http client to validate
		SessionFilter session=new SessionFilter();
		String sessionresponse=given().log().all().relaxedHTTPSValidation()
				.header("Content-Type", "application/json").body("{ \"username\": \"dimbak\", \"password\": \"dipanker@123\" }")
				.filter(session).when().post("/rest/auth/1/session").then().log().all().extract().body().asString();
		String mess="Value added from jira project";
		//to add comments in jira
		String addresponse=given().log().all().header("Content-Type", "application/json").pathParam("Key", "10100")
				.body("{\r\n"
						+ "    \"body\": \""+mess+"\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}").filter(session).when().post("/rest/api/2/issue/{Key}/comment")
				.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath addresp=new JsonPath(addresponse);
		int respid=addresp.getInt("id");
		//add attachment using multipart
		given().log().all().filter(session).header("X-Atlassian-Token","no-check").pathParam("Key", "10100").header("Content-Type","multipart/form-data")
		.multiPart("file",new File("E:\\EcLipseProjects\\ProjRest\\src\\test\\resources\\Sample.txt"))
		.when().post("/rest/api/2/issue/{Key}/attachments").then().log().all()
		.assertThat().statusCode(200);

		//Get issue
		String getreposnse=given().log().all()
				.filter(session).queryParam("fields", "comment")
				.pathParam("Key", "10100").when().get("/rest/api/2/issue/{Key}")
				.then().log().all().extract().response().asString();
		System.out.println(getreposnse);
		JsonPath respJs=new JsonPath(getreposnse);
		int commentCount=respJs.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentCount;i++)
		{
			int newcommentid=respJs.getInt("fields.comment.comments["+i+"].id");
			if(newcommentid==respid)
			{
				String strmessage = respJs.get("fields.comment.comments["+i+"].body").toString();
				Assert.assertEquals(mess, strmessage);
				break;
			}
		}
	}

}
