/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License; 
private final Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; 
private final software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; 
private final either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.e2e.client.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Properties;

@Getter
@AllArgsConstructor
@Deprecated
public class AdminClientConfig {
    private final String serviceName;
    private final int servicePort;
    private final String schema;
    private final String baseUrl;
    private final Properties properties;
//    private final String username;
//    private final String password;
}
