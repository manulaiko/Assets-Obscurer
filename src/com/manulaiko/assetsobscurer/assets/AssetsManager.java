package com.manulaiko.assetsobscurer.assets;

import com.google.gson.Gson;
import com.manulaiko.assetsobscurer.main.EncryptionManager;
import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import java.io.File;
import java.nio.file.Files;
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
            this.index(new Index(new ArrayList<>()));
        }
    }

    /**
     * Scans all the directories and adds the entries to the index.
     */
    public void scan() {
        Scanner scanner = new Scanner(this.assets());

        AssetsManager.console.info("Scanning " + this.assets().getAbsolutePath() + "...");
        scanner.scan();
        List<Index.Asset> assets = scanner.assets();
        AssetsManager.console.info("Found " + assets.size() + " assets!");

        this.addAll(assets);
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
}
