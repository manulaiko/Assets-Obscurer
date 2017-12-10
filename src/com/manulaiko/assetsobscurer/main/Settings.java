package com.manulaiko.assetsobscurer.main;

import java.io.File;

/**
 * Settings class.
 * ===============
 *
 * Contains application's settings.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Settings {
    /**
     * Whether we're running in the command mode or not.
     */
    public static boolean commandMode = true;

    /**
     * Path to the assets folder.
     */
    public static File assets = new File("assets");

    /**
     * Path to the encryption key.
     */
    public static File key = new File("key");

    /**
     * Key length.
     */
    public static int keyLength = 128;

    /**
     * Automatic encryption mode.
     */
    public static boolean encryptMode = true;
}
