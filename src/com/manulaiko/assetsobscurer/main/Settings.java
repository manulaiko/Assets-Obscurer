package com.manulaiko.assetsobscurer.main;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

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
     * Publick key.
     */
    public static PublicKey publicKey = null;

    /**
     * Private key.
     */
    public static PrivateKey privateKey = null;

    /**
     * Key length.
     */
    public static int keyLength = 256;

    /**
     * Automatic encryption mode.
     */
    public static boolean encryptMode = true;
}
