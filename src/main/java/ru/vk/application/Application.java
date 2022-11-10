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

  public void task1()
  {
    /*
     select organizations.name, sum(positions.quantity) as summ from organizations join invoices
     on invoices.organization_id=organizations.id
     left join invoices_positions
     on invoices_positions.invoice_id = invoices.id
     left join positions
     on invoices_positions.position_id = positions.id
     group by organizations.name
     order by summ desc
     limit 10
*/
  }

  public void task2()
  {
    /*
      select organizations.name, sum(positions.quantity) as summ from organizations join invoices
      on invoices.organization_id=organizations.id
      left join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      left join positions
      on invoices_positions.position_id = positions.id
      group by organizations.name
      having  sum(positions.quantity)>9000
      order by summ
*/
  }

  public void task3()
  {
    /*
      select date, sum(quantity), sum(quantity*price) from organizations left join invoices
      on invoices.organization_id=organizations.id
      join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      join positions
      on invoices_positions.position_id = positions.id
      join products
      on positions.product_id = products.id
      where date >= '2022-11-01' and date <= '2022-11-08'
      group by date
      order by date asc
     */
  }

  public void task4()
  {
    /*
      select avg(cast(price as numeric)) from positions join invoices_positions
      on positions.id = invoices_positions.position_id
      join invoices
      on invoices.id = invoices_positions.invoice_id
      where date >= '2022-11-01' and date <= '2022-11-06'
*/
  }

  public void task5()
  {
    /*
      select organizations.name, products.name from organizations left join invoices
      on invoices.organization_id=organizations.id
      join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      join positions
      on invoices_positions.position_id = positions.id
      join products
      on positions.product_id = products.id
*/
  }
}
