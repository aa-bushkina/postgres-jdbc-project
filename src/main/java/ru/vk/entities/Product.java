package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

public final class Product
{
  public final int id;
  public final int internalCode;
  @NotNull
  public final String name;

  public Product(final int id, final int internalCode, @NotNull final String name)
  {
    this.id = id;
    this.internalCode = internalCode;
    this.name = name;
  }

  @Override
  public @NotNull String toString()
  {
    return "Product{" +
      "id=" + id +
      "internal code=" + internalCode +
      ", name='" + name +
      '}';
  }
}