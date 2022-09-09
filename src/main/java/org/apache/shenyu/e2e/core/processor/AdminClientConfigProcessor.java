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

package org.apache.shenyu.e2e.core.processor;

import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.client.admin.AdminClientConfig;
import org.apache.shenyu.e2e.core.annotation.ShenYuAdminClientConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class AdminClientConfigProcessor extends ShenYuAnnotationProcessor<ShenYuAdminClientConfig> {
    
    public AdminClientConfigProcessor(ShenYuAdminClientConfig annotation) {
        super(ShenYuAnnotationProcessors.getAnnotationProxy(annotation));
    }
    
    @Override
    public void preprocess(Store store) {
//        AdminClientConfig config = new AdminClientConfig(
//                annotation.serviceName(),
//                annotation.servicePort(),
//                annotation.schema(),
//                annotation.baseUrl(),
//                annotation.username(),
//                annotation.password()
//        );
//        store.put(AdminClientConfig.class, config);
    }
    
    @Override
    public void process(Store store, Object target, Field field) {
        Assertions.assertDoesNotThrow(() -> {
            field.setAccessible(true);
            field.set(target, store.get(AdminClient.class, Supplier.class).get());
        });
    }
    
}
