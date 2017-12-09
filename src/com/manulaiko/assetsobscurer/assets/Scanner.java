package com.manulaiko.assetsobscurer.assets;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import java.io.File;
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
    private List<Index.Asset> _assets;

    /**
     * Scans the directory and returns the assets.
     */
    public void scan() {
        Arrays.stream(this.dir().listFiles(f -> f.isDirectory())).forEach(this::scan);
    }

    /**
     * Scans the directory and returns the assets.
     *
     * @param dir Directory to scan.
     */
    public void scan(File dir) {
        Arrays.stream(dir.listFiles(f -> f.isDirectory())).forEach(this::scan);

        Arrays.stream(dir.listFiles(f -> f.isFile() && !f.getName().equals("assets.index"))).forEach(this::add);
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

        this.assets().add(new Index.Asset("", asset.getPath()));
        Scanner.console.finer("Found " + asset.getPath());
    }
}
