package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vk.entities.Invoice;

import javax.sql.DataSource;
import java.sql.*;

import static org.mockito.Mockito.*;

class InvoiceDAOTest extends AbstractDAOTest
{
  /*@NotNull
  @Inject
  InvoiceDAO invoiceDAO;*/
  @Mock
  DataSource mockDataSource;
  @Mock
  Connection mockConn = ;
  @Mock
  PreparedStatement mockPreparedStmnt;
  @Mock
  ResultSet mockResultSet;

  private void setUp() throws SQLException
  {
    //when(mockDataSource.getConnection()).thenReturn(mockConn);
    when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
    doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
    doNothing().when(mockPreparedStmnt).setDate(anyInt(), Date.valueOf(anyString()));
    doNothing().when(mockPreparedStmnt).setInt(anyInt(), anyInt());
    doNothing().when(mockPreparedStmnt.executeUpdate());
  }

  @Test
  @DisplayName("Получение накладной из БД")
  void get()
  {
  }

  @Test
  @DisplayName("Просмотр всех накладных в БД")
  void all()
  {
  }

  @Test
  @DisplayName("Добавление новой накладной в БД")
  void save() throws SQLException
  {
    setUp();
    InvoiceDAO instance = new InvoiceDAO(mockConn);
    instance.save(new Invoice(100, "1212123434", Date.valueOf("2022-09-21"), 3));

    verify(mockConn, times(1)).prepareStatement(anyString());
    verify(mockPreparedStmnt, times(1)).setString(anyInt(), anyString());
    verify(mockPreparedStmnt, times(1)).setDate(anyInt(), Date.valueOf(anyString()));
    verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
    verify(mockPreparedStmnt, times(1)).executeUpdate();
  }

  @Test
  @DisplayName("Обновление данных накладной из БД")
  void update()
  {
  }

  @Test
  @DisplayName("Удаление накладной из БД")
  void delete()
  {
  }
}