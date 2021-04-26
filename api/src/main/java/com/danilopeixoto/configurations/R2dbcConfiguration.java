package com.danilopeixoto.configurations;

import com.danilopeixoto.converters.JsonNodeReadingConverter;
import com.danilopeixoto.converters.JsonNodeWritingConverter;
import com.danilopeixoto.converters.RuntimeTypeWritingConverter;
import com.danilopeixoto.converters.StatusTypeWritingConverter;
import com.danilopeixoto.models.RuntimeType;
import com.danilopeixoto.models.StatusType;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {
  @Bean
  @Override
  public ConnectionFactory connectionFactory() {
    return new PostgresqlConnectionFactory(
      PostgresqlConnectionConfiguration.builder()
        .codecRegistrar(
          EnumCodec
            .builder()
            .withEnum("runtime_type", RuntimeType.class)
            .withEnum("status_type", StatusType.class)
            .build())
        .build());
  }

  @Bean
  @Override
  public R2dbcCustomConversions r2dbcCustomConversions() {
    return R2dbcCustomConversions.of(
      PostgresDialect.INSTANCE,
      new RuntimeTypeWritingConverter(),
      new StatusTypeWritingConverter(),
      new JsonNodeWritingConverter(),
      new JsonNodeReadingConverter());
  }

  @Bean
  public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
    return new R2dbcTransactionManager(connectionFactory);
  }

  @Bean
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(populator);

    return initializer;
  }
}
