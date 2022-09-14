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

package org.apache.shenyu.e2e.testcase.common.specification;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.shenyu.e2e.engine.scenario.function.Verifier;
import org.apache.shenyu.e2e.engine.scenario.specification.CaseSpec;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShenYuCaseSpec implements CaseSpec {
    
    private final String name;
    
    private final List<Verifier> verifiers;
    
    public static ShenYuTestCaseSpecBuilder builder() {
        return new ShenYuTestCaseSpecBuilder();
    }
    
    public static ShenYuTestCaseSpecBuilder builder(String name) {
        return new ShenYuTestCaseSpecBuilder(name);
    }
    
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShenYuTestCaseSpecBuilder {
        private String name;
        private final Builder<Verifier> builder = ImmutableList.builder();
        
        public ShenYuTestCaseSpecBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ShenYuTestCaseSpecBuilder add(Verifier verifier) {
            builder.add(verifier);
            return this;
        }
        
        public ShenYuCaseSpec build() {
            return new ShenYuCaseSpec(name, builder.build());
        }
    }
}
