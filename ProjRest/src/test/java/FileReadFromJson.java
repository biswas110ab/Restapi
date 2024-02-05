
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.Payload;
import files.Resuable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
public class FileReadFromJson {

	@Test
	public void ReadJsonFile() throws IOException
	{
		
		// to convert json file to string use this inside body https://www.javatpoint.com/convert-json-file-to-string-in-java 
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("E:\\EcLipseProjects\\ProjRest\\src\\test\\resources\\AddBook.json"))))
				.when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Response =======");
		System.out.println(response);
		JsonPath je=Resuable.JsonReusable(response);
		String value = je.get("ID");
		System.out.println(value);

	}
}
