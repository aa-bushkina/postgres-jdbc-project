package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.OrganizationDAO;
import ru.vk.DAO.ProductDAO;
import ru.vk.application.utils.DBProperties;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application
{
  @NotNull
  final private FlywayInitializer initializer;

  @NotNull
  final private OrganizationDAO organizationDAO;

  @NotNull
  final private ProductDAO productDAO;

  @Inject
  public Application( @NotNull FlywayInitializer initializer, @NotNull OrganizationDAO organizationDAO, @NotNull ProductDAO productDAO)
  {
    this.initializer = initializer;
    this.organizationDAO = organizationDAO;
    this.productDAO = productDAO;
  }

  public void makeDB(@NotNull final String path)
  {
    initializer.initDB(path);
  }

  public void cleanDB()
  {
    initializer.cleanDB();
  }

  public Map<Organization, Integer> getTop10OrganizationsByQuantity()
  {
    return organizationDAO.getTop10OrganizationsByQuantity();
  }

  public Map<Organization, Integer> getOrganizationsWithDefiniteQuantity()
  {
    return organizationDAO.getOrganizationsWithDefiniteQuantity();
  }

  public Map<Product, Double> getAverageOfProductPrice()
  {
    return productDAO.getAverageOfProductPrice();
  }

  public Set<ProductInfo> getEverydayProductCharacteristics()
  {
    return productDAO.getEverydayProductCharacteristics();
  }

  public Map<Organization, List<Product>> getProductsListByOrganizations()
  {
    return organizationDAO.getProductsListByOrganizations();
  }
}
