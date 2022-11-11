package ru.vk.DAO;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import ru.vk.application.ApplicationModule;

public abstract class AbstractDAOTest
{
  @NotNull
  final String[] args = {"jdbc:postgresql://localhost/", "task1", "postgres", "postgres"};

  @BeforeEach
  public void beforeEach()
  {
    Injector injector = Guice.createInjector(new ApplicationModule(args));
    injector.injectMembers(this);
  }
}
