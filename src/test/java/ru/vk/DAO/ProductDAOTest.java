package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductDAOTest extends AbstractDAOTest
{
  @Inject
  @NotNull
  private ProductDAO productDAO;
  @Test
  @DisplayName("Получение товара из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех товаров в БД")
  void all()
  {
  }

  @Test
  @DisplayName("Добавление нового товара в БД")
  void save()
  {
  }

  @Test
  @DisplayName("Обновление данных товара из БД")
  void update()
  {
  }

  @Test
  @DisplayName("Удаление товара из БД")
  void delete()
  {
  }
}