package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

public final class Position
{
  public final int id;
  public final double price;
  @NotNull
  public final Product product;
  public final int quantity;

  public Position(int id, double price, @NotNull Product product, int quantity)
  {
    this.id = id;
    this.price = price;
    this.product = product;
    this.quantity = quantity;
  }

  @Override
  public @NotNull String toString()
  {
    return "Position{" +
      "id=" + id +
      "price=" + price +
      "product=" + product +
      ", quantity='" + quantity +
      '}';
  }
}