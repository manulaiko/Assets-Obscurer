package com.manulaiko.assetsobscurer.assets;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Index class.
 * ============
 *
 * Represents the assets index.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@AllArgsConstructor
public class Index {
    /**
     * Indexed assets.
     */
    private List<Asset> _assets = new ArrayList<>();

    /**
     * Checks if the specified asset exists inthe list.
     *
     * @param a Asset to check.
     *
     * @return Whether `a` is already in the list or not.
     */
    public boolean contains(Asset a) {
        for (Asset b : this.assets()) {
            if (
                    a.hash().equals(b.hash()) ||
                    a.path().equals(b.path())
                    ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds an asset to the list.
     *
     * @param a Asset to add.
     */
    public boolean add(Asset a) {
        if (!this.contains(a)) {
            this.assets().add(a);

            return true;
        }

        return false;
    }

    /**
     * Asset class.
     * ============
     *
     * Represents an asset entry.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    @Data
    @AllArgsConstructor
    public static class Asset {
        /**
         * Hash code.
         */
        private String _hash;

        /**
         * Original path.
         */
        private String _path;
    }
}
