
Webim-java
==========

webim java sdk 5.2

Depends
=======

lib/org.json-20120521.jar

Dist Jar
========

dist/webim-5.2.jar

API
===

WebimClient
------------------
	WebimEndpoint ep = new WebimEndpoint("uid1", "user1");

	WebimClient client = new WebimClient(ep, "localhost", "public", "localhost", 8000);

Online
------

	List<String> buddyIds = new ArrayList<String>();
	buddyIds.add("uid1");
	buddyIds.add("uid2");

	List<String> groupIds = new ArrayList<String>();
	groupIds.add("grp1");
	groupIds.add("grp2");

	JSONObject rt = client.online(buddyIds, groupIds);


Offline
-------	

	client.offline()


Publish Message
---------------

	client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));

Publish Status
--------------

	client.publish(new WebimStatus("uid2", "typing", "User2 is typing"));
	
Publish Presence
----------------

	client.publish(new WebimPresence("away", "Away"));


Push Message
------------

	client.push("uid3", new WebimMessage("uid2", "User3", "hahaha", "", 1292832.183));

Presences
---------

	client.presences(buddyIds);

Members
-------
	
	JSONArray members = client.members("grp1");


developer
=========

http://nextalk.im

详见javadoc


