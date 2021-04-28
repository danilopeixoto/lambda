package com.danilopeixoto.api.configuration;

import com.danilopeixoto.api.converter.JsonNodeReadingConverter;
import com.danilopeixoto.api.converter.JsonNodeWritingConverter;
import com.danilopeixoto.api.converter.RuntimeTypeWritingConverter;
import com.danilopeixoto.api.converter.StatusTypeWritingConverter;
import com.danilopeixoto.api.model.RuntimeType;
import com.danilopeixoto.api.model.StatusType;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
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
    R2dbcProperties properties = this.r2dbcProperties();
    ConnectionFactoryOptions options = ConnectionFactoryOptions.parse(properties.getUrl());

    return new PostgresqlConnectionFactory(
      PostgresqlConnectionConfiguration.builder()
        .host(options.getRequiredValue(ConnectionFactoryOptions.HOST))
        .port(options.getRequiredValue(ConnectionFactoryOptions.PORT))
        .database(options.getRequiredValue(ConnectionFactoryOptions.DATABASE))
        .username(properties.getUsername())
        .password(properties.getPassword())
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
  public R2dbcProperties r2dbcProperties() {
    return new R2dbcProperties();
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
    populator.setSeparator(";;");

    initializer.setDatabasePopulator(populator);

    return initializer;
  }
}
