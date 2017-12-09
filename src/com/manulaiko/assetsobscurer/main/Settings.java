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
    public static boolean commandMode = false;

    /**
     * Path to the assets folder.
     */
    public static File assets = new File("assets");

    /**
     * Path to the private key.
     */
    public static File privateKey = new File("private.key");

    /**
     * Path to the public key.
     */
    public static File publicKey = new File("public.key");

    /**
     * Key length.
     */
    public static int keyLength = 2048;
}
