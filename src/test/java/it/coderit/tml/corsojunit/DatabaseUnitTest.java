package it.coderit.tml.corsojunit;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
public class DatabaseUnitTest {

    @Autowired
    DataSource dataSource;
    Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        connection = dataSource.getConnection();
        System.out.println("Connection OK");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE persone(id long primary key , nome varchar(255) not null)");
        statement.close();
        statement = connection.createStatement();
        statement.execute("INSERT INTO persone(id, nome) VALUES (1, 'Mario')");
        statement.close();
        statement = connection.createStatement();
        statement.execute("INSERT INTO persone(id, nome) VALUES (2, 'Marco')");
        statement.close();
    }

    @AfterEach
    public void tearDown() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE persone");
        statement.close();
        connection.close();
    }

    @Test
    public void testConnection() throws Exception {
        Statement statement = connection.createStatement();
        boolean executed = statement.execute("select * from persone order by id asc");
        Assertions.assertTrue(executed);
        ResultSet resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        long dual = resultSet.getLong(1);
        Assertions.assertEquals(1, dual);
    }

    @Test
    public void testConnection2() throws Exception {
        Statement statement = connection.createStatement();
        boolean executed = statement.execute("select * from persone order by id asc");
        Assertions.assertTrue(executed);
        ResultSet resultSet = statement.getResultSet();
        Assertions.assertTrue(resultSet.next());
        long dual = resultSet.getLong(1);
        Assertions.assertEquals(1, dual);
    }
}
