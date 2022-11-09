package ru.vk.application;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;

public class ApplicationModule extends AbstractModule
{
  @NotNull
  final String[] args;

  public ApplicationModule(@NotNull final String[] args)
  {
    if (!checkArgs(args))
    {
      throw new RuntimeException("Incorrect args.");
    }
    this.args = args;
  }

  @Override
  protected void configure()
  {
    bind(DBProperties.class).toInstance(new DBProperties(args[0], args[1], args[2], args[3]));
  }

  private boolean checkArgs(@NotNull final String[] args)
  {
    return (args.length == 4) && args[0].contains("jdbc:postgresql://");
  }
}
