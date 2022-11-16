package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
  public boolean equals(Object obj)
  {
    final Product other = (Product) obj;
    return this.id == other.id
      && this.internalCode.equals(other.internalCode)
      && this.name.equals(other.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, internalCode, name);
  }

  @Override
  public @NotNull String toString()
  {
    return "\nProduct{" +
      "\nid='" + id +
      "',\ninternal code='" + internalCode +
      "',\nname='" + name +
      "'}\n";
  }
}