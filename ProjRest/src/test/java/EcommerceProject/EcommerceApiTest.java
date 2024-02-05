package EcommerceProject;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class EcommerceApiTest {

	@Test
	public void commerce() {
		// TODO Auto-generated method stub
RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
.setContentType(ContentType.JSON).build();
LoginPage lp=new LoginPage();
lp.setUserEmail("biswas110ab@gmail.com");
lp.setUserPassword("Dimbak@123");
RequestSpecification log = given().log().all().spec(req).body(lp);
LoginResponse loginResponse = log.when().post("/api/ecom/auth/login").then().log().all()
.extract().response().as(LoginResponse.class);
System.out.println(loginResponse.getToken());
System.out.println(loginResponse.getUserId());


RequestSpecification addProductBasePage = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
.addHeader("Authorization", loginResponse.getToken())
.addHeader("Content-Type","multipart/form-data")
.build();

RequestSpecification reqAddProduct = given().log().all().spec(addProductBasePage).formParam("productName", "qwerty")
.formParam("productAddedBy",loginResponse.getUserId())
.formParam("productCategory", "fashion")
.formParam("productSubCategory", "shirts")
.formParam("productPrice", "11500")
.formParam("productDescription", "Addias Originals")
.formParam("productFor", "women")
.multiPart("productImage",new File("E:\\EcLipseProjects\\ProjRest\\src\\test\\java\\EcommerceProject\\pexels-mnz-1598505.jpg"));
String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
.then().log().all().extract().response().asString();
JsonPath js = new JsonPath(addProductResponse);
String productId = js.get("productId");
System.out.println(productId);

//Create Orders
RequestSpecification reqCreateORder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setRelaxedHTTPSValidation()
.addHeader("Authorization", loginResponse.getToken()).setContentType(ContentType.JSON).build();
OrderDetails od=new OrderDetails();
Orders or = new Orders();
or.setCountry("India");
or.setProductOrderedId(productId);
List<Orders> ke=new ArrayList<Orders>();
ke.add(or);
od.setOrders(ke);
RequestSpecification createOrderRequest = given().log().all().spec(reqCreateORder).body(od);
String responseAddOrder = createOrderRequest.when().post("/api/ecom/order/create-order").then().log().all()
.extract().response().asString();
System.out.println(responseAddOrder);

//Detele Product
RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setRelaxedHTTPSValidation()
.setContentType(ContentType.JSON)
.addHeader("Authorization", loginResponse.getToken()).build();
RequestSpecification deleteProductReq = given().spec(deleteProductBaseReq).log().all().pathParam("prodId", productId);
 String jse= deleteProductReq.when().delete("/api/ecom/product/delete-product/{prodId}").then()
.log().all().extract().body().asString();
JsonPath jpe=new JsonPath(jse);
System.out.println(jpe.getString("message"));
Assert.assertTrue(jpe.getString("message").equalsIgnoreCase("Product Deleted Successfully"));

	}

}
