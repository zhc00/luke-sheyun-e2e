///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package org.apache.shenyu.e2e.core;
//
//import lombok.Getter;
//import org.apache.shenyu.e2e.client.admin.AdminClient;
//import org.apache.shenyu.e2e.client.admin.AdminClientConfig;
//import org.apache.shenyu.e2e.client.gateway.GatewayClient;
//import org.apache.shenyu.e2e.client.gateway.GatewayClientConfig;
//import org.apache.shenyu.e2e.config.ShenYuEngineConfigure;
//import org.apache.shenyu.e2e.core.processor.ShenYuAnnotationProcessors;
//import org.apache.shenyu.e2e.core.processor.ShenYuAnnotationProcessors.FieldAnnotationProcessors;
//import org.apache.shenyu.e2e.core.service.DockerServiceCompose;
//import org.apache.shenyu.e2e.core.service.LocalServiceCompose;
//import org.apache.shenyu.e2e.core.service.ServiceCompose;
//import org.junit.jupiter.api.extension.ExtensionContext.Store;
//
//import java.util.List;
//import java.util.function.Supplier;
//
//public class ShenYuEngineContext {
//    private final List<FieldAnnotationProcessors> processors;
//    private final ShenYuEngineConfigure configure;
//    private final Class<?> testClass;
//    @Getter
//    private final Store store;
//
//    private ServiceCompose serviceCompose;
//
//    public ShenYuEngineContext(Store store, Class<?> testClass, ShenYuEngineConfigure configure, List<FieldAnnotationProcessors> processors) {
//        this.store = store;
//        this.testClass = testClass;
//        this.configure = configure;
//        this.processors = processors;
//    }
//
//    public void setup() {
//        processors.forEach(p -> p.preprocess(store));
//
//        AdminClientConfig adminClientConfig = getValue(AdminClientConfig.class);
//        GatewayClientConfig gatewayClientConfig = getValue(GatewayClientConfig.class);
//        if (configure.isRunOnHost()) {
//            serviceCompose = new LocalServiceCompose(adminClientConfig, gatewayClientConfig);
//        } else {
//            serviceCompose = new DockerServiceCompose(configure.getDockerComposeFile(), adminClientConfig, gatewayClientConfig);
//        }
//
//        serviceCompose.setup();
//    }
//
//    public boolean isRunOnHost() {
//        return configure.isRunOnHost();
//    }
//
//    public void resolveAnnotations(Object instance) {
//        store.put(AdminClient.class, (Supplier<AdminClient>) this::getAdminClient);
//        store.put(GatewayClient.class, (Supplier<GatewayClient>) this::getGatewayClient);
//
//        processors.forEach(p -> p.process(store, instance));
//    }
//
//    public AdminClient getAdminClient() {
//        return serviceCompose.newAdminClient();
//    }
//
//    public GatewayClient getGatewayClient() {
//        return serviceCompose.newGatewayClient();
//    }
//
//
//    public void cleanup() {
//        serviceCompose.cleanup();
//        // todo remove store
//    }
//
//    public static ShenYuEngineContext fromTestClass(Store store, Class<?> testClass) {
//        ShenYuEngineConfigure configure = ShenYuAnnotationProcessors.resolveTestClassAnnotations(testClass);
//        List<FieldAnnotationProcessors> processors = ShenYuAnnotationProcessors.resolveFieldAnnotations(testClass);
//        return new ShenYuEngineContext(store, testClass, configure, processors);
//    }
//
//    public <T> T getValue(Class<T> clazz) {
//        return store.get(clazz, clazz);
//    }
//
//}
