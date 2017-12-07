package com.manulaiko.tabitha.database;

import com.manulaiko.tabitha.utils.Inflector;
import com.manulaiko.tabitha.utils.Str;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Database Access Object.
 * =======================
 *
 * Base class for all objects that will be retrieved from the database.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class DAO {
    /**
     * Entity ID.
     */
    private int _id = 0;

    /**
     * Constructor.
     *
     * @param id Entity ID.
     */
    public DAO(int id) {
        this._id = id;
    }

    /**
     * Returns entity ID.
     *
     * @return Entity ID.
     */
    public int id() {
        return this._id;
    }

    /**
     * Sets entity ID.
     *
     * @param id New Entity ID.
     */
    public void id(int id) {
        this._id = id;
    }

    /**
     * Saves the current entity to the database.
     */
    public void save() {
        if (this.id() == 0) {
            int id = Factory.database().insert(this._insertQuery(), this.getFields().values());
            this.id(id);

            return;
        }

        Factory.database().update(this._updateQuery());
    }

    /**
     * Builds and returns the INSERT query for this DAO.
     *
     * @return INSERT Query.
     */
    private String _insertQuery() {
        String columns = Str.implode(this.getFields().keySet(), "`, `");
        String values = Str.implode(this._getPlaceHolders(this.getFields().size()), ", ");

        return "INSERT INTO `" + this.getTable() + "` (`" + columns + "`) VALUES (" + values + ");";
    }

    /**
     * Builds and returns the UPDATE query for this DAO.
     *
     * @return UPDATE Query.
     */
    private String _updateQuery() {
        String set = Str.implode(this.getFields().keySet(), "`=?, `");

        return "UPDATE `" + this.getTable() + "` SET " + set + " WHERE `id`=" + this.id();
    }

    /**
     * Returns an array of given length with placeholders.
     *
     * @param length Array length.
     */
    private Collection<String> _getPlaceHolders(int length) {
        Collection<String> placeholders = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            placeholders.add("?");
        }

        return placeholders;
    }

    /**
     * Returns table name.
     *
     * @return Table name.
     */
    public String getTable() {
        String className = this.getClass().getSimpleName();
        className = Str.snakeCase(className);

        return Inflector.plural(className);
    }

    /**
     * Returns fields from the database.
     *
     * @return Database fields.
     */
    public abstract Map<String, Object> getFields();
}
