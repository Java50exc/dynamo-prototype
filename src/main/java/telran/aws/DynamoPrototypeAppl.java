package telran.aws;

import java.util.*;
import java.util.stream.IntStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;

public class DynamoPrototypeAppl {

	public static void main(String[] args) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		DynamoDB dynamo = new DynamoDB(client);
		Table table = dynamo.getTable("avg-values");
		List<Map<String, Object>> mapList = getMapList(20);
		
		mapList.forEach(m -> table.putItem(new PutItemSpec().withItem(Item.fromMap(m))));
	}

	private static List<Map<String, Object>> getMapList(int nMaps) {
		List<Map<String, Object>> result = IntStream.range(0, nMaps).mapToObj(i -> getRandomMap()).toList();
		
		return result;
	}

	private static Map<String, Object> getRandomMap() {
		Random gen = new Random();
		Map<String, Object> result = new HashMap<>();
		result.put("id", gen.nextInt(1, 4));
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		result.put("timestamp", System.currentTimeMillis());
		result.put("value", gen.nextFloat(100, 200));
		return result;
	}

}
