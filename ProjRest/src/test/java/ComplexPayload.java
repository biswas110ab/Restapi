import java.util.Iterator;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexPayload {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js=new JsonPath(Payload.CoursePrice());
		//size method applies only on array to get the count
		int count = js.getInt("courses.size()");
		System.out.println(count);
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		String titleFirstCourse=js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		//Print all course and there respective titles
		for(int i=0;i<count;i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitle);

		} 
		System.out.println("Print no of copies sold by RPA Course"); 
		for(int j=0;j<count;j++)
		{
			if(js.getString("courses["+j+"].title").equalsIgnoreCase("RPA"))
			{
				System.out.println(js.get("courses["+j+"].copies").toString());
				break;
			}
		}

	}

}