package com.manulaiko.assetsobscurer.assets;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scanner class.
 * ==============
 *
 * Scans the specified directory for assets.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
public class Scanner {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(Scanner.class);

    /**
     * Directory to scan.
     */
    private final File _dir;

    /**
     * Scanned assets.
     */
    private List<Index.Asset> _assets = new ArrayList<>();

    /**
     * Scans the directory and returns the assets.
     */
    public void scan() {
        File[] files = this.dir().listFiles();

        if (files != null) {
            Arrays.stream(files).forEach(this::scan);
        }
    }

    /**
     * Scans the directory and returns the assets.
     *
     * @param dir Directory to scan.
     */
    public void scan(File dir) {
        File[] dirs = dir.listFiles(File::isDirectory);
        if (dirs != null) {
            Arrays.stream(dirs).forEach(this::scan);
        }

        File[] files = dir.listFiles(File::isFile);
        if (files != null) {
            Arrays.stream(files).forEach(this::add);
        }

        if (dir.isFile()) {
            this.add(dir);
        }
    }

    /**
     * Adds an asset to the array.
     *
     * @param asset Asset to add.
     */
    public void add(File asset) {
        if (asset.isDirectory()) {
            this.scan(asset);

            return;
        }

        if (asset.getName().equals("assets.index")) {
            return;
        }

        this.assets().add(new Index.Asset("", asset.getPath(), false));
        Scanner.console.finer("Found " + asset.getPath());
    }
}
