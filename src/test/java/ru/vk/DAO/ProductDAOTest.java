package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.entities.Invoice;
import ru.vk.entities.Product;

import javax.inject.Named;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ProductDAOTest extends AbstractTest
{
  @Inject
  @NotNull
  @Named("Product")
  private Dao productDAO;

  @Test
  @DisplayName("Получение товара из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех товаров в БД")
  void all()
  {
    List<Product> list = List.of(
      new Product(1, "product1", "0000000001"),
      new Product(2, "product2", "0000000002"),
      new Product(3, "product3", "1000000001"),
      new Product(4, "product4", "1000000011"),
      new Product(5, "product5", "1a00000032"),
      new Product(6, "product6", "1a00000053"),
      new Product(7, "product7", "1a00dfg078"),
      new Product(8, "product8", "1a0eg00422"),
      new Product(9, "product9", "1a004t0032"),
      new Product(10, "product10", "1a00ddrs10"),
      new Product(11, "product11", "2d00000032"),
      new Product(12, "product12", "1a05302436"),
      new Product(13, "product13", "1113400324"),
      new Product(14, "product14", "1113324524"),
      new Product(15, "product15", "1a00fw4500"));
    assertThat(productDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление нового товара в БД")
  void save()
  {
    final Product product = new Product(16, "product16", "383jf9284f");
    productDAO.save(product);
    assertThat((List<Product>)productDAO.all(), hasItem(product));
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