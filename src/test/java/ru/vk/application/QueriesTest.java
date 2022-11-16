package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.entities.Organization;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class QueriesTest extends AbstractTest
{
  @Inject
  @NotNull
  private Application application;

  @Test
  void getTop10OrganizationsByQuantity()
  {
    final Map<Organization, Integer> map = new HashMap<>()
    {{
      put(new Organization(15, "organization15", "1023456811", "3434343434"), 356300);
      put(new Organization(3, "organization3", "7300450045", "3456789012"), 354800);
      put(new Organization(2, "organization2", "2500000021", "2345678901"), 349800);
      put(new Organization(10, "organization10", "1054407811", "3456384888"), 108774);
      put(new Organization(12, "organization12", "3567007811", "3456237474"), 103975);
      put(new Organization(6, "organization6", "1050343811", "4444444444"), 101333);
      put(new Organization(9, "organization9", "1052227811", "3749506837"), 10754);
      put(new Organization(1, "organization1", "2330123011", "1234567890"), 9000);
      put(new Organization(8, "organization8", "1050007811", "1452667778"), 7400);
      put(new Organization(5, "organization5", "1050337331", "5678901234"), 7000);
    }};

    assertThat(application.getTop10OrganizationsByQuantity(), equalTo(map));
  }

  @org.junit.jupiter.api.Test
  void getOrganizationsWithDefiniteQuantity()
  {
    application.getAverageOfProductPrice();
  }

  @org.junit.jupiter.api.Test
  void getEverydayProductCharacteristics()
  {
    application.getEverydayProductCharacteristics();
  }

  @org.junit.jupiter.api.Test
  void getAverageOfProductPrice()
  {
    application.getAverageOfProductPrice();
  }

  @org.junit.jupiter.api.Test
  void getProductsListByOrganizations()
  {
    application.getProductsListByOrganizations();
  }
}