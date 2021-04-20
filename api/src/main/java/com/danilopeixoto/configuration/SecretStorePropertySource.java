package com.danilopeixoto.configuration;

import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SecretStorePropertySource extends PropertySource<SecretStorePropertySource> {
  public SecretStorePropertySource() {
    super( "SecretStorePropertySource");
  }

  @Override
  public Object getProperty(String name) {
    if (name.startsWith("secrets")) {
      try {
        String variableName = name.substring(name.indexOf('.') + 1);
        return Files.readString(Paths.get(System.getenv(variableName))).strip();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return null;
  }
}
