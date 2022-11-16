package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

class OrganizationDAOTest extends AbstractTest
{
  @Inject
  @NotNull
  private OrganizationDAO organizationDAO;
  @Test
  @DisplayName("Получение организации из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех организаций в БД")
  void all()
  {
  }

  @Test
  @DisplayName("Добавление новой организации в БД")
  void save()
  {
  }

  @Test
  @DisplayName("Обновление данных организации из БД")
  void update()
  {
  }

  @Test
  @DisplayName("Удаление организации из БД")
  void delete()
  {
  }
}