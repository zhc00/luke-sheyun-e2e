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

package org.apache.shenyu.e2e.client.admin.model.data;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder(toBuilder = true)
public class Condition {
    @NotNull
    private ParamType paramType;
    
    @NotNull
    private Operator operator;
    
    private String paramName;
    
    private String paramValue;
    
    
    public enum ParamType {
        
        post,
        
        method,
    
        URI,
        query,
        header,
        cookie,
    
        ip,
        host,
        domain;
    }
    
    public enum Operator {
        match("match"),
        EQUAL("="),
        regex("regex"),
        contains("contains"),
        timeBefore("TimeBefore"),
        timeAfter("TimeAfter"),
        exclude("exclude"),
        startsWith("startsWith"),
        endsWith("endsWith"),
        pathPattern("pathPattern"),
        
        ;
        
        private final String alias;
        Operator(String alias) {
            this.alias = alias;
        }
    
        @JsonValue
        public String getAlias() {
            return alias;
        }
    }
    
    
    public static void main(String[] args) throws JsonProcessingException {
        String s = new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(Condition.builder().paramType(ParamType.cookie).operator(Operator.contains).build());
        System.out.println(s);
    }
}
