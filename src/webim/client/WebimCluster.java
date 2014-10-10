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

import webim.model.WebimEndpoint;

/** 
 * Webim集群支持。将当前登陆用户，按自定义规则分配到不同消息服务器。
 * 
 * @author Feng Lee <feng.lee@nextalk.im>
 * @since 5.7
 */
public interface WebimCluster {

	/**
	 * 根据当前用户，返回消息服务器地址
	 * 
	 * @param ep 当前用户
	 * @return 消息服务器地址
	 */
	String getServer(WebimEndpoint ep);
	
}
