/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;



import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class QueryHelper {

    public static Object getFirstValue(String sql) {
        PreparedStatement stmt = null;
        Connection conn;
        try {
            
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Object o = rs.getObject(1);
            rs.close();
            stmt.close();
            conn.close();
            return o;
        } catch (SQLException e) {
            System.out.println("Query failed");
            return null;
        } 
    }

    public static PreparedStatement getPreparedStatement(String sqlStatement, Object... args) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn;
        conn = ConnectionHelper.getConnection();
        System.out.println("Getting stmt for " + sqlStatement);
        System.out.println("With values: " + Arrays.toString(args));
        stmt = conn.prepareStatement(sqlStatement);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Setting " + args[i] + ", type: " + args[i].getClass().getTypeName() + " to index: " + (i + 1));
            stmt.setObject(i + 1, args[i]);

        }

        return stmt;
    }

    public static ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
        ResultSet resultSet = null;
        resultSet = stmt.executeQuery();
        return resultSet;
    }

    public static ResultSet executeQuery(Statement stmt, String sql) throws SQLException {
        ResultSet resultSet = null;
        resultSet = stmt.executeQuery(sql);
        return resultSet;
    }

    public static ResultSet executeQuery(CallableStatement stmt, String sql) throws SQLException {
        ResultSet resultSet = null;
        resultSet = stmt.executeQuery(sql);

        return resultSet;
    }

    public static CallableStatement callProcedure(String procedureName, Object... args) throws SQLException {
        Connection conn = ConnectionHelper.getConnection();
        CallableStatement call = conn.prepareCall(procedureName);
//            String[] split = procedureName.split("");
        int counter = 1;
        if (args.length != 0 && procedureName.contains("?")) {
            for (int i = 0; i < procedureName.length(); i++) {
                if (procedureName.charAt(i) == '?') {
                    call.setObject(counter, args[counter - 1]);
                    counter++;
                }
            }
        }

        return call;
    }

    public static void deleteQuery(String table, String identifier, String identifierValue) {
        if (DialogHelper.confirm(null, "Tiếp tục? Xóa Voucher")) {
            String sqlDelete = "DELETE FROM " + table + " WHERE " + identifier + "= ?";
            int row = 0;
            try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sqlDelete, identifierValue);) {
                row = stmt.executeUpdate();
                if (row <= 0) {
                    DialogHelper.alert(null, "Xóa thất bại");
                } else {
                    DialogHelper.alert(null, "Xóa thành công");
                }
                ConnectionHelper.close(stmt.getConnection());
            } catch (SQLException e) {
                System.out.println("Xóa thất bại: " + e.getMessage());
                e.printStackTrace();
            } finally {
                StringBuilder log = new StringBuilder();
                log.append("[");
                log.append(DateHelper.getFullDateTime().toString());
                log.append("]: ");
                log.append("Performed (DELETE) Query on table (");
                log.append(table);
                log.append(")");
                log.append("by ID: (");
                log.append(SessionHelper.getCURRENT_ROLE());
                log.append("). ");
                log.append("Row(s) affected: ");
                log.append(row);
                loggingQuery(log.toString());
            }
        }
        return;
    }

    public static void deleteQuery(String table, String identifier, int identifierValue) {
        if (DialogHelper.confirm(null, "Tiếp tục? ,Xóa") ) {
            String sqlDelete = "DELETE FROM " + table + " WHERE " + identifier + "= ?";
        int row = 0;
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sqlDelete, identifierValue);) {
            row = stmt.executeUpdate();
            if (row <= 0) {
                DialogHelper.alert(null, "Xóa thất bại");
            } else {
                DialogHelper.alert(null, "Xóa thành công");
            }
            ConnectionHelper.close(stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("Xóa thất bại: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (DELETE) Query on table (");
            log.append(table);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(row);
            loggingQuery(log.toString());
        }
        }
        return;
    }

    public static void insertQuery(String tableName, Object... model) {
        if (DialogHelper.confirm(null, "Tiếp tục?, Thực hiện thêm mới")) {
            if (model.length == 0 || model == null) {
            System.out.println("Nothing to insert");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" VALUES(");
        for (int i = 0; i < model.length; i++) {
            sb.append("?");
            if (i == model.length - 1) {
                sb.append(")");
                break;
            }
            sb.append(",");
        }
        String sqlStatement = sb.toString();
        int row = 0;
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sqlStatement, model);) {
            row = stmt.executeUpdate();
            ConnectionHelper.close(stmt.getConnection());
            System.out.println("Insert thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (INSERT) Query on table (");
            log.append(tableName);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(row);
            loggingQuery(log.toString());
        }
        }
        return;
    }

    public static void insertQueryWithName(String tableName, Map<String, Object> model) {
        if (DialogHelper.confirm(null, "Tiếp tục?, Thực hiện thêm mới")) {
            if (model.isEmpty() || model == null) {
            System.out.println("Không có giá trị để thực hiển Insert");
            return;
        }

        //ColumnList
        StringBuilder columns = new StringBuilder();
        //ColumnValues
        List<Object> values = new ArrayList();
        //Iterate to get stuff from map
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            //fill columnName
            columns.append(entry.getKey());
            columns.append(",");
            //fill columnValue
            values.add(entry.getValue());
        }
        columns.deleteCharAt(columns.length() - 1);
        ///////////////

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(columns.toString());
        sb.append(") ");

        sb.append(" VALUES(");
        for (int i = 0; i < model.size(); i++) {
            sb.append("?");
            if (i == model.size() - 1) {
                sb.append(")");
                break;
            }
            sb.append(",");
        }
        String sqlStatement = sb.toString();
        int row = 0;
        values.stream().forEach(e -> System.out.println(e.toString()));
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sqlStatement, values.toArray());) {
            row = stmt.executeUpdate();
            ConnectionHelper.close(stmt.getConnection());
            System.out.println("Insert thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (INSERT) Query on table (");
            log.append(tableName);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(row);
            loggingQuery(log.toString());
        }
        }
        return;
    }

    public static void insertQueryWithNameSilent(String tableName, Map<String, Object> model) {
        if (model.isEmpty() || model == null) {
            System.out.println("Không có giá trị để thực hiển Insert");
            return;
        }

        //ColumnList
        StringBuilder columns = new StringBuilder();
        //ColumnValues
        List<Object> values = new ArrayList();
        //Iterate to get stuff from map
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            //fill columnName
            columns.append(entry.getKey());
            columns.append(",");
            //fill columnValue
            values.add(entry.getValue());
        }
        columns.deleteCharAt(columns.length() - 1);
        ///////////////

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(columns.toString());
        sb.append(") ");

        sb.append(" VALUES(");
        for (int i = 0; i < model.size(); i++) {
            sb.append("?");
            if (i == model.size() - 1) {
                sb.append(")");
                break;
            }
            sb.append(",");
        }
        String sqlStatement = sb.toString();
        int row = 0;
        values.stream().forEach(e -> System.out.println(e.toString()));
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sqlStatement, values.toArray());) {
            row = stmt.executeUpdate();
            ConnectionHelper.close(stmt.getConnection());
            System.out.println("Insert thành công");
        } catch (SQLException e) {
            System.out.println("Lỗi insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (INSERT) Query on table (");
            log.append(tableName);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(row);
            loggingQuery(log.toString());
        }
    }

    public static void updateQuery(String tableName, Map<String, Object> keyValuePair) {
        if (DialogHelper.confirm(null, "Tiếp tục?, Cập nhập?")) {
            StringBuilder sb = new StringBuilder();
        sb.append("UPDATE");
        sb.append(" ");
        sb.append(tableName);
        sb.append(" ");
        sb.append("SET");
        sb.append(" ");
        int count = 0;
        ArrayList<Object> list = new ArrayList();
        for (Map.Entry<String, Object> entries : keyValuePair.entrySet()) {
            sb.append(entries.getKey());
            sb.append(" = ? ");
            list.add(entries.getValue());
            if (count == keyValuePair.size() - 2) {
                sb.append(" ");
                sb.append("WHERE");
                sb.append(" ");
                count++;
                continue;
            } else if (count == keyValuePair.size() - 1) {
                break;
            }
            sb.append(",");
            count++;
        }
        int rows = 0;
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sb.toString(), list.toArray());) {
            rows = stmt.executeUpdate();
            ConnectionHelper.close(stmt.getConnection());
            DialogHelper.alert(null, "Update thành công");
        } catch (SQLException e) {
            DialogHelper.alert(null, "Update thất bại");
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (UPDATE) Query on table (");
            log.append(tableName);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(rows);
            loggingQuery(log.toString());
        }
        }
//        System.out.println("Updating Query with " + keyValuePair.size() + " values");
        return;
    }

    public static void updateQuerySilent(String tableName, Map<String, Object> keyValuePair) {
        //same method, just no confirmations
//        System.out.println("Updating Query with " + keyValuePair.size() + " values");
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE");
        sb.append(" ");
        sb.append(tableName);
        sb.append(" ");
        sb.append("SET");
        sb.append(" ");
        int count = 0;
        ArrayList<Object> list = new ArrayList();
        for (Map.Entry<String, Object> entries : keyValuePair.entrySet()) {
            sb.append(entries.getKey());
            sb.append(" = ? ");
            list.add(entries.getValue());
            if (count == keyValuePair.size() - 2) {
                sb.append(" ");
                sb.append("WHERE");
                sb.append(" ");
                count++;
                continue;
            } else if (count == keyValuePair.size() - 1) {
                break;
            }
            sb.append(",");
            count++;
        }
        int rows = 0;
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(sb.toString(), list.toArray());) {
            rows = stmt.executeUpdate();
            ConnectionHelper.close(stmt.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            StringBuilder log = new StringBuilder();
            log.append("[");
            log.append(DateHelper.getFullDateTime().toString());
            log.append("]: ");
            log.append("Performed (UPDATE) Query on table (");
            log.append(tableName);
            log.append(")");
            log.append("by ID: (");
            log.append(SessionHelper.getCURRENT_ID());
            log.append("). ");
            log.append("Row(s) affected: ");
            log.append(rows);
            loggingQuery(log.toString());
        }
    }

    public static PreparedStatement getAbsoluteFilterQuery(String tableName, Map<String, Object> valueMap) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(" ");
        sb.append("WHERE");
        sb.append(" ");
        int i = 0;
        List values = new ArrayList();
        for (Map.Entry<String, Object> entries : valueMap.entrySet()) {
            values.add(entries.getValue());
            sb.append(entries.getKey());
            sb.append("=");
            sb.append("?");
            if (i == valueMap.size() - 1) {
                break;
            }
            sb.append(",");
            i++;
        }
        System.out.println(sb.toString());
        return QueryHelper.getPreparedStatement(sb.toString(), values.toArray());
    }

    public static PreparedStatement getRelativeFilterQuery(String tableName, Map<String, Object> valueMap) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(" ");
        sb.append("WHERE");
        sb.append(" ");
        int i = 0;
        List values = new ArrayList();
        for (Map.Entry<String, Object> entries : valueMap.entrySet()) {
            values.add(entries.getValue());
            sb.append(" UPPER(");
            sb.append(entries.getKey());
            sb.append(")");
            sb.append(" ");
            sb.append("LIKE CONCAT('%'");
            sb.append(",?,");
            sb.append("'%')");
            if (i == valueMap.size() - 1) {
                break;
            }
            sb.append(",");
            i++;
        }
//        System.out.println(sb.toString());
        return QueryHelper.getPreparedStatement(sb.toString(), values.toArray());
    }

    public static PreparedStatement getLessThanQuery(String tableName, Map<String, Object> valueMap) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(" ");
        sb.append("WHERE");
        sb.append(" ");
        int i = 0;
        List values = new ArrayList();
        for (Map.Entry<String, Object> entries : valueMap.entrySet()) {
            values.add(entries.getValue());
            sb.append(entries.getKey());
            sb.append("<=");
            sb.append("?");
            if (i == valueMap.size() - 1) {
                break;
            }
            sb.append(",");
            i++;
        }
        return QueryHelper.getPreparedStatement(sb.toString(), values.toArray());
    }

    public static List<Map<String, Object>> readQuery(String tableName) {
        List<Map<String, Object>> resultList = new ArrayList();
        Map<String, Object> row;
        String readQuery = "SELECT * FROM " + tableName;
        try (PreparedStatement stmt = QueryHelper.getPreparedStatement(readQuery);
                ResultSet rs = stmt.executeQuery();) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                row = new LinkedHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            ConnectionHelper.close(stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("Không thể thực hiện đọc giá trị từ Cơ sở dữ liệu");
            e.printStackTrace();
        } finally {
            return resultList;
        }
    }

    public static List<Map<String, Object>> readQuery(PreparedStatement readWithConditionStatement) {
        System.out.println("Read for: " + readWithConditionStatement.toString());
        List<Map<String, Object>> resultList = new ArrayList();
        Map<String, Object> row;
        try (PreparedStatement stmt = readWithConditionStatement;
                ResultSet rs = stmt.executeQuery();) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                row = new LinkedHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            ConnectionHelper.close(stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("Không thể thực hiện đọc giá trị từ Cơ sở dữ liệu");
            e.printStackTrace();
        } finally {
            return resultList;
        }
    }

    public static PreparedStatement getTopQueryWithCondition(Map<String, String> columnsToRead, String tableName, Map<String, Object> conditions, String groupBy, String orderBy, int rowToFetch) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (columnsToRead.size() <= 0 || columnsToRead == null) {
            System.out.println("Không có giá trị để đọc");
            return null;
        }
        int mapCount = 0;
        sb.append("SELECT ");
        for (Map.Entry<String, String> entries : columnsToRead.entrySet()) {
            sb.append(entries.getKey());
            sb.append(" AS N'");
            sb.append(entries.getValue());
            sb.append("'");
            if (mapCount == columnsToRead.size() - 1) {
                break;
            }
            sb.append(",");
            mapCount++;
        }
        mapCount = 0;
        sb.append(" FROM");
        sb.append(" ");
        sb.append(tableName);
        sb.append(" WHERE ");
        Object[] values = new Object[conditions.size()];
        for (Map.Entry<String, Object> entries : conditions.entrySet()) {
            sb.append(entries.getKey());
            sb.append("=");
            sb.append("?");
            values[mapCount] = entries.getValue();
            if (mapCount == conditions.size() - 1) {
                break;
            }
            sb.append(" AND ");
            mapCount++;
        }
        sb.append(" GROUP BY ");
        sb.append(groupBy);
        sb.append(" ORDER BY ");
        sb.append(orderBy);
        sb.append(" DESC ");
        sb.append("OFFSET 0 ROWS FETCH NEXT ");
        sb.append(rowToFetch);
        sb.append(" ROWS ONLY");
        System.out.println("sb" + sb.toString());
        return QueryHelper.getPreparedStatement(sb.toString(), values);
    }

    private static void loggingQuery(String loggingContent) {
        try (FileWriter fr = new FileWriter("logging.txt", true);
                BufferedWriter bw = new BufferedWriter(fr)) {
            bw.append("\n");
            bw.append(loggingContent);
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
