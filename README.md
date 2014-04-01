
WebIM-java
==========

WebIM Java SDK for NexTalk.IM

Requires
========

Java 1.6+

lib/org.json-20120521.jar

Dist Jar
========

dist/webim.client-5.4-$date.jar

API
===

WebimClient
------------------
	WebimEndpoint ep = new WebimEndpoint("uid1", "user1");

	WebimClient client = new WebimClient(ep, "domain", "apikey", "localhost", 8000);

Online
------

	List<String> buddyIds = new ArrayList<String>();
	buddyIds.add("uid1");
	buddyIds.add("uid2");

	List<String> roomIds = new ArrayList<String>();
	roomIds.add("room1");
	roomIds.add("room2");

	Map<String,Object> rt = client.online(buddyIds, roomIds);


Offline
-------

	client.offline()


Publish Message
---------------

	client.publish(new WebimMessage("uid2", "User3", "hahaha", "", 1292832.183);

Publish Status
--------------

	client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));

Publish Presence
----------------

	client.publish(new WebimPresence("away", "Away"));


Push Message
------------

	client.push("uid3", new WebimMessage("uid2", "User3", "hahaha", "", 1292832.183));

Read Presences
---------

	client.presences(buddyIds);

Read Room Members
-------

	JSONObject members = client.members("room1");


Author
=========

http://nextalk.im

详见javadoc
