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


/**
 * Webim机器人
 * 
 * @author Feng Lee <feng.lee@nextalk.im>
 * @since 5.7
 */
public class WebimRobot extends WebimEndpoint {

	private static final long serialVersionUID = 6087298698057543417L;

	public final static String TAG = "robot";

	private WebimAskProvider provider = null;

	public WebimRobot(String id, String nick) {
		super("robot:" + id, nick);
		setGroup("robot");
		setPresence("online");
		setShow("available");
	}

	/**
	 * 设置机器人问题提供者
	 * 
	 * @param provider
	 */
	public void setProvider(WebimAskProvider provider) {
		this.provider = provider;
	}

	/**
	 * 回答问题
	 * 
	 * @param ask 问题
	 * @return 问题答案
	 */
	public String answer(String ask) {
		if(provider == null) {
			return "Cannot answer your question...:(";
		}
		return provider.answer(ask);
	}

	/**
	 * 获取问题列表
	 * 
	 * @return 问题列表
	 */
	public String[] getAskList() {
		if(provider == null) {
			return new String[0];
		}
		return provider.askList();
	}

}
