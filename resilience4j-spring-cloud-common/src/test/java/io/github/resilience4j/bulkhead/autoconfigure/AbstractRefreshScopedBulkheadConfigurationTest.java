/*
 * Copyright 2019 Ingyu Hwang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.resilience4j.bulkhead.autoconfigure;

import io.github.resilience4j.bulkhead.configure.BulkheadConfigurationProperties;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.common.bulkhead.configuration.ThreadPoolBulkheadConfigurationProperties;
import io.github.resilience4j.consumer.DefaultEventConsumerRegistry;
import io.github.resilience4j.core.registry.CompositeRegistryEventConsumer;
import org.junit.Test;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractRefreshScopedBulkheadConfigurationTest {

    @Test
    public void shouldHaveRefreshScopeAnnotation() {
        Arrays.stream(AbstractRefreshScopedBulkheadConfiguration.class.getMethods())
            .filter(method -> method.isAnnotationPresent(Bean.class))
            .forEach(method -> assertThat(method.isAnnotationPresent(RefreshScope.class)).isTrue());
    }

    @Test
    public void testBulkheadCloudCommonConfig() {
        BulkheadConfig bulkheadConfig = new BulkheadConfig();

        assertThat(bulkheadConfig.bulkheadRegistry(
            new BulkheadConfigurationProperties(), new DefaultEventConsumerRegistry<>(),
            new CompositeRegistryEventConsumer<>(emptyList()),
            new CompositeCustomizer<>(Collections.emptyList()))).isNotNull();

        assertThat(bulkheadConfig.threadPoolBulkheadRegistry(
            new ThreadPoolBulkheadConfigurationProperties(), new DefaultEventConsumerRegistry<>(),
            new CompositeRegistryEventConsumer<>(emptyList()),
            new CompositeCustomizer<>(Collections.emptyList()))).isNotNull();
    }

    static class BulkheadConfig extends AbstractRefreshScopedBulkheadConfiguration {

    }
}