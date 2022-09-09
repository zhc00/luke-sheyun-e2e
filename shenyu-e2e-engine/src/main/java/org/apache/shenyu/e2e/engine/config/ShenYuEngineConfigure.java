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

package org.apache.shenyu.e2e.engine.config;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.DockerConfigure.DockerServiceConfigure;
import org.apache.shenyu.e2e.engine.config.ShenYuEngineConfigure.HostConfigure.HostServiceConfigure;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.Parameter;
import org.apache.shenyu.e2e.engine.annotation.ShenYuTest.ServiceConfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Getter
public class ShenYuEngineConfigure {
    
    private Mode mode;
    
    private HostConfigure hostConfigure;
    
    private DockerConfigure dockerConfigure;
    
    public boolean isRunOnHost() {
        return Mode.host == mode;
    }
    
    public boolean isRunOnDocker() {
        return Mode.docker == mode;
    }
    
    public enum Mode {
        host, docker
    }
    
    public enum ServiceType {
        SHENYU_ADMIN,
        SHENYU_GATEWAY,
        EXTERNAL_SERVICE,
    }
    
    @Getter
    @AllArgsConstructor
    public static class HostConfigure {
        private final HostServiceConfigure admin;
        private final HostServiceConfigure gateway;
        private final List<HostServiceConfigure> externalServices;
        
        @Getter
        @AllArgsConstructor
        public static class HostServiceConfigure {
            private final String serviceName;
            private final String baseUrl;
            private final Properties properties;
        }
    }
    
    @Getter
    @AllArgsConstructor
    public static class DockerConfigure {
        private final String dockerComposeFile;
        private final DockerServiceConfigure admin;
        private final DockerServiceConfigure gateway;
        private final List<DockerServiceConfigure> externalServices;
        
        @Getter
        @AllArgsConstructor
        public static class DockerServiceConfigure {
            private final String schema;
            private final String serviceName;
            private final int port;
            private final Properties properties;
        }
    }

//    @SneakyThrows
//    public static ShenYuEngineConfigure loadConfigure(String configureFile) {
//        File file = new File(configureFile);
//        FileInputStream stream = new FileInputStream(file);
//        Yaml yaml = new Yaml(new Constructor(ShenYuEngineConfigure.class));
//        return yaml.load(stream);
//    }
    
    // fixme
    public static ShenYuEngineConfigure fromAnnotation(ShenYuTest annotation) {
        ShenYuEngineConfigure configure = new ShenYuEngineConfigure();
        configure.mode = annotation.mode();
        if (Mode.docker == annotation.mode()) {
            configure.dockerConfigure = parseDockerServiceConfigures(annotation.dockerComposeFile(), annotation.services());
        } else {
            configure.hostConfigure = parseHostServiceConfigures(annotation.services());
        }
        return configure;
    }
    
    static HostConfigure parseHostServiceConfigures(ServiceConfigure[] serviceConfigures) {
        HostServiceConfigure admin = null;
        HostServiceConfigure gateway = null;
        List<HostServiceConfigure> services = new ArrayList<>();
        for (ServiceConfigure configure : serviceConfigures) {
            switch (configure.type()) {
                case SHENYU_ADMIN:
                    admin = parseHostServiceConfigure(configure);
                    break;
                case SHENYU_GATEWAY:
                    gateway = parseHostServiceConfigure(configure);
                    break;
                default:
                    services.add(parseHostServiceConfigure(configure));
            }
        }
        return new HostConfigure(admin, gateway, services);
    }
    
    
    static HostServiceConfigure parseHostServiceConfigure(ServiceConfigure configure) {
        Preconditions.checkNotNull(configure.serviceName(), "serviceName is non-nullable");
        Preconditions.checkNotNull(configure.baseUrl(), "baseUrl is non-nullable in docker-compose mode");
        
        return new HostServiceConfigure(configure.serviceName(), configure.baseUrl(), toProperties(configure.parameters()));
    }
    
    static DockerConfigure parseDockerServiceConfigures(String dockerComposeFile, ServiceConfigure[] serviceConfigures) {
        Preconditions.checkNotNull(Strings.emptyToNull(dockerComposeFile), "dockerComposeFile is required in docker-compose mode");
        
        DockerServiceConfigure admin = null;
        DockerServiceConfigure gateway = null;
        List<DockerServiceConfigure> services = new ArrayList<>();
        for (ServiceConfigure configure : serviceConfigures) {
            switch (configure.type()) {
                case SHENYU_ADMIN:
                    admin = parseDockerServiceConfigure(configure);
                    break;
                case SHENYU_GATEWAY:
                    gateway = parseDockerServiceConfigure(configure);
                    break;
                default:
                    services.add(parseDockerServiceConfigure(configure));
            }
        }
        return new DockerConfigure(dockerComposeFile, admin, gateway, services);
    }
    
    static DockerServiceConfigure parseDockerServiceConfigure(ServiceConfigure configure) {
        Preconditions.checkNotNull(configure.serviceName(), "serviceName is non-nullable");
        
        return new DockerServiceConfigure(configure.schema(), configure.serviceName(), configure.port(), toProperties(configure.parameters()));
    }
    
    static Properties toProperties(Parameter[] parameters) {
        Properties properties = new Properties();
        Arrays.stream(parameters).forEach(p -> properties.put(p.key(), p.value()));
        return properties;
    }
}
