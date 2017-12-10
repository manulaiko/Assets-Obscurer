package com.manulaiko.tabitha.database;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import com.manulaiko.tabitha.utils.Str;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO Factory.
 * ============
 *
 * Factory object used to retrieve objects from the database.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Factory<T extends DAO> {
    ///////////////////////////////////
    // Static Methods and Properties //
    ///////////////////////////////////

    /**
     * Console instance.
     */
    public static final Console console = ConsoleManager.forClass(Factory.class);

    /**
     * Database object.
     */
    protected static Connection _database;

    /**
     * Builder object.
     */
    protected Builder _builder;

    /**
     * Table name.
     */
    protected String _table;

    ///////////////////////////////////////
    // Non Static Methods and Properties //
    ///////////////////////////////////////

    /**
     * Primary key name.
     */
    protected String _primaryKey = "id";

    /**
     * Current cache.
     */
    protected Map<Integer, T> _cache = new HashMap<>();

    /**
     * Sets database object.
     *
     * @param database Database connection object.
     */
    public static void database(Connection database) {
        Factory._database = database;
    }

    /**
     * Returns database object.
     *
     * @return Database object.
     */
    public static Connection database() {
        return Factory._database;
    }

    /**
     * Returns all results from the API server.
     *
     * This method will return all the results provided by the database.
     *
     * @return Results from API server.
     */
    public Map<Integer, T> all() {
        if (this._cache.size() != 0) {
            return this._cache;
        }

        Map<Integer, T> ret = new HashMap<>();

        try {
            ResultSet query = Factory.database().query("SELECT * FROM `" + this.table() + "`");

            while (query.next()) {
                T d = this.build(query);

                ret.put(d.id(), d);
            }
        } catch (SQLException e) {
            Factory.console.exception("Couldn't load all results from `" + this.table() + "`", e);
        }

        this._cache.putAll(ret);

        return this._cache;
    }

    /**
     * Finds an object from the database based on the given id.
     *
     * @param id Primary Key value.
     *
     * @return DAO instance.
     */
    public T find(int id) {
        if (this.cache().containsKey(id)) {
            return this.cache().get(id);
        }

        return this.find(this.primaryKey(), id);
    }

    /**
     * Finds an object from the database based on the given id.
     *
     * @param column Column name.
     * @param value  Column value.
     *
     * @return DAO instance.
     */
    public T find(String column, Object value) {
        T object = null;

        try {
            ResultSet query = Factory.database().query(
                    "SELECT * FROM `" + this.table() + "` WHERE `" + column + "`=?",
                    value
            );

            if (!query.isBeforeFirst()) {
                Factory.console.warning("DAO for `" +
                        this.table() +
                        "` with `" +
                        column +
                        "` " +
                        value.toString() +
                        " not found!");

                return null;
            }

            object = this.build(query);
        } catch (Exception e) {
            // Ignore
        }

        return object;
    }

    /**
     * Finds an object from the database based on the given id.
     *
     * @param columns Column names and values.
     *
     * @return DAO instance.
     */
    public T find(Map<String, Object> columns) {
        T object = null;

        try {
            String query = "SELECT * FROM `" + this.table() + "` WHERE `";
            query += Str.implode(columns.keySet(), "`=? AND `");

            ResultSet result = Factory.database().query(query, columns.values());

            if (!result.isBeforeFirst()) {
                Factory.console.warning("DAO for `" + this.table() + "` not found. Query: " + query);

                return null;
            }

            object = this.build(result);
        } catch (Exception e) {
            // Ignore
        }

        return object;
    }

    /**
     * Builds and returns a collection of specified amount and id
     *
     * @param id     ID of the row.
     * @param amount Amount of rows with `id` to create.
     *
     * @return List of `amount` sized objects with id `id`.
     */
    public List<T> list(int id, int amount) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            list.add(this.find(id));
        }

        return list;
    }

    /**
     * Clears the current cache.
     */
    public void clearCache() {
        this._cache.clear();
    }

    /**
     * Builds a DAO object from its ResultSet query.
     *
     * @param result Query result.
     *
     * @return DAO object.
     */
    public T build(ResultSet result) {
        return (T) builder().build(result);
    }

    //<editor-fold desc="Getters and Setters" defaultState="collapsed">

    /**
     * Returns table name.
     *
     * @return Table name.
     */
    public String table() {
        return _table;
    }

    /**
     * Sets table name.
     *
     * @param table New table name.
     */
    public void table(String table) {
        _table = table;
    }

    /**
     * Returns primary key name.
     *
     * @return Primary key name.
     */
    public String primaryKey() {
        return _primaryKey;
    }

    /**
     * Sets primary key name.
     *
     * @param primaryKey New primary key name.
     */
    public void primaryKey(String primaryKey) {
        _primaryKey = primaryKey;
    }

    /**
     * Returns the builder object.
     *
     * @return Builder object.
     */
    public Builder builder() {
        return _builder;
    }

    /**
     * Sets the builder object.
     *
     * @param builder New builder object.
     */
    public void builder(Builder builder) {
        _builder = builder;
    }

    /**
     * Sets `cache`.
     *
     * @param cache New value for `cache`.
     */
    public void cache(Map<Integer, T> cache) {
        this._cache = cache;
    }

    /**
     * Returns `cache`.
     *
     * @return Current value of `cache`.
     */
    public Map<Integer, T> cache() {
        return this._cache;
    }
    //</editor-fold>
}
