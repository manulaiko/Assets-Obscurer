package com.manulaiko.tabitha.database;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.sql.*;

/**
 * Connection class.
 * =================
 *
 * This class establish a connection to a JDBC driver
 * and provides methods for executing queries.
 *
 * The constructor accepts as parameters:
 *
 * * A string being the hostname/ip of the MySQL server.
 * * A short integer being the port on which the server is running.
 * * A string being the username.
 * * A string being the password.
 * * A string being the name of the database.
 *
 * The other constructor accepts as parameter the JDBC connection.
 *
 * Example:
 *
 * ```java
 * try {
 * Connection connection  = new Connection("localhost", (short)3306, "root", "", "tabitha");
 * Connection connection1 = new Connection(org.sqlite.JDBC.createConnection("jdbc:sqlite:database.db"));
 * } catch(ConnectionFailed e) {
 * System.out.println("Couldn't connect to database server!");
 * }
 * ```
 *
 * @author Manuliako <manulaiko@gmail.com>
 */
public class Connection {
    /**
     * Console instance.
     */
    public static final Console console = ConsoleManager.forClass(Connection.class);

    /**
     * Connection object.
     */
    protected java.sql.Connection _connection;

    /**
     * Constructor.
     *
     * @param host     Server host.
     * @param port     Server port.
     * @param username Authentication user name.
     * @param password Authentication password.
     * @param database Database name.
     *
     * @throws Exception If couldn't connect to the server.
     */
    public Connection(String host, short port, String username, String password, String database) throws Exception {
        this._connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database,
                username,
                password
        );
    }

    /**
     * Constructor.
     *
     * @param connection JDBC connection.
     */
    public Connection(java.sql.Connection connection) {
        this._connection = connection;
    }

    /**
     * Empty constructor.
     */
    public Connection() {
    }

    /**
     * Creates and executes a query.
     *
     * @param sql Query to execute.
     *
     * @return Query result.
     */
    public ResultSet query(String sql) {
        ResultSet result = null;

        try {
            Statement stm = this._connection.createStatement();
            result = stm.executeQuery(sql);
            stm.close();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Creates and executes a query.
     *
     * @param sql  Query to execute.
     * @param args Statement parameters.
     *
     * @return Query result.
     */
    public ResultSet query(String sql, Object... args) {
        ResultSet result = null;

        try {
            PreparedStatement stmt = this.prepare(sql, args);
            result = stmt.executeQuery();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Creates and executes an update query.
     *
     * @param sql Query to execute.
     *
     * @return Affected rows.
     */
    public int update(String sql) {
        int result = 0;

        try {
            Statement stm = this._connection.createStatement();
            result = stm.executeUpdate(sql);
            stm.close();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Creates and executes an update query.
     *
     * @param sql  Query to execute.
     * @param args Statement parameters.
     *
     * @return Affected rows.
     */
    public int update(String sql, Object... args) {
        int result = 0;

        try {
            PreparedStatement stmt = this.prepare(sql, args);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Creates and executes an insert query.
     *
     * @param sql Query to execute.
     *
     * @return Last insert id.
     */
    public int insert(String sql) {
        int result = 0;

        try {
            Statement stm = this._connection.createStatement();
            stm.executeUpdate(sql);

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }

            stm.close();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Creates and executes an update query.
     *
     * @param sql  Query to execute.
     * @param args Statement parameters.
     *
     * @return Affected rows.
     */
    public int insert(String sql, Object... args) {
        int result = 0;

        try {
            PreparedStatement stmt = this.prepare(true, sql, args);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }

            stmt.close();
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return result;
    }

    /**
     * Executes a prepared statement.
     *
     * @param sql  Query to execute.
     * @param args Statement parameters.
     *
     * @return Query result.
     */
    public PreparedStatement prepare(String sql, Object... args) {
        return this.prepare(false, sql, args);
    }

    /**
     * Executes a prepared statement.
     *
     * @param isInsert Whether the prepared statement is an INSERT query or not.
     * @param sql      Query to execute.
     * @param args     Statement parameters.
     *
     * @return Query result.
     */
    public PreparedStatement prepare(boolean isInsert, String sql, Object... args) {
        PreparedStatement stmt = null;

        try {
            stmt = this._connection.prepareStatement(sql);
            if (isInsert) {
                stmt = this._connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }

            for (int i = 0; i < args.length; i++) {
                int index = i + 1;
                Object p = args[i];

                if (p instanceof Boolean) {
                    stmt.setBoolean(index, (Boolean) p);
                } else if (p instanceof Integer) {
                    stmt.setInt(index, (Integer) p);
                } else if (p instanceof Double) {
                    stmt.setDouble(index, (Double) p);
                } else if (p instanceof Float) {
                    stmt.setDouble(index, (Float) p);
                } else if (p instanceof Byte) {
                    stmt.setByte(index, (Byte) p);
                } else if (p instanceof Long) {
                    stmt.setLong(index, (Long) p);
                } else if (p instanceof Array) {
                    stmt.setArray(index, (Array) p);
                } else if (p == null) {
                    stmt.setNull(index, 0);
                } else {
                    stmt.setString(index, p.toString());
                }
            }
        } catch (Exception e) {
            Connection.console.exception("Couldn't execute query `" + sql + "`", e);
        }

        return stmt;
    }

    /**
     * Returns database connection.
     *
     * @return Database connection.
     */
    public java.sql.Connection getConnection() {
        return this._connection;
    }
}
