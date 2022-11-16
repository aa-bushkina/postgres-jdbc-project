package ru.vk.application.utils;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public record ProductInfo(@NotNull Date date,
                          @NotNull Product product,
                          int quantity,
                          BigDecimal sum)
{
  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductInfo info = (ProductInfo) o;
    return quantity == info.quantity
      && date.equals(info.date)
      && product.equals(info.product)
      && sum.equals(info.sum);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(date, product, quantity, sum);
  }
}
