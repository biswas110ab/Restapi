import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.Resuable;

public class Crud {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
	     String response=given().log().all().queryParam("key", "qaclick123")
	     .header("Content-Type","application/json")
	     .body(Payload.AddPlace())
	     //resourse should be passed in when 
	     .when().post("maps/api/place/add/json")
	     .then().log().all().statusCode(200).body("scope", equalTo("APP"))
	     .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
	     .extract().response().asString();
		System.out.println("=================");
		System.out.println(response);
		JsonPath js=new JsonPath(response);
		System.out.println("=================");
		System.out.println(js.getString("place_id"));
		String placeidis=js.getString("place_id");
		//update api
		String changecity="Mumbai";
				
		given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeidis+"\",\r\n"
				+ "\"address\":\""+changecity+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json").then().log().all()
		.statusCode(200)
		.body("msg", equalTo("Address successfully updated"));

		//get api
	String Address = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeidis)
		.when().get("maps/api/place/get/json")
		.then().log().all().statusCode(200).body("address", equalTo(changecity))
		.extract().body().asString();
	JsonPath jews = Resuable.JsonReusable(Address);
	Assert.assertEquals(jews.getString("address"), changecity,"Value do not match");
	}

}
