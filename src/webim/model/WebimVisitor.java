/*
 * WebimEndpoint.java
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
package webim.model;

import java.util.Date;

/**
 * Webim访客对象
 * 
 * @author Feng Lee <feng.lee@nextalk.im>
 * @since 5.7
 */
public class WebimVisitor extends WebimEndpoint {
	
	public static final String TAG = "vid";
	
	/**
	 * 访客来源IP
	 */
	private String ipaddr;
	
	/**
	 * 访客来源引用
	 */
	private String referer;
	
	/**
	 * 访客地理位置
	 */
	private String location;
	
	/**
	 * 访客上线时间
	 */
	private Date created;


	public WebimVisitor(String id, String nick) {
		super(TAG + ":" + id, nick);
		setGroup("visitor");
	}


	public String getIpaddr() {
		return ipaddr;
	}


	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}


	public String getReferer() {
		return referer;
	}


	public void setReferer(String referer) {
		this.referer = referer;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


}
