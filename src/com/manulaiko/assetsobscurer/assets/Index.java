package com.manulaiko.assetsobscurer.assets;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Asset> _assets;

    /**
     * Returns the decrypted assets.
     *
     * @return Decrypted assets.
     */
    public List<Index.Asset> decrypted() {
        return this.assets().stream().filter(a -> !a.isEncrypted()).collect(Collectors.toList());
    }

    /**
     * Returns the encrypted assets.
     *
     * @return Encrypted assets.
     */
    public List<Index.Asset> encrypted() {
        return this.assets().stream().filter(Asset::isEncrypted).collect(Collectors.toList());
    }

    /**
     * Checks if the specified asset exists inthe list.
     *
     * @param a Asset to check.
     *
     * @return Whether `a` is already in the list or not.
     */
    public boolean contains(Asset a) {
        for (Asset b : this.assets()) {
            if (a.hash().equals(b.hash()) || a.path().equals(b.path())) {
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
     * Updates an asset.
     *
     * @param a Asset to update.
     *
     * @return Whether the asset was successfully updated or no.
     */
    public boolean update(Asset a) {
        Asset asset = null;
        for (Asset b : this.assets()) {
            if (a.hash().equals(b.hash()) || a.path().equals(b.path())) {
                asset = b;

                break;
            }
        }

        if (asset == null) {
            return false;
        }

        asset.isEncrypted(a.isEncrypted());
        asset.hash(a.hash());
        asset.path(a.path());

        return true;
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

        /**
         * Whether the asset is encrypted or not.
         */
        private boolean _isEncrypted;
    }
}
