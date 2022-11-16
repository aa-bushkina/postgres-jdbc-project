package ru.vk.DAO;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Position;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class PositionDAO implements Dao<Position>
{
  private final @NotNull Connection connection;

  public PositionDAO(@NotNull Connection connection)
  {
    this.connection = connection;
  }

  @Override
  public @NotNull Position get(int id)
  {
    try
    {
      var preparedStatement = connection
        .prepareStatement("SELECT id, price, product_id, quantity FROM positions WHERE id = ?");
      {
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next())
        {
          return new Position(resultSet.getInt("id"),
            resultSet.getBigDecimal("price"),
            resultSet.getInt("product_id"),
            resultSet.getInt("quantity"));
        }
      }
    } catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Position> all()
  {
    final var result = new ArrayList<Position>();
    try (var statement = connection.createStatement())
    {
      try (var resultSet = statement.executeQuery("SELECT * FROM positions"))
      {
        while (resultSet.next())
        {
          result.add(new Position(resultSet.getInt("id"),
            resultSet.getBigDecimal("price").setScale(2, RoundingMode.CEILING),
            resultSet.getInt("product_id"),
            resultSet.getInt("quantity")));
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
  public void save(@NotNull Position entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("INSERT INTO positions(id, price, product_id, quantity) VALUES(?,?,?,?)"))
    {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setBigDecimal(2, entity.price);
      preparedStatement.setInt(3, entity.product_id);
      preparedStatement.setInt(4, entity.quantity);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull Position entity)
  {
    try (var preparedStatement = connection
      .prepareStatement("UPDATE positions SET price = ?, product_id = ?, quantity = ? WHERE id = ?"))
    {
      preparedStatement.setBigDecimal(1, entity.price);
      preparedStatement.setInt(2, entity.product_id);
      preparedStatement.setInt(3, entity.quantity);
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull Position entity)
  {
    try (var preparedStatement = connection.prepareStatement("DELETE FROM positions WHERE id = ?"))
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
