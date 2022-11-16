package ru.vk.DAO;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class ProductDAO implements Dao<Product>
{
  private final @NotNull Connection connection;

  public ProductDAO(@NotNull Connection connection)
  {
    this.connection = connection;
  }

  @Override
  public @NotNull Product get(int id)
  {
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement
        .executeQuery("SELECT id, name, internal_code,  FROM products WHERE id = ?" + id))
      {
        if (resultSet.next())
        {
          return new Product(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("internal_code"));
        }
      }
    } catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Product> all()
  {
    final var result = new ArrayList<Product>();
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement.executeQuery("SELECT * FROM products"))
      {
        while (resultSet.next())
        {
          result.add(new Product(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("internal_code")));
        }
        return result;
      }
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void save(@NotNull Product entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("INSERT INTO products(name, internal_code) VALUES(?,?)"))
    {
      preparedStatement.setString(1, entity.name);
      preparedStatement.setString(2, entity.internalCode);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull Product entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("UPDATE products SET name = ?, internalCode = ? WHERE id = ?"))
    {
      preparedStatement.setString(1, entity.name);
      preparedStatement.setString(2, entity.internalCode);
      preparedStatement.executeUpdate();
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull Product entity)
  {
    try (var preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?"))
    {
      preparedStatement.setInt(1, entity.id);
      if (preparedStatement.executeUpdate() == 0)
      {
        throw new IllegalStateException("Record with id = " + entity.id + " not found");
      }
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
