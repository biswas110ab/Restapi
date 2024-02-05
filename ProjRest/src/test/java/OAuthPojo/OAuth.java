package OAuthPojo;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class OAuth {
	@Test
	public void Serialization() {
		Seriali sr = new Seriali();
		sr.setAccuracy(50);
		sr.setAddress("29, side layout, cohen 09");
		sr.setLanguage("French-IN");
		sr.setWebsite("http://google.com");
		sr.setPhone_number("(+91) 983 893 3937");
		sr.setName("Frontline house");
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").addHeader("Content-Type", "application/json").build();
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		List<String> se = new ArrayList<String>();
		se.add("shoe park");
		se.add("shop");
		sr.setTypes(se);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		sr.setLocation(l);
		RequestSpecification rspon = given().log().all().spec(req).body(sr);

		String respons = rspon.when().post("/maps/api/place/add/json").then().log().all()
				.spec(resSpec).extract().body().asString();
		System.out.println(respons);
	}

}
