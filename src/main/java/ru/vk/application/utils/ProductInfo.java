package ru.vk.application.utils;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.util.Date;

public record ProductInfo(@NotNull Date date,
                          @NotNull Product product,
                          int quantity,
                          BigDecimal sum)
{
}
