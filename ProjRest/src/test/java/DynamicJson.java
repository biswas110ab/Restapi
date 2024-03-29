import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.Resuable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class DynamicJson {
	@Test(dataProvider = "BooksData")
	public void addBook(String isle,String aisele)
	
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
		.body(Payload.AddBook(isle,aisele))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Response =======");
		System.out.println(response);
		JsonPath je=Resuable.JsonReusable(response);
		String value = je.get("ID");
		System.out.println(value);
		
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"oname","1234"},{"eerew","3432"},{"dses","34532"}};
	}

}
