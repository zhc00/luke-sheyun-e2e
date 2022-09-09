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

package luke.e2e.engine;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ReflectionUtils.HierarchyTraversalMode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.org.hamcrest.core.AnyOf;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MyTest {
    @RegisterExtension
    static Extension e = new BeforeAllCallback() {
        @Override
        public void beforeAll(ExtensionContext context) throws Exception {
            System.out.println("extension");
        }
    };
    
    
    
    @BeforeAll
    static void before() {
        System.out.println("before all func ============");
    }
    
    @Test
    void test() {
        RestTemplate rest = new RestTemplate();
        RequestEntity<String> request = RequestEntity.post("http://httpbin.org/post").body("xx");
        ResponseEntity<String> response = rest.exchange(request, String.class);
        System.out.println(response.getBody());
        
    
    
        assertThat("").isLessThan("");
        assertThat("")
                .is(new Condition<>(x -> false, "xx"));
    }
}
