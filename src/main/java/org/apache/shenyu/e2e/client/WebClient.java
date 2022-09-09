package org.apache.shenyu.e2e.client;///*
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
//package luke.e2e.client;
//
//import org.apache.http.HttpHost;
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.tomcat.util.http.parser.MediaType;
//import org.springframework.lang.Nullable;
//
//public interface WebClient {
//
//    enum HttpMethod {
//        POST,
//        GET,
//        PUT,
//        DELETE,
//    }
//
//    enum HttpStatus {
//        OK,
//        NotFound,
//    }
//
//    HttpResponse execute(HttpMethod method, String uri, @Nullable HttpHeaders headers, @Nullable HttpBody body);
//
//    class HttpHeaders {
//
//        HttpClient client = HttpClientBuilder.create()
//                .build();
//
//        void dox() {
//            HttpHost host = new HttpHost("", 90, "");
//        }
//
//    }
//
//    interface HttpRequest {
//
//    }
//
//    interface HttpResponse {
//        HttpExpect expect();
//    }
//
//    interface HttpExpect {
//        interface Headers {
//
//        }
//
//        Headers headers();
//
//        HttpExpect status(int statusCode);
//
//        HttpExpect status(String statusLine);
//
//        HttpExpect content(MediaType type, HttpBody body);
//    }
//
////    interface HttpHeaders {
////
////    }
////
//    interface HttpBody {
//
//    }
//}
