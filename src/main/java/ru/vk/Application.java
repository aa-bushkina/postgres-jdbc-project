package ru.vk;

import com.google.inject.Inject;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public class Application
{
  @NotNull
  final private DBProperties DBProperties;

  @Inject
  public Application(@NotNull final DBProperties DBProperties)
  {
    this.DBProperties = DBProperties;
  }

  public void makeDB()
  {
    FlywayInitializer initializer = new FlywayInitializer(DBProperties);
    initializer.initDB();
  }


}
