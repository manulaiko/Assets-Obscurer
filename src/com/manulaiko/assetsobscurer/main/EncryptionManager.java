package com.manulaiko.assetsobscurer.main;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

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
                    Settings.keyLength
            );
            EncryptionManager._instance.initialize(
                    Settings.publicKey,
                    Settings.privateKey
            );
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
     * AES Key.
     */
    private SecretKey _secretKey;

    /**
     * Public key.
     */
    private PublicKey _publicKey;

    /**
     * Private key.
     */
    private PrivateKey _privateKey;

    /**
     * Initializes the encryption manager.
     */
    private void initialize(PublicKey publicKey, PrivateKey privateKey) {
        if (this.secretKey() != null) {
            return;
        }

        try {

            if (publicKey == null && privateKey == null) {
                EncryptionManager.console.fine("Generating key pair...");

                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(1024);
                KeyPair keyPair = generator.genKeyPair();

                publicKey = keyPair.getPublic();
                privateKey = keyPair.getPrivate();

                EncryptionManager.console.finer("Key pair generated!");
            }

            this.secretKey(this.generateKey());
            this.publicKey(publicKey);
            this.privateKey(privateKey);
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't initialize EncryptionManager!", e);
        }
    }

    /**
     * Generates a new key
     *
     * @return Generated key.
     */
    public SecretKey generateKey() {
        try {
            EncryptionManager.console.info("Generating session key...");
            KeyGenerator keyGen = KeyGenerator.getInstance("Rijndael");
            keyGen.init(this.keyLength());

            return keyGen.generateKey();
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't session key!", e);

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
            if (this.publicKey() == null) {
                throw new Exception("Public key is null!");
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(baos);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey());

            if (this.secretKey() == null) {
                this.secretKey(this.generateKey());
            }

            byte[] encriptedKey = cipher.doFinal(this.secretKey().getEncoded());
            output.writeInt(encriptedKey.length);
            output.write(encriptedKey);

            SecureRandom r = new SecureRandom();
            byte[] iv = new byte[16];
            r.nextBytes(iv);

            output.write(iv);

            IvParameterSpec spec = new IvParameterSpec(iv);
            Cipher symmetricCipher = Cipher.getInstance("Rijndael/CBC/PKCS5Padding");
            symmetricCipher.init(Cipher.ENCRYPT_MODE, this.secretKey(), spec);

            CipherOutputStream cos = new CipherOutputStream(output, symmetricCipher);
            cos.write(bytes);
            cos.close();

            return baos.toByteArray();
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
            DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));

            SecretKey key = this._readKey(input);
            IvParameterSpec iv = this._readIv(input);

            Cipher cipher = Cipher.getInstance("Rijndael/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            CipherInputStream cis = new CipherInputStream(input, cipher);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[0xFFFF];
            for (int len = cis.read(buffer); len != -1; len = cis.read(buffer)) {
                out.write(buffer, 0, len);
            }

            return out.toByteArray();
        } catch (Exception e) {
            EncryptionManager.console.exception("Couldn't decrypt bytes!", e);

            return null;
        }
    }

    /**
     * Returns the session key from a input stream.
     *
     * @param input Input stream with session key.
     *
     * @return Session key.
     */
    private SecretKey _readKey(DataInputStream input) throws Exception {
        if (this.privateKey() == null) {
            throw new Exception("Private key is null!");
        }

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, this.privateKey());

        byte[] sessionKey = new byte[input.readInt()];
        input.readFully(sessionKey);

        byte[] key = rsaCipher.doFinal(sessionKey);

        return new SecretKeySpec(key, "Rijndael");
    }

    /**
     * Returns the IV from a input stream.
     *
     * @param input Input stream with IV.
     *
     * @return IV from input.
     */
    private IvParameterSpec _readIv(DataInputStream input) throws Exception {
        byte[] iv = new byte[16];
        input.read(iv);

        return new IvParameterSpec(iv);
    }
}
