package cn.ifengkou.db.timeseries;

import org.junit.Test;

import java.sql.*;

/**
 * com.github.housepower.jdbc.ClickHouseDriver 驱动测试
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2019/7/19
 */
public class ClickhouseUtilTest {

    @Test
    public void testLogEngine() throws ClassNotFoundException, SQLException {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://212.64.15.210:9000","default","sloong123");
        Statement stmt = connection.createStatement();
        stmt.executeQuery("drop table if exists test_jdbc_example");
        stmt.executeQuery("create table test_jdbc_example(day DateTime, name String, age UInt8) Engine=Log");

        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO test_jdbc_example VALUES(?, ?, ?)");

        for (int i = 0; i < 200; i++) {
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(2, "Zhang San" + i);
            pstmt.setByte(3, (byte) i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();


        ResultSet rs = stmt.executeQuery("SELECT day,name,age FROM test_jdbc_example limit 20");

        while (rs.next()) {
            System.out.println(rs.getTimestamp(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
        }

        //stmt.executeQuery("drop table test_jdbc_example");

        rs.close();
        pstmt.close();
        stmt.close();
        connection.close();
    }

    @Test
    public void testMergeTreeEngine() throws ClassNotFoundException, SQLException {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://212.64.15.210:9000");

        Statement stmt = connection.createStatement();
        stmt.executeQuery("drop table if exists test_mergeTree_example");
        stmt.executeQuery("create table test_mergeTree_example(day Date, name String, age UInt8) Engine=MergeTree PARTITION BY toYYYYMM(day) ORDER BY (age)");

        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO test_mergeTree_example VALUES(?, ?, ?)");

        for (int i = 0; i < 2000; i++) {
            pstmt.setDate(1, new Date(System.currentTimeMillis()));
            pstmt.setString(2, "Zhang San" + i);
            pstmt.setByte(3, (byte) i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();


        ResultSet rs = stmt.executeQuery("SELECT day,name,age FROM test_mergeTree_example limit 20");

        while (rs.next()) {
            System.out.println(rs.getDate(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));
        }

        //stmt.executeQuery("drop table test_jdbc_example");

        rs.close();
        pstmt.close();
        stmt.close();
        connection.close();
    }

    @Test
    public void testMergeTreePartitionByStrEngine() throws ClassNotFoundException, SQLException {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://212.64.15.210:9000");

        Statement stmt = connection.createStatement();
        stmt.executeQuery("drop table if exists test_str_partition_example");
        stmt.executeQuery("create table test_str_partition_example(day Date, name String,sex String, age UInt32) Engine=MergeTree PARTITION BY (sex) ORDER BY (age)");

        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO test_str_partition_example VALUES(?, ?, ?,?)");

        for (int i = 0; i < 2000; i++) {
            pstmt.setDate(1, new Date(System.currentTimeMillis()));
            pstmt.setString(2, "Zhang San" + i);
            String x = "man";
            if(i < 1000) {
                x = "female";
            }
            pstmt.setString(3, x);
            pstmt.setInt(4, i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();


        ResultSet rs = stmt.executeQuery("SELECT day,name,sex,age FROM test_str_partition_example limit 20");
        while (rs.next()) {
            System.out.println(rs.getDate(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3) + "\t" + rs.getInt(4));
        }

        //stmt.executeQuery("drop table test_jdbc_example");

        rs.close();
        pstmt.close();
        stmt.close();
        connection.close();

        //Assert.assertEquals(2000,rs.getFetchSize());
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://212.64.15.210:9000");

        //ALTER TABLE [db.]table UPDATE column1 = expr1 [, ...] WHERE filter_expr
        String sql = "ALTER TABLE test_str_partition_example UPDATE name='john' where age>25";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.execute();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT day,name,sex,age FROM test_str_partition_example where age=26 limit 20");
        while (rs.next()) {
            System.out.println(rs.getDate(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3) + "\t" + rs.getInt(4));
        }

        //stmt.executeQuery("drop table test_jdbc_example");

        rs.close();
        pstmt.close();
        stmt.close();
        connection.close();

        //Assert.assertEquals(2000,rs.getFetchSize());
    }

    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://212.64.15.210:9000");

        //ALTER TABLE [db.]table UPDATE column1 = expr1 [, ...] WHERE filter_expr
        String sql = "ALTER TABLE test_str_partition_example DELETE where age>30 or age <10";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.execute();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT day,name,sex,age FROM test_str_partition_example limit 20");
        while (rs.next()) {
            System.out.println(rs.getDate(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3) + "\t" + rs.getInt(4));
        }

        //stmt.executeQuery("drop table test_jdbc_example");

        rs.close();
        pstmt.close();
        stmt.close();
        connection.close();

        //Assert.assertEquals(2000,rs.getFetchSize());
    }
}
