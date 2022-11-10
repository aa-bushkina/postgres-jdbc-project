package ru.vk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.Application;
import ru.vk.application.ApplicationModule;

public class Main
{
  public static void main(@NotNull String[] args)
  {
    final Injector injector = Guice.createInjector(new ApplicationModule(args));
    injector.getInstance(Application.class).makeDB();
    System.out.println("Выбрать первые 10 поставщиков по количеству поставленного товара\n");

    System.out.println(injector.getInstance(Application.class).getTop10OrganizationByQuantity());
    System.out.println("\nВыбрать поставщиков с количеством поставленного товара " +
      "выше указанного значения: 9000\n");

    System.out.println(injector.getInstance(Application.class).getOrganizationsWithDefiniteQuantity());
    System.out.println("\nЗа каждый день для каждого товара рассчитать количество и " +
      "сумму полученного товара в указанном периоде\n");

    System.out.println(injector.getInstance(Application.class).getEverydayProductCharacteristics());
    System.out.println("\nРассчитать среднюю цену полученного товара за период\n");

    System.out.println(injector.getInstance(Application.class).getAverageOfProductPrice());
    System.out.println("\nВывести список товаров, поставленных организациями за период\n");

    System.out.println(injector.getInstance(Application.class).getProductsListByOrganizations());
  }
}
