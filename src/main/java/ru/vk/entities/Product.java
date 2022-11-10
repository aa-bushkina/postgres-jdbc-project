package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

public final class Product
{
  public final int id;
  @NotNull
  public final String internalCode;
  @NotNull
  public final String name;

  public Product(final int id, @NotNull final String internalCode, @NotNull final String name)
  {
    this.id = id;
    this.internalCode = internalCode;
    this.name = name;
  }

  @Override
  public @NotNull String toString()
  {
    return "\nProduct{" +
      "\nid=" + id +
      ",\ninternal code=" + internalCode +
      ",\nname='" + name +
      "}\n";
  }
}