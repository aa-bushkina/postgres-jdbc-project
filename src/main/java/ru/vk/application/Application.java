package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.utils.DBProperties;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.sql.Date;
import java.sql.*;
import java.util.*;

public class Application
{
  @NotNull
  final private DBProperties DBProperties;

  @NotNull
  final private FlywayInitializer initializer;

  @Inject
  public Application(@NotNull final DBProperties DBProperties, @NotNull FlywayInitializer initializer)
  {
    this.DBProperties = DBProperties;
    this.initializer = initializer;
  }

  public void makeDB(@NotNull final String path)
  {
    initializer.initDB(path);
  }

  public void cleanDB()
  {
    initializer.cleanDB();
  }

  private Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
      DBProperties.connection() + DBProperties.name(),
      DBProperties.username(),
      DBProperties.password());
  }

  public Map<Organization, Integer> getTop10OrganizationsByQuantity()
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

    try (var connection = getConnection())
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
    return new LinkedHashMap<>();
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

    try (var connection = getConnection())
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
    return new LinkedHashMap<>();
  }

  public Set<ProductInfo> getEverydayProductCharacteristics()
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

    try (var connection = getConnection())
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final Date startDate = Date.valueOf("2022-11-03");
        final Date endDate = Date.valueOf("2022-11-05");
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
    return new LinkedHashSet<>();
  }

  public Map<Product, Double> getAverageOfProductPrice()
  {
    final @NotNull String SELECT_SQL = """
      select products.id, products.name, products.internal_code, avg(cast(price as numeric)) as avg from positions join invoices_positions
      on positions.id = invoices_positions.position_id
      join invoices
      on invoices.id = invoices_positions.invoice_id
      join products
      on products.id = positions.product_id
      where date >= ? and date <= ?
      group by products.id, products.name
      order by products.name""";

    try (var connection = getConnection())
    {
      try (var statement = connection.prepareStatement(SELECT_SQL))
      {
        final Date startDate = Date.valueOf("2022-11-01");
        final Date endDate = Date.valueOf("2022-11-06");
        statement.setDate(1, startDate);
        statement.setDate(2, endDate);
        try (var resultSet = statement.executeQuery())
        {
          LinkedHashMap<Product, Double> map = new LinkedHashMap<>();
          while (resultSet.next())
          {
            map.put(new Product(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("internal_code")), resultSet.getDouble("avg"));
          }
          return map;
        }
      }
    } catch (SQLException exception)
    {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<Organization, List<Product>> getProductsListByOrganizations()
  {
    final @NotNull String SELECT_SQL = """
      select organizations.id as org_id, organizations.name as org_name, organizations.inn,
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

    try (var connection = getConnection())
    {
      try (var statement =
             connection.prepareStatement(SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
               ResultSet.CONCUR_UPDATABLE))
      {
        final Date startDate = Date.valueOf("2022-11-05");
        final Date endDate = Date.valueOf("2022-11-09");
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
                resultSet.getString("name"),
                resultSet.getString("internal_code")));
              if (!resultSet.isLast())
              {
                continue;
              }
            }
            resultSet.previous();
            map.put(new Organization(
                resultSet.getInt("org_id"),
                resultSet.getString("org_name"),
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
    return new LinkedHashMap<>();
  }
}
