package com.manulaiko.assetsobscurer.assets;

import com.google.gson.Gson;
import com.manulaiko.assetsobscurer.main.EncryptionManager;
import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Assets manager.
 * ===============
 *
 * Manages the assets folder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
public class AssetsManager {
    ///////////////////////////////////
    // Static methods and properties //
    ///////////////////////////////////

    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(AssetsManager.class);

    /**
     * Singleton instance.
     */
    private static AssetsManager _instance;

    /**
     * Returns singleton instance.
     *
     * @return Singleton instance.
     */
    public static AssetsManager instance() {
        if (AssetsManager._instance == null) {
            AssetsManager._instance = new AssetsManager(Settings.assets);
            AssetsManager._instance.initialize();
        }

        return AssetsManager._instance;
    }

    ///////////////////////////////////////
    // Non static methods and properties //
    ///////////////////////////////////////

    /**
     * Assets folder.
     */
    private final File _assets;

    /**
     * Assets index.
     */
    private Index _index;

    /**
     * Initializes the asset manager.
     */
    public void initialize() {
        if (this.index() != null) {
            return;
        }

        try {
            File file = this.assets().listFiles(f -> f.getName().equals("assets.index") && f.isFile())[0];
            byte[] encryptedIndex = Files.readAllBytes(file.toPath());
            String json = new String(EncryptionManager.instance().decrypt(encryptedIndex));

            this.index(new Gson().fromJson(json, Index.class));
        } catch (Exception e) {
            AssetsManager.console.info("Asset index not found, a new one will be created.");
            this.index(new Index(new ArrayList<>()));
        }
    }

    /**
     * Finds and returns an asset.
     *
     * @param path Path to the asset.
     *
     * @return Asset entry on index.
     */
    public Index.Asset find(String path) {
        try {
            return this.index()
                       .assets()
                       .stream()
                       .filter(a -> a.path().equals(path))
                       .findFirst()
                       .get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Builds and returns an input stream for the
     * given asset.
     *
     * @param path Path to the asset.
     *
     * @return Input stream for `asset` or null.
     */
    public InputStream asInputStream(String path) {
        try {
            Index.Asset asset = this.find(path);
            Obscurer obscurer = new Obscurer(null);

            return obscurer.asInputStream(asset);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the asset index.
     */
    public void saveIndex() {
        try {
            this.index().check();
            String json = new Gson().toJson(this.index());
            Path path = this.assets().toPath().resolve("assets.index");

            AssetsManager.console.info("Saving assets index to " + path + "...");

            byte[] encryptedIndex = EncryptionManager.instance().encrypt(json.getBytes("UTF-8"));
            Files.write(path, encryptedIndex);

            AssetsManager.console.info("Assets index updated.");
        } catch (Exception e) {
            AssetsManager.console.exception("Couldn't save assets index!", e);
        }
    }

    /**
     * Scans all the directories and adds the entries to the index.
     */
    public void scan() {
        this.index().check();
        Scanner scanner = new Scanner(this.assets());

        AssetsManager.console.info("Scanning " + this.assets().getAbsolutePath() + "...");
        scanner.scan();
        List<Index.Asset> assets = scanner.assets();
        AssetsManager.console.info("Found " + assets.size() + " assets!");

        this.addAll(assets);
    }

    /**
     * Encrypts the indexed assets.
     */
    public void encrypt() {
        this.index().check();
        List<Index.Asset> assets = this.index().decrypted();
        Obscurer obscurer = new Obscurer(assets);

        AssetsManager.console.info("Encrypting " + assets.size() + " assets...");
        List<Index.Asset> encrypted = obscurer.encrypt();
        AssetsManager.console.info(encrypted.size() + " assets encrypted!");

        this.updateAll(encrypted);
    }

    /**
     * Decrypts the indexed assets.
     */
    public void decrypt() {
        this.index().check();
        List<Index.Asset> assets = this.index().encrypted();
        Obscurer obscurer = new Obscurer(assets);

        AssetsManager.console.info("Decrypting " + assets.size() + "assets...");
        List<Index.Asset> decrypted = obscurer.decrypt();
        AssetsManager.console.info(decrypted.size() + " assets decrypted!");

        this.updateAll(decrypted);
    }

    /**
     * Adds a list of assets to the index.
     *
     * @param assets Assets to add.
     */
    public void addAll(List<Index.Asset> assets) {
        int newEntries = 0;
        for (Index.Asset a : assets) {
            if (this.index().add(a)) {
                newEntries++;
            }
        }

        AssetsManager.console.fine("Added " + newEntries + " new entries to the index!");
    }

    /**
     * Updates a list of assets to the index.
     *
     * @param assets Assets to update.
     */
    public void updateAll(List<Index.Asset> assets) {
        int updated = 0;
        for (Index.Asset a : assets) {
            if (this.index().update(a)) {
                updated++;
            }
        }

        AssetsManager.console.fine("Updated " + updated + " entries of the index!");
    }
}
