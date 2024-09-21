package db;

import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class IntroToJDBC {

    String url = "jdbc:postgresql://18.159.52.24:5434/postgres";
    String username = "cashwiseuser";
    String password = "cashwisepass";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    @Test
    public void test() throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();
        String query = "select * from clients";
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            if(resultSet.getInt("company_name_id") == 22){
                Assert.assertTrue(true);
                return;
            }
        }
        Assert.assertFalse("Company name id = 22 does not exist ", false);

    }
    @Test
    public void getCompany() throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();
        String query = "select company_name_id from clients where company_name = 'Macarons'";
        resultSet = statement.executeQuery(query);
        int id = 0;
        while (resultSet.next()){
            id = resultSet.getInt("company_name_id");
        }
        System.out.println(id);
        Assert.assertEquals(282,id);
    }

    @Test
    public void getSum() throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();
        String query = " select ";
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://18.159.52.24:5434/postgres";
        String username = "cashwiseuser";
        String password = "cashwisepass";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();

        String query = "select client_id from clients";
        resultSet = statement.executeQuery(query);

        //System.out.println(resultSet);
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }


    }







}
