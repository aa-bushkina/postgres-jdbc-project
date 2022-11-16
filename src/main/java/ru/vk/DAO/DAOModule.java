package ru.vk.DAO;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOModule extends AbstractModule
{
  @NotNull
  final String[] args;

  public DAOModule(@NotNull final String[] args)
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
    try
    {
      var connection = DriverManager.getConnection(args[0] + args[1], args[2], args[3]);
      bind(Dao.class).annotatedWith(Names.named("Invoice")).toInstance(new InvoiceDAO(connection));
      bind(Dao.class).annotatedWith(Names.named("Organization")).toInstance(new OrganizationDAO(connection));
      bind(Dao.class).annotatedWith(Names.named("Position")).toInstance(new PositionDAO(connection));
      bind(Dao.class).annotatedWith(Names.named("Product")).toInstance(new ProductDAO(connection));
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
  }

  private boolean checkArgs(@NotNull final String[] args)
  {
    return (args.length == 4) && args[0].contains("jdbc:postgresql://");
  }
}
