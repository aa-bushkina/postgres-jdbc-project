package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.InvoiceDAO;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Invoice;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

  public Map<Organization, Integer> getTop10OrganizationByQuantity()
  {
    final @NotNull String SELECT_SQL = """
      select organizations.id, organizations.name, organizations.inn,
      organizations.payment_account, sum(positions.quantity)  as quantity
      from organizations join invoices
      on invoices.organization_id=organizations.id
      left join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      left join positions
      on invoices_positions.position_id = positions.id
      group by organizations.name,  organizations.id, organizations.inn, organizations.payment_account
      order by quantity desc
      limit ?""";

    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final int limitValue = 10;
        statement.setInt(1, limitValue);
        try (var resultSet = statement.executeQuery())
        {
          LinkedHashMap<Organization, Integer> map = new LinkedHashMap<>();
          while (resultSet.next())
          {
            map.put(new Organization(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("inn"),
                resultSet.getString("payment_account")),
              resultSet.getInt("quantity"));
          }
          return map;
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return null;
  }

  public Map<Organization, Integer> getOrganizationsWithDefiniteQuantity()
  {
    final @NotNull String SELECT_SQL = """
      select organizations.id, organizations.name, organizations.inn,
      organizations.payment_account, sum(positions.quantity)  as quantity
      from organizations join invoices
      on invoices.organization_id=organizations.id
      left join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      left join positions
      on invoices_positions.position_id = positions.id
      group by organizations.name,  organizations.id, organizations.inn, organizations.payment_account
      having  sum(positions.quantity)>?
      order by quantity desc""";

    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final int quantityValue = 9000;
        statement.setInt(1, quantityValue);
        try (var resultSet = statement.executeQuery())
        {
          LinkedHashMap<Organization, Integer> map = new LinkedHashMap<>();
          while (resultSet.next())
          {
            map.put(new Organization(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("inn"),
                resultSet.getString("payment_account")),
              resultSet.getInt("quantity"));
          }
          return map;
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return null;
  }

  public LinkedHashSet<ProductInfo> getEverydayProductCharacteristics()
  {
    final @NotNull String SELECT_SQL = """
      select date, products.id, products.name, products.internal_code,
      sum(quantity) as quantity, sum(quantity*price)::numeric as sum from organizations
      left join invoices
      on invoices.organization_id=organizations.id
      join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      join positions
      on invoices_positions.position_id = positions.id
      join products
      on positions.product_id = products.id
      where date >= ? and date <= ?
      group by  date, products.id, products.name
      order by products.name""";

    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final Date startDate = Date.valueOf("2022-11-01");
        final Date endDate = Date.valueOf("2022-11-08");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);

        try (var resultSet = statement.executeQuery())
        {
          LinkedHashSet<ProductInfo> set = new LinkedHashSet<>();
          while (resultSet.next())
          {
            ProductInfo info = new ProductInfo(
              resultSet.getDate("date"),
              new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("internal_code")),
              resultSet.getInt("quantity"),
              resultSet.getBigDecimal("sum"));
            set.add(info);
          }
          return set;
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return null;
  }

  public double getAverageOfProductPrice()
  {
    final @NotNull String SELECT_SQL = """
      select avg(cast(price as numeric)) as avg from positions join invoices_positions
      on positions.id = invoices_positions.position_id
      join invoices
      on invoices.id = invoices_positions.invoice_id
      where date >= ? and date <= ?""";

    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final Date startDate = Date.valueOf("2022-11-01");
        final Date endDate = Date.valueOf("2022-11-06");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        try (var resultSet = statement.executeQuery())
        {
          if (resultSet.next())
          {
            return resultSet.getDouble("avg");
          }
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return 0;
  }

  public Map<Organization, List<Product>> getProductsListByOrganizations()
  {
    final @NotNull String SELECT_SQL = """
      select organizations.id as org_id, organizations.name, organizations.inn,
      organizations.payment_account, products.id as pr_id, products.name, products.internal_code
      from organizations left join invoices
      on invoices.organization_id=organizations.id
      join invoices_positions
      on invoices_positions.invoice_id = invoices.id
      join positions
      on invoices_positions.position_id = positions.id
      join products
      on positions.product_id = products.id
      where date >= ? and date <= ?""";

    try (var connection = DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password()))
    {
      try (var statement =
             connection.prepareStatement(SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
               ResultSet.CONCUR_UPDATABLE))
      {
        final Date startDate = Date.valueOf("2022-11-01");
        final Date endDate = Date.valueOf("2022-11-06");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        try (var resultSet = statement.executeQuery())
        {
          ArrayList<Product> productsList = new ArrayList<>();
          Map<Organization, List<Product>> map = new LinkedHashMap<>();
          int currentId = 0;
          int organizationId;
          boolean isFirstRow = true;
          while (resultSet.next())
          {
            organizationId = resultSet.getInt("org_id");
            if (isFirstRow)
            {
              isFirstRow = false;
              currentId = organizationId;
            }
            if (organizationId == currentId)
            {
              productsList.add(new Product(
                resultSet.getInt("pr_id"),
                resultSet.getString("internal_code"),
                resultSet.getString("name")));
              if (!resultSet.isLast())
              {
                continue;
              }
            }
            resultSet.previous();
            map.put(new Organization(
                resultSet.getInt("org_id"),
                resultSet.getString("name"),
                resultSet.getString("inn"),
                resultSet.getString("payment_account")),
              new ArrayList<>(productsList));
            productsList.clear();
            currentId = organizationId;
            resultSet.next();
            if (!resultSet.isLast())
            {
              resultSet.previous();
            }
          }
          return map;
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return null;
  }
}
