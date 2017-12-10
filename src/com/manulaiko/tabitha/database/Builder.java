package com.manulaiko.tabitha.database;

import java.sql.ResultSet;

/**
 * Builder class.
 * ==============
 *
 * Builds the DAO objects.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface Builder<T> {
    /**
     * Builds and returns the DAO object.
     *
     * @param result Query result.
     *
     * @return DAO object for `query`.
     */
    T build(ResultSet result);
}
