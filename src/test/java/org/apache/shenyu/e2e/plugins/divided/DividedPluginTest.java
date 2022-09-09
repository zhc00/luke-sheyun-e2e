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

package org.apache.shenyu.e2e.plugins.divided;

import org.apache.shenyu.e2e.client.admin.AdminClient;
import org.apache.shenyu.e2e.core.annotation.ShenYuE2E;
import org.apache.shenyu.e2e.plugins.divided.DividedPluginTest.Configure;
import org.apache.shenyu.e2e.plugins.divided.DividedPluginTest.ServiceConfigure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Stream;

@ShenYuE2E
@Configure(
        services = {
                @ServiceConfigure(
                        ServiceName = "xxx"
                ),
                @ServiceConfigure(
                        ServiceName = "yyy"
                )
        },
        sharedKey = "handlers",
        dockerComposeFile = ""
)
public class DividedPluginTest {
    
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Configure {
        ServiceConfigure[] services() default {};
        
        String sharedKey() default "global";
        
        String dockerComposeFile() default "docker-compose.yml";
    }
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ServiceConfigure {
        String ServiceName() default "";
        
        String schema() default "http";
        
        int port() default 9090;
        
        String baseUrl() default "";
    }
    
    Stream<Arguments> cases() {
        return null;
    }
//
//    @BeforeAll
//    void setup(AdminClient client, Sense sense) {
//        sense.req("").expect(R.status(404).xx());
//    }
//
//    @BeforeEach
//    void before() {
//    }
//
//    @ParameterizedTest
//    @MethodSource("cases")
//    void test(Sense sense) {
//
//    }
//
//    @AfterAll
//    void cleanup(AdminClient client, Sense sense) {
//        client.deleteAll();
//        sense.req("").expecte(R.status(400).xx());
//    }
}
