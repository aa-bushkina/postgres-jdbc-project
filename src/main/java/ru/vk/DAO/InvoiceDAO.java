package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Invoice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class InvoiceDAO implements Dao<Invoice>
{
  private final @NotNull Connection connection;

  @Inject
  public InvoiceDAO(@NotNull Connection connection)
  {
    this.connection = connection;
  }

  @Override
  public @NotNull Invoice get(int id)
  {
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement
        .executeQuery("SELECT id, num, date, organization_id FROM invoices WHERE id = ?" + id))
      {
        if (resultSet.next())
        {
          return new Invoice(resultSet.getInt("id"),
            resultSet.getString("num"),
            resultSet.getDate("date"),
            resultSet.getInt("organization_id"));
        }
      }
    } catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Invoice> all()
  {
    final var result = new ArrayList<Invoice>();
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement.executeQuery("SELECT * FROM invoices"))
      {
        while (resultSet.next())
        {
          result.add(new Invoice(resultSet.getInt("id"),
            resultSet.getString("num"),
            resultSet.getDate("date"),
            resultSet.getInt("organization_id")));
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
  public void save(@NotNull Invoice entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("INSERT INTO invoices(id, num, date, organization_id) VALUES(?,?,?,?)"))
    {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.num);
      preparedStatement.setDate(3, entity.date);
      preparedStatement.setInt(4, entity.organization_id);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull Invoice entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("UPDATE invoices SET num = ?, " +
        "date = ?, " +
        "organization_id = ? WHERE id = ?"))
    {
      preparedStatement.setString(1, entity.num);
      preparedStatement.setDate(2, entity.date);
      preparedStatement.setInt(3, entity.organization_id);
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull Invoice entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("DELETE FROM invoices WHERE id = ?"))
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
