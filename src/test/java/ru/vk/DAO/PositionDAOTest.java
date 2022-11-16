package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;

class PositionDAOTest extends AbstractTest
{
  @Inject
  @NotNull
  @Named("Position")
  private Dao positionDAO;

  @Test
  @DisplayName("Получение позиции накладной из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех позиции накладных в БД")
  void all()
  {
  }

  @Test
  @DisplayName("Добавление новой позиции накладной в БД")
  void save()
  {
  }

  @Test
  @DisplayName("Обновление данных позиции накладной из БД")
  void update()
  {
  }

  @Test
  @DisplayName("Удаление позиции накладной из БД")
  void delete()
  {
  }
}