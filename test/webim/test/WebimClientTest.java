package webim.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import webim.client.WebimClient;
import webim.client.WebimEndpoint;
import webim.client.WebimMessage;
import webim.client.WebimPresence;
import webim.client.WebimStatus;

public class WebimClientTest {

	private WebimClient client;
	
	private List<String> buddyIds;
	
	private List<String> roomIds;
	
	@Before
	public void setUp() throws Exception {
		WebimEndpoint ep = new WebimEndpoint("uid1", "user1");
		client = new WebimClient(ep, "localhost", "public", "localhost", 8000);

		buddyIds = new ArrayList<String>();
		buddyIds.add("uid1");
		buddyIds.add("uid2");
		
		roomIds = new ArrayList<String>();
		roomIds.add("room1");
		roomIds.add("room2");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnline() throws Exception {
		Map<String, Object> rt =  client.online(buddyIds, roomIds);
		System.out.println(rt);
	}

	@Test
	public void testOffline() throws Exception{
		Map<String, Object> rt = client.online(buddyIds, roomIds);
		System.out.println(rt);
		client.offline();
	}

	@Test
	public void testPublishWebimPresence() throws Exception {
		client.online(buddyIds, roomIds);
		client.publish(new WebimPresence("away", "Away"));
	}

	@Test
	public void testPublishWebimStatus() throws Exception{
		client.online(buddyIds, roomIds);
		client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));
	}

	@Test
	public void testPublishWebimMessage() throws Exception {
		client.online(buddyIds, roomIds);
		client.publish(new WebimMessage("uid2", "User1", "hahaha", "", 1292832.183));
	}
	
	//@Test 
	public void testPushMessage() throws Exception {
		client.push("uid3", new WebimMessage("uid2", "User3", "hahaha", "", 1292832.183));
	}
	
	@Test
	public void testPresences() throws Exception {
		client.online(buddyIds, roomIds);
		System.out.println(client.presences(buddyIds));
	}

	@Test
	public void testMembers() throws Exception {
		client.online(buddyIds, roomIds);
		JSONObject members = client.members("room1");
		System.out.println(members);
		members = client.members("room3");
		System.out.println(members);
	}

	@Test
	public void testJoin() throws Exception {
		client.online(buddyIds, roomIds);
		JSONObject rtObj = client.join("room1");
		System.out.print(rtObj);
	}

	@Test
	public void testLeave() throws Exception {
		client.online(buddyIds, roomIds);
	}

}
