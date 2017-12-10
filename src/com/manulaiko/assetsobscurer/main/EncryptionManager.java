package com.manulaiko.assetsobscurer.main;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;

/**
 * Encryption manager.
 * ===================
 *
 * Manages the key generation and encryption/decryption.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
public class EncryptionManager {
    ///////////////////////////////////
    // Static methods and properties //
    ///////////////////////////////////

    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(EncryptionManager.class);

    /**
     * Singleton instance.
     */
    private static EncryptionManager _instance;

    /**
     * Returns singleton instance.
     */
    public static EncryptionManager instance() {
        if (EncryptionManager._instance == null) {
            EncryptionManager._instance = new EncryptionManager(
                    Settings.keyLength,
                    Settings.key
            );
            EncryptionManager._instance.initialize();
        }

        return EncryptionManager._instance;
    }

    ///////////////////////////////////////
    // Non static methods and properties //
    ///////////////////////////////////////

    /**
     * Key length.
     */
    private final int _keyLength;

    /**
     * Key location.
     */
    private final File _key;

    /**
     * AES Key.
     */
    private SecretKey _secretKey;

    /**
     * AES Cipher for encryption.
     */
    private Cipher _aesCipherEncrypt;

    /**
     * AES Cipher for decryption.
     */
    private Cipher _aesCipherDecrypt;

    /**
     * Initializes the encryption manager.
     */
    private void initialize() {
        if (this.secretKey() != null) {
            return;
        }

        try {
            this.aesCipherEncrypt(Cipher.getInstance("AES"));
            this.aesCipherDecrypt(Cipher.getInstance("AES"));

            if (!this.key().isFile()) {
                this.secretKey(this._generateKey());
            } else {
                this.secretKey(this._loadKey());
            }
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't initialize Encryption Manager!", e);
        }
    }

    /**
     * Sets the encryption key.
     *
     * @param key New encryption key.
     *
     * @throws InvalidKeyException If the cipher couldn't be initialized.
     */
    public void secretKey(SecretKey key) throws InvalidKeyException {
        this._secretKey = key;

        this.aesCipherEncrypt().init(Cipher.ENCRYPT_MODE, this.secretKey());
        this.aesCipherDecrypt().init(Cipher.DECRYPT_MODE, this.secretKey());
    }

    /**
     * Loads the key from the filesystem.
     *
     * @return Loaded key.
     */
    private SecretKey _loadKey() {
        try {
            EncryptionManager.console.info("Loading AES key...");
            FileInputStream input = new FileInputStream(this.key());
            byte[] key = new byte[(int) this.key().length()];
            input.read(key);
            input.close();

            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't load Key Pair!", e);

            return null;
        }
    }

    /**
     * Generates a new key
     *
     * @return Generated key.
     */
    private SecretKey _generateKey() {
        try {
            EncryptionManager.console.info("Generating AES key...");
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(this.keyLength());

            SecretKey key = keyGen.generateKey();

            FileOutputStream output = new FileOutputStream(this.key());
            output.write(key.getEncoded());
            output.close();

            return key;
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't generate Key Pair!", e);

            return null;
        }
    }

    /**
     * Encrypts a byte array.
     *
     * @param bytes Bytes to encrypt.
     *
     * @return Encrypted bytes.
     */
    public byte[] encrypt(byte[] bytes) {
        try {
            return this.aesCipherEncrypt().doFinal(bytes);
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't encrypt bytes!", e);

            return null;
        }
    }

    /**
     * Decrypts a byte array.
     *
     * @param bytes Bytes to decrypt.
     *
     * @return Decrypted bytes.
     */
    public byte[] decrypt(byte[] bytes) {
        try {
            return this.aesCipherDecrypt().doFinal(bytes);
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't decrypt bytes!", e);

            return null;
        }
    }
}
