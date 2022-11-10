package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.InvoiceDAO;
import ru.vk.entities.Invoice;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application
{
  @NotNull
  final private DBProperties DBProperties;
  final private int DEFAULT_ID = 0;

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

  public void testInvoiceDAO()
  {
    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      final var dao = new InvoiceDAO(connection);

      //dao.save(new Invoice(DEFAULT_ID, "7765656565", Date.valueOf("2022-10-07"), 3));
      dao.all().forEach(System.out::println);
     dao.delete(new Invoice(8, "7765656565", Date.valueOf("2022-10-07"), 3));
      dao.update(new Invoice(6, "8899889988", Date.valueOf("2022-10-08"), 2));
      dao.all().forEach(System.out::println);
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
  }

}
