package com.manulaiko.assetsobscurer.assets;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
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
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(Index.class);

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
            if (a.path().equals(b.path())) {
                return true;
            }

            if (!b.hash().isEmpty() && a.hash().equals(b.hash())) {
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
     * Checks that all the assets in the index exist.
     *
     * The assets that doesn't exit will be deleted from
     * the index.
     */
    public void check() {
        Index.console.fine("Checking assets...");
        int i = this.assets().size();
        this.assets().removeIf(asset -> !this.check(asset));
        int j = this.assets().size();

        if (i == j) {
            return;
        }

        Index.console.fine((i - j) + " assets where deleted from index.");
    }

    /**
     * Checks that a given asset exists.
     *
     * @param asset Asset to check.
     *
     * @return Whether `asset` exists in the file system or not.
     */
    public boolean check(Asset asset) {
        File f = new File(asset.path());
        if (asset.isEncrypted()) {
            f = Settings.assets.toPath().resolve(asset.hash()).toFile();
        }

        return f.isFile();
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
