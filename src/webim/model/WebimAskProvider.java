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
 * Webim机器人问题提供者
 * 
 * @author Feng Lee <feng.lee@nextalk.im>
 * @since 5.7 
 */
public interface WebimAskProvider {

	/**
	 * 回答问题
	 * 
	 * @param ask 输入问题
	 * @return 问题答案
	 */
	String answer(String ask);

	/**
	 * 问题列表
	 * 
	 * @return 问题列表
	 */
	String[] askList();
	
}
