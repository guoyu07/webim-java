package webim.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import webim.WebimClient;
import webim.WebimEndpoint;
import webim.WebimMessage;
import webim.WebimPresence;
import webim.WebimStatus;

public class WebimClientTest {

	private WebimClient client;
	
	private List<String> buddyIds;
	
	private List<String> groupIds;
	
	@Before
	public void setUp() throws Exception {
		WebimEndpoint ep = new WebimEndpoint("uid1", "user1");
		client = new WebimClient(ep, "localhost", "public", "localhost", 8000);
		buddyIds = new ArrayList<String>();
		buddyIds.add("uid1");
		buddyIds.add("uid2");
		
		groupIds = new ArrayList<String>();
		groupIds.add("grp1");
		groupIds.add("grp2");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnline() throws Exception {
		JSONObject rt = client.online(buddyIds, groupIds);
		System.out.println(rt);
	}

	@Test
	public void testOffline() throws Exception{
		JSONObject rt = client.online(buddyIds, groupIds);
		System.out.println(rt);
		client.offline();
	}

	@Test
	public void testPublishWebimPresence() throws Exception {
		client.online(buddyIds, groupIds);
		client.publish(new WebimPresence("away", "Away"));
	}

	@Test
	public void testPublishWebimStatus() throws Exception{
		client.online(buddyIds, groupIds);
		client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));
	}

	@Test
	public void testPublishWebimMessage() throws Exception {
		client.online(buddyIds, groupIds);
		client.publish(new WebimMessage("uid2", "User1", "hahaha", "", 1292832.183));
	}
	
	@Test 
	public void testPushMessage() throws Exception {
		client.push("uid3", new WebimMessage("uid2", "User3", "hahaha", "", 1292832.183));
	}
	
	@Test
	public void testPresences() throws Exception {
		client.online(buddyIds, groupIds);
		System.out.println(client.presences(buddyIds));
	}

	@Test
	public void testMembers() throws Exception {
		client.online(buddyIds, groupIds);
		JSONArray members = client.members("grp1");
		System.out.println(members);
	}

	@Test
	public void testJoin() throws Exception {
		client.online(buddyIds, groupIds);
		JSONObject rtObj = client.join("grp1");
		System.out.print(rtObj);
	}

	@Test
	public void testLeave() throws Exception {
		client.online(buddyIds, groupIds);
	}

}
