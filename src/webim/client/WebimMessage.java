/*
 * WebimMessage.java
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package webim.client;

import java.util.Map;

/**
 * Webim即时消息对象
 * 
 * @author Ery Lee <ery.lee @ gmail.com>
 * @since 1.0
 */
public class WebimMessage {

	private String style = "";
	private String body;
	private String nick;
	private String to;
	private String type = "chat";
	private boolean offline = false;
	private double timestamp;
	
	public WebimMessage() {
		
	}
	
	public WebimMessage(String to, String nick, String body,
			String style, double timestamp) {
		this.to = to;
		this.nick = nick;
		this.body = body;
		this.style = style;
		this.timestamp = timestamp;
	}

	public String getNick() {
		return nick;
	}

	public String getTo() {
		return to;
	}

	public String getType() {
		return type;
	}

	public String getBody() {
		return body;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public String getStyle() {
		return style;
	}

	public void feed(Map<String, String> data) {
		data.put("to", to);
		data.put("nick", nick);
		data.put("type", type);
		data.put("body", body);
		data.put("style", style);
		data.put("timestamp", String.valueOf(timestamp));
	}
	
	public String toString() {
		return String.format("Message(to=%s, body=%s, style=%s, timestamp=%d",
				to,body, style, timestamp);
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

}
