package ru.vk.DAO.utils;

import org.jetbrains.annotations.NotNull;

public record Queries()
{
  public static final @NotNull String AVG_OF_PRODUCT_PRICE_QUERY = """
    SELECT products.id, products.name, products.internal_code, avg(cast(price AS numeric)) AS avg FROM positions
    JOIN invoices_positions
    ON positions.id = invoices_positions.position_id
    JOIN invoices
    ON invoices.id = invoices_positions.invoice_id
    JOIN products
    ON products.id = positions.product_id
    WHERE date >= ? AND date <= ?
    GROUP BY products.id, products.name
    ORDER BY products.name""";

  public static final @NotNull String EVERYDAY_PRODUCT_CHARACTERISTICS_QUERY = """
    SELECT date, products.id, products.name, products.internal_code,
    SUM(quantity) AS quantity, SUM(quantity*price)::numeric AS sum FROM organizations
    LEFT JOIN invoices
    ON invoices.organization_id=organizations.id
    JOIN invoices_positions
    ON invoices_positions.invoice_id = invoices.id
    JOIN positions
    ON invoices_positions.position_id = positions.id
    JOIN products
    ON positions.product_id = products.id
    WHERE date >= ? AND date <= ?
    GROUP BY  date, products.id, products.name
    ORDER BY products.name""";

  public static final @NotNull String PRODUCT_LIST_BY_ORGANIZATION_QUERY = """
    SELECT organizations.id AS org_id, organizations.name AS org_name, organizations.inn,
    organizations.payment_account, products.id AS pr_id, products.name, products.internal_code
    FROM organizations LEFT JOIN invoices
    ON invoices.organization_id=organizations.id
    JOIN invoices_positions
    ON invoices_positions.invoice_id = invoices.id
    JOIN positions
    ON invoices_positions.position_id = positions.id
    JOIN products
    ON positions.product_id = products.id
    WHERE date >= ? AND date <= ?""";

  public static final @NotNull String ORGANIZATION_WITH_DEFINITE_QUANTITY_QUERY = """
    SELECT organizations.id, organizations.name, organizations.inn,
    organizations.payment_account, SUM(positions.quantity) AS quantity
    FROM organizations join invoices
    ON invoices.organization_id=organizations.id
    LEFT JOIN invoices_positions
    ON invoices_positions.invoice_id = invoices.id
    LEFT JOIN positions
    ON invoices_positions.position_id = positions.id
    GROUP BY organizations.name,  organizations.id, organizations.inn, organizations.payment_account
    HAVING  SUM(positions.quantity)>?
    ORDER BY quantity desc""";

  public static final @NotNull String ORGANIZATIONS_BY_QUANTITY_QUERY = """
      SELECT organizations.id, organizations.name, organizations.inn,
      organizations.payment_account, SUM(positions.quantity) AS quantity
      FROM organizations JOIN invoices
      ON invoices.organization_id=organizations.id
      LEFT JOIN invoices_positions
      ON invoices_positions.invoice_id = invoices.id
      LEFT JOIN positions
      ON invoices_positions.position_id = positions.id
      GROUP BY organizations.name,  organizations.id, organizations.inn, organizations.payment_account
      ORDER BY quantity DESC
      LIMIT ?""";
}
