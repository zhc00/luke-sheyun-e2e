/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.e2e.core.service;

import org.apache.shenyu.e2e.client.ExternalServiceClient;
import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.client.admin.AdminClientConfig;
import org.apache.shenyu.e2e.client.gateway.GatewayClient;
import org.apache.shenyu.e2e.client.gateway.GatewayClientConfig;

import java.util.Objects;

public interface ServiceCompose {
    
    void start();
//
//    /**
//     * Get ShenYu Admin Server base url.
//     *
//     * @return {schema}://{host of admin service}:{port of admin service}
//     */
//    String getAdminBaseUrl();
//
//
//    /**
//     * Get ShenYu Bootstrap(Gateway) server base url.
//     *
//     * @return {schema}://{host of bootstrap service}:{port of bootstrap service}
//     */
//    String getGatewayBaseUrl();
    
    AdminClient newAdminClient(String scenarioId);
    
    GatewayClient newGatewayClient(String scenarioId);
    
    ExternalServiceClient newExternalServiceClient(String externalServiceName);
    
//    AdminClientConfig getAdminClientConfig();
//
//    GatewayClientConfig getGatewayClientConfig();
//
//    default AdminClient newAdminClient() {
//        AdminClientConfig config = getAdminClientConfig();
//        if (Objects.isNull(config)) {
//            return null;
//        }
//        return new AdminClient(getAdminBaseUrl(), config.getUsername(), config.getPassword());
//    }
//
//    default GatewayClient newGatewayClient() {
//        GatewayClientConfig config = getGatewayClientConfig();
//        if (Objects.isNull(config)) {
//            return null;
//        }
//        return new GatewayClient(getGatewayBaseUrl());
//    }
    
    void stop();
}
