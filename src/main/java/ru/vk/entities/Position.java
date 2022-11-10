package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

public final class Position
{
  public final int id;
  public final double price;
  public final int product_id;
  public final int quantity;

  public Position(final int id, final double price, final int product_id, final int quantity)
  {
    this.id = id;
    this.price = price;
    this.product_id = product_id;
    this.quantity = quantity;
  }

  @Override
  public @NotNull String toString()
  {
    return "Position{" +
      "id=" + id +
      "price=" + price +
      "product_id=" + product_id +
      ", quantity='" + quantity +
      '}';
  }
}