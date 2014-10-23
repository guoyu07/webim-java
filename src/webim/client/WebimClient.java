/*
 * WebimClient.java
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

import org.json.JSONException;
import org.json.JSONObject;

import webim.model.WebimEndpoint;
import webim.model.WebimMessage;
import webim.model.WebimPresence;
import webim.model.WebimStatus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

/**
 * WebimClient是与消息服务器通信接口，采用JSON/HTTP协议设计，接口包括:<br>
 * <ul>
 * <li>online: 通知消息服务器用户上线</li>
 * <li>online: 通知消息服务器用户下线</li>
 * <li>publish: 向消息服务器转发Presence、Status、Message</li>
 * <li>push: 直接向消息服务器推送消息，无需先登录获取Ticket</li>
 * <li>presences: 获取多用户的现场(Presence)</li>
 * <li>members: 向消息服务器获取群组当前在线用户表</li>
 * <li>join: 通知消息服务器有用户加入群组</li>
 * <li>leave: 通知消息服务器有用户离开群组</li>
 * </ul>
 * 
 * @author Feng Lee <feng.lee@nextalk.im>
 * @since 1.0
 */
public class WebimClient {

	/**
	 * 协议版本号
	 */
	public static String APIVSN = "v5";

	/**
	 * 当前端点(用户)
	 */
	private WebimEndpoint ep;

	/**
	 * 站点域名
	 */
	private String domain;

	/**
	 * 消息服务器通信APIKEY
	 */
	private String apikey;

	/**
	 * 消息服务器内部通信地址
	 */
	private WebimCluster cluster;
	
	//private String host;

	/**
	 * 消息服务器端口
	 */
	//private int port;

	/**
	 * 通信令牌，每online一次，消息服务器返回一个新令牌
	 */
	private String ticket = null;

	/**
	 * 创建Clieng实例
	 * 
	 * @param ep
	 *            当前端点
	 * @param domain
	 *            域名
	 * @param apikey
	 *            APIKEY
	 * @param cluster
	 *            消息服务器集群提供者
	 */
	public WebimClient(WebimEndpoint ep, String domain, String apikey, WebimCluster cluster) {
		this.ep = ep;
		this.domain = domain;
		this.apikey = apikey;
		this.cluster = cluster;
	}

	/**
	 * 设置与消息服务器通信令牌
	 * 
	 * @param ticket
	 *            通信令牌
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * 域名
	 * 
	 * @return Domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 端点
	 * 
	 * @return Endpoint
	 */
	public WebimEndpoint getEndpoint() {
		return ep;
	}

	/**
	 * 集群策略
	 * 
	 * @return 集群策略
	 */
	public WebimCluster getCluster() {
		return cluster;
	}

	/**
	 * 设置集群策略
	 */
	public void setCluster(WebimCluster cluster) {
		this.cluster = cluster;
	}


	/**
	 * 通知消息服务器用户上线
	 * 
	 * @param buddies
	 *            用户好友id列表
	 * @param rooms
	 *            用户群组id列表
	 * 
	 * @return Map<String,Object> 成功返回Map对象，存放连接(connection)、现场列表(presences)等信息。
	 * @throws WebimException
	 *             失败返回异常
	 */
	public Map<String, Object> online(Set<String> buddies, Set<String> rooms)
			throws WebimException {
		Map<String, String> data = newData();
		data.put("rooms", this.join(",", rooms));
		data.put("buddies", this.join(",", buddies));
		data.put("name", ep.getId());
		data.put("nick", ep.getNick());
		data.put("status", ep.getStatus());
		data.put("show", ep.getShow());
		try {
			String body = httpost("/presences/online", data);
			JSONObject json = new JSONObject(body);
			setTicket(json.getString("ticket"));
			Map<String, String> conn = new HashMap<String, String>();
			conn.put("ticket", json.getString("ticket"));
			conn.put("domain", this.domain);
			conn.put("jsonpd", json.getString("jsonpd"));
			conn.put("server", json.getString("jsonpd"));
			if (json.has("websocket"))
				conn.put("websocket", json.getString("websocket"));
			if (json.has("mqtt"))
				conn.put("mqtt", json.getString("mqtt"));
			Map<String, Object> rtData = new HashMap<String, Object>();
			rtData.put("success", true);
			rtData.put("connection", conn);
			rtData.put("presences", json2Map(json.getJSONObject("presences")));
			return rtData;
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 通知消息服务器下线
	 * 
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject offline() throws WebimException {
		Map<String, String> data = newData();
		data.put("ticket", this.ticket);
		try {
			String body = httpost("/presences/offline", data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 向消息服务器转发现场(Presence)
	 * 
	 * @param presence
	 *            现场
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject publish(WebimPresence presence) throws WebimException {
		Map<String, String> data = newData();
		data.put("nick", ep.getNick());
		presence.feed(data);
		try {
			String body = httpost("/presences/show", data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 向消息服务器转发状态(Status)
	 * 
	 * @param status
	 *            状态
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject publish(WebimStatus status) throws WebimException {
		Map<String, String> data = newData();
		data.put("nick", ep.getNick());
		status.feed(data);
		try {
			String body = httpost("/statuses", data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 向消息服务器转发即时消息(Message)
	 * 
	 * @param message
	 *            即时消息
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject publish(WebimMessage message) throws WebimException {
		Map<String, String> data = newData();
		message.feed(data);
		try {
			String body = httpost("/messages", data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 向消息服务器直接推送即时消息(Message)，无需登录获取ticket。
	 * 
	 * @param message
	 *            即时消息
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject push(String from, WebimMessage message)
			throws WebimException {
		Map<String, String> data = newData();
		data.put("from", from);
		message.feed(data);
		try {
			String body = httpost("/messages", data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 从消息服务器读取多多用户现场(Presence)信息
	 * 
	 * @param ids
	 *            用户id列表
	 * @return JsonObject对象，例子: {"uid1": "available", "uid2": "away"}
	 * 
	 * @exception WebimException
	 */
	public JSONObject presences(Set<String> ids) throws WebimException {
		Map<String, String> data = newData();
		data.put("ids", join(",", ids));
		try {
			String body = httpget("/presences", data);
			// compatible with 5.4.1
			if (body.startsWith("[")) {
				return new JSONObject("{}");
			}
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 向消息服务器请求群组在线成员信息
	 * 
	 * @param room
	 *            群组id
	 * @return presences, 成员现场状态
	 * @throws WebimException
	 */
	public JSONObject members(String room) throws WebimException {
		Map<String, String> data = newData();
		data.put("room", room);
		try {
			String uri = String.format("/rooms/%s/members", room);
			String body = httpget(uri, data);
			// compatible with 5.4.1
			if (body.startsWith("[")) {
				return new JSONObject("{}");
			}
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 通知消息服务器用户加入群组
	 * 
	 * @param room
	 *            群组id
	 * @return JSONObject "{'id': room, 'count': '0'}"
	 * @throws WebimException
	 */
	public JSONObject join(String room) throws WebimException {
		Map<String, String> data = newData();
		data.put("nick", ep.getNick());
		data.put("room", room);
		try {
			String uri = String.format("/rooms/%s/join", room);
			String body = httpost(uri, data);
			JSONObject respObj = new JSONObject(body);
			return respObj;
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	/**
	 * 通知消息服务器用户离开群组
	 * 
	 * @param room
	 *            群组id
	 * @return JSONObject "{'status': 'ok'}" or
	 *         "{'status': 'error', 'message': 'blabla'}"
	 * @throws WebimException
	 */
	public JSONObject leave(String room) throws WebimException {
		Map<String, String> data = newData();
		data.put("nick", ep.getNick());
		data.put("room", room);
		try {
			String uri = String.format("/rooms/%s/leave", room);
			String body = httpost(uri, data);
			return new JSONObject(body);
		} catch (WebimException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebimException(500, e.getMessage());
		}
	}

	private String httpget(String path, Map<String, String> params)
			throws Exception {
		URL url;
		HttpURLConnection conn = null;
		try {
			url = new URL(apiurl(path) + "?" + encodeData(params));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			initConn(conn);
			conn.connect();
			return readResonpse(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private String httpost(String path, Map<String, String> data)
			throws Exception {
		URL url;
		HttpURLConnection conn = null;
		try {
			// Create connection
			url = new URL(apiurl(path));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			String urlParameters = encodeData(data);
			conn.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));

			initConn(conn);

			// Send request
			DataOutputStream wr = null;
			try {
				wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
			} finally {
				if (wr != null)
					wr.close();
			}
			return readResonpse(conn);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private void initConn(HttpURLConnection conn)
			throws UnsupportedEncodingException {
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		String basicAuth = DatatypeConverter.printBase64Binary((this.domain
				+ ":" + this.apikey).getBytes("UTF-8"));
		conn.setRequestProperty("Authorization", "Basic " + basicAuth);
	}

	private String readResonpse(HttpURLConnection conn) throws IOException,
			WebimException {
		// Get Response
		if (conn.getResponseCode() != 200) {
			throw new WebimException(conn.getResponseCode(),
					conn.getResponseMessage());
		}
		BufferedReader rd = null;
		StringBuffer response = new StringBuffer();
		try {
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			// System.out.println(response.toString());
		} finally {
			if (rd != null)
				rd.close();
		}
		return response.toString();
	}

	private Map<String, String> newData() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("version", APIVSN);
		data.put("domain", domain);
		data.put("apikey", apikey);
		if (ticket != null) {
			data.put("ticket", ticket);
		}
		return data;
	}

	private String encodeData(Map<String, String> data) throws Exception {
		Set<String> list = new HashSet<String>();
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pair = it.next();
			list.add(pair.getKey() + "="
					+ URLEncoder.encode(pair.getValue(), "utf-8"));
		}
		return join("&", list);
	}

	private String join(String sep, Set<String> ss) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = ss.iterator();
		while (it.hasNext()) {
			String s = it.next();
			if (first) {
				sb.append(s);
				first = false;
			} else {
				sb.append(sep);
				sb.append(s);
			}
		}
		return sb.toString();
	}

	private String apiurl(String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return String.format("http://%s/%s%s", cluster.getServer(ep), APIVSN, path);
	}

	private Map<String, String> json2Map(JSONObject json) throws JSONException {
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			String key = it.next();
			map.put(key, json.getString(key));
		}
		return map;
	}

}
