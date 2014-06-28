/*
 * WebimMember.java
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

/**
 * Webim群组成员
 * 
 * @author Ery Lee <ery.lee at gmail.com>
 * @since 5.4
 */

public class WebimMember {

	/**
	 * 成员id
	 */
	private String id;

	/**
	 * 成员昵称
	 */
	private String nick;

	/**
	 * 成员现场，默认offline
	 */
	private String presence = "offline";
	
	/**
	 * 成员现场状态，默认unavailable
	 */
	private String show = "unavailable";
	
	/**
	 * 缺省构造函数
	 */
	public WebimMember() {
		
	}
	
	/**
	 * 创建成员实例
	 * 
	 * @param id
	 *            成员id
	 * @param nick
	 *            成员昵称
	 */
	public WebimMember(String id, String nick) {
		this.id = id;
		this.nick = nick;
	}

	/**
	 * 成员ID
	 * 
	 * @return 成员ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 成员昵称
	 * 
	 * @return 成员昵称
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 设置成员昵称
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * 成员现场
	 * 
	 * @return 成员现场
	 */
	public String getPresence() {
		return presence;
	}

	/**
	 * 设置成员现场
	 * 
	 * @param presence
	 */
	public void setPresence(String presence) {
		this.presence = presence;
	}

	/**
	 * 成员现场状态
	 * 
	 * @return 现场状态
	 */
	public String getShow() {
		return show;
	}
	
	/**
	 * 设置成员现场状态
	 * 
	 * @param show
	 */
	public void setShow(String show) {
		this.show = show;
	}

}
