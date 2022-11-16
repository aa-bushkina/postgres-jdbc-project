package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.application.Application;

class ProductDAOTest extends AbstractTest
{
  @Inject
  @NotNull
  private ProductDAO productDAO;

  @Inject
  @NotNull
  private Application application;

  @Inject
  @NotNull
  private String path = "db.productTest";
  @BeforeAll
  public void setUp()
  {
    application.makeDB(path);
  }

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