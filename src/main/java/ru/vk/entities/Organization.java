package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

public final class Organization
{
  public final int id;
  @NotNull
  public final String name;
  @NotNull
  public final String inn;
  @NotNull
  public final String paymentAccount;


  public Organization(final int id, @NotNull final String name, @NotNull final String inn, @NotNull final String paymentAccount)
  {
    this.id = id;
    this.name = name;
    this.inn = inn;
    this.paymentAccount = paymentAccount;
  }

  @Override
  public @NotNull String toString()
  {
    return "Organization{" +
      "id=" + id +
      ", name='" + name +
      ", inn='" + inn +
      ", payment account='" + paymentAccount +
      '}';
  }
}