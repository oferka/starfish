package org.ok.starfish.data.repository.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

    private final ElasticsearchRestHighLevelClientConfiguration elasticsearchRestHighLevelClientConfiguration;

    public ElasticsearchConfiguration(@Autowired ElasticsearchRestHighLevelClientConfiguration elasticsearchRestHighLevelClientConfiguration) {
        this.elasticsearchRestHighLevelClientConfiguration = elasticsearchRestHighLevelClientConfiguration;
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchRestHighLevelClientConfiguration.getConnectTo())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
