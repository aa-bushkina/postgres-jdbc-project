package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;

public final class Invoice
{
  public final int id;
  @NotNull
  public final String num;
  @NotNull
  public final Date date;
  @NotNull
  public final Organization organization;


  public Invoice(final int id, @NotNull final String num, @NotNull final Date date, @NotNull final Organization organization)
  {
    this.id = id;
    this.num = num;
    this.date = date;
    this.organization = organization;
  }

  @Override
  public @NotNull String toString()
  {
    return "Invoice{" +
      "id=" + id +
      "number=" + num +
      ", date='" + date +
      ", sender organization='" + organization +
      '}';
  }
}