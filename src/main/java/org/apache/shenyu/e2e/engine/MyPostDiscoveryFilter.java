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

package org.apache.shenyu.e2e.engine;

import org.apache.shenyu.e2e.core.annotation.ShenYuScenario;
import org.apache.shenyu.e2e.core.annotation.ShenYuTest;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.engine.config.DefaultJupiterConfiguration;
import org.junit.jupiter.engine.config.JupiterConfiguration;
import org.junit.jupiter.engine.descriptor.ClassTestDescriptor;
import org.junit.jupiter.engine.descriptor.JupiterTestDescriptor;
import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.launcher.PostDiscoveryFilter;
import org.testcontainers.shaded.org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class MyPostDiscoveryFilter implements PostDiscoveryFilter {
    
    @Override
    public FilterResult apply(TestDescriptor object) {
        if (object instanceof ClassTestDescriptor) {
            System.out.println(">>>>>>>>>>>>" + object.isContainer() + ", " + object.getClass());
            Class<?> testClass = ((ClassTestDescriptor) object).getTestClass();
            if (testClass.isAnnotationPresent(ShenYuTest.class)) {
                AnnotationUtils.findAnnotatedFields(testClass, ShenYuScenario.class, f -> true).forEach(e -> {
                    System.out.println(" xxxxxxxxxxx : " + e);
                });
    
                System.out.println("<<<<<<<<<<<<<<<<");
                
                
            }
            try {
                Field field = FieldUtils.getDeclaredField(JupiterTestDescriptor.class, "configuration", true);
                JupiterConfiguration conf = (JupiterConfiguration) field.get(object);
        
                object.addChild(new TestMethodTestDescriptor(
                        object.getUniqueId().append(TestMethodTestDescriptor.SEGMENT_TYPE, "xxxx"),
                        MyPostDiscoveryFilter.class,
                        MyPostDiscoveryFilter.class.getDeclaredMethod("x"),
                        conf
                ));
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return FilterResult.included("");
    }
    
    @TestFactory
    Stream<DynamicTest> x() {
        return Stream.<DynamicTest>builder().build();
    }
}
