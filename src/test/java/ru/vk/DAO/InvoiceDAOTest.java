package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;

class InvoiceDAOTest extends AbstractTest
{
  @Inject
  @NotNull
  @Named("Invoice")
  private Dao invoiceDAO;

  @Test
  @DisplayName("Получение накладной из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех накладных в БД")
  void all()
  {
  }

  @Test
  @DisplayName("Добавление новой накладной в БД")
  void save()
  {

  }

  @Test
  @DisplayName("Обновление данных накладной из БД")
  void update()
  {
  }

  @Test
  @DisplayName("Удаление накладной из БД")
  void delete()
  {
  }
}