package ru.vk.DAO;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Organization;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class OrganizationDAO implements Dao<Organization>
{
  private final @NotNull Connection connection;

  public OrganizationDAO(@NotNull Connection connection)
  {
    this.connection = connection;
  }

  @Override
  public @NotNull Organization get(int id)
  {
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement
        .executeQuery("SELECT id, name, inn, payment_account FROM organizations WHERE id = ?" +  (Integer)id))
      {
        if (resultSet.next())
        {
          return new Organization(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("inn"),
            resultSet.getString("payment_account"));
        }
      }
    } catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + " not found");
  }

  @Override
  public @NotNull List<Organization> all()
  {
    final var result = new ArrayList<Organization>();
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement.executeQuery("SELECT * FROM organizations"))
      {
        while (resultSet.next())
        {
          result.add(new Organization(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("inn"),
            resultSet.getString("payment_account")));
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
  public void save(@NotNull Organization entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("INSERT INTO organizations(id, name, inn, payment_account) VALUES(?,?,?,?)"))
    {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.name);
      preparedStatement.setString(3, entity.inn);
      preparedStatement.setString(4, entity.paymentAccount);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull Organization entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("UPDATE organizations SET name = ?, inn = ?, payment_account = ? WHERE id = ?"))
    {
      preparedStatement.setString(1, entity.name);
      preparedStatement.setString(2, entity.inn);
      preparedStatement.setString(3, entity.paymentAccount);
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull Organization entity)
  {
    try (var preparedStatement = connection.prepareStatement("DELETE FROM organizations WHERE id = ?"))
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
