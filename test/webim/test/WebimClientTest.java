package webim.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import webim.client.WebimClient;
import webim.client.WebimCluster;
import webim.model.WebimEndpoint;
import webim.model.WebimMessage;
import webim.model.WebimPresence;
import webim.model.WebimStatus;
import webim.model.WebimUser;

public class WebimClientTest {

	private WebimClient client;

	private Set<String> buddyIds;

	private Set<String> roomIds;

	private WebimCluster cluster = new WebimCluster() {

		@Override
		public String getServer(WebimEndpoint ep) {
			return "t.nextalk.im:8000";
		}
		
	};
	
	@Before
	public void setUp() throws Exception {
		WebimEndpoint ep = new WebimUser("uid1", "user1");
		client = new WebimClient(ep, "localhost", "public", cluster);

		buddyIds = new HashSet<String>();
		buddyIds.add("uid1");
		buddyIds.add("uid2");

		roomIds = new HashSet<String>();
		roomIds.add("room1");
		roomIds.add("room2");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnline() throws Exception {
		Map<String, Object> rt = client.online(buddyIds, roomIds);
		System.out.println(rt);
	}

	@Test
	public void testOffline() throws Exception {
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
	public void testPublishWebimStatus() throws Exception {
		client.online(buddyIds, roomIds);
		client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));
	}

	@Test
	public void testPublishWebimMessage() throws Exception {
		client.online(buddyIds, roomIds);
		client.publish(new WebimMessage("uid2", "User1", "hahaha", "",
				1292832.183));
	}

	// @Test
	public void testPushMessage() throws Exception {
		client.push("uid3", new WebimMessage("uid2", "User3", "hahaha", "",
				1292832.183));
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
