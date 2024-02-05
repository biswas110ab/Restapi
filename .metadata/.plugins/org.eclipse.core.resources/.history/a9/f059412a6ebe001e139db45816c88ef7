import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class TotalPriceCheck {

	@Test
	public void checkPrice()
	{
		JsonPath js=new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		int sumAmount=0;
		int expAmount=js.getInt("dashboard.purchaseAmount");
		for(int i=0;i<count;i++)
		{
			int course=js.getInt("courses["+i+"].price");
			int price=js.getInt("courses["+i+"].copies");
			int amount=course*price;
			sumAmount+=amount;
		}
		Assert.assertEquals(sumAmount, expAmount,"Values do not match");
	}
}
