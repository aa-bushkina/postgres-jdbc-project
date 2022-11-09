package ru.vk.application;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

public class FlywayInitializer
{
  @NotNull
  final private DBProperties DBProperties;

  public FlywayInitializer(@NotNull final DBProperties dbProperties)
  {
    DBProperties = dbProperties;
  }

  public void initDB()
  {
    final Flyway flyway = Flyway
      .configure()
      .dataSource(DBProperties.connection() + DBProperties.name(),
        DBProperties.username(),
        DBProperties.password())
      //.cleanDisabled(false)
      .locations("db")
      .load();
    //flyway.clean();
    flyway.migrate();
  }


}
