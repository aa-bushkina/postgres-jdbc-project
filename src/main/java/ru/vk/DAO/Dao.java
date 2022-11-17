package ru.vk.DAO;

import org.jetbrains.annotations.NotNull;
import ru.vk.application.utils.DBProperties;

import java.util.List;

public interface Dao<T>
{
  @NotNull T get(int id);

  @NotNull List<@NotNull T> all();

  void save(@NotNull T entity);

  void update(@NotNull T entity);

  void delete(@NotNull T entity);
}
