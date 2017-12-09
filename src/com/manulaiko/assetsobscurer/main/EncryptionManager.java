package com.manulaiko.assetsobscurer.main;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
                    Settings.privateKey,
                    Settings.publicKey
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
     * Private key.
     */
    private final File _privateKey;

    /**
     * Public key.
     */
    private final File _publicKey;

    /**
     * Key pair.
     */
    private KeyPair _keyPair;

    /**
     * Cipher.
     */
    private Cipher _cipher;

    /**
     * Initializes the encryption manager.
     */
    private void initialize() {
        if (this.keyPair() != null) {
            return;
        }

        if (!this.privateKey().isFile() || !this.publicKey().isFile()) {
            this.keyPair(this._generateKeyPair());

            return;
        }

        try {
            this.keyPair(this._loadKeyPair());
            this.cipher(Cipher.getInstance("RSA"));
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't initialize Encryption Manager!", e);
        }
    }

    /**
     * Loads the key pair from the filesystem.
     *
     * @return Loaded key pair.
     */
    private KeyPair _loadKeyPair() {
        try {
            EncryptionManager.console.info("Loading key pair...");
            FileInputStream input = new FileInputStream(this.publicKey());
            byte[] encodedPublicKey = new byte[(int) this.publicKey().length()];
            input.read(encodedPublicKey);
            input.close();

            input = new FileInputStream(this.privateKey());
            byte[] encodedPrivateKey = new byte[(int) this.privateKey().length()];
            input.read(encodedPrivateKey);
            input.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't load Key Pair!", e);

            return null;
        }
    }

    /**
     * Generates a new key pair
     *
     * @return Generated key pair.
     */
    private KeyPair _generateKeyPair() {
        try {
            EncryptionManager.console.info("Generating key pair...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            keyGen.initialize(this.keyLength());
            KeyPair keyPair = keyGen.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream output = new FileOutputStream(this.publicKey());
            output.write(publicKeySpec.getEncoded());
            output.close();

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            output = new FileOutputStream(this.privateKey());
            output.write(privateKeySpec.getEncoded());
            output.close();

            return keyPair;
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
            this.cipher().init(Cipher.ENCRYPT_MODE, this.keyPair().getPrivate());

            return this.cipher().doFinal(bytes);
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
            this.cipher().init(Cipher.DECRYPT_MODE, this.keyPair().getPublic());

            return this.cipher().doFinal(bytes);
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't decrypt bytes!", e);

            return null;
        }
    }
}
