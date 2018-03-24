package com.manulaiko.assetsobscurer.main.commands;

import com.manulaiko.assetsobscurer.main.EncryptionManager;
import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import com.manulaiko.tabitha.utils.ICommand;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class LoadKeyPair implements ICommand {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(LoadKeyPair.class);

    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    @Override
    public void handle(String[] command) {
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");

            LoadKeyPair.console.fine("Enter path to public key:");
            String path = LoadKeyPair.console.readLine();

            if (!path.isEmpty()) {
                FileInputStream in = new FileInputStream(path);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                int b = 0;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                in.close();

                byte[] bytes = out.toByteArray();
                out.close();

                X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
                PublicKey pk = factory.generatePublic(spec);

                Settings.publicKey = pk;
                EncryptionManager.instance().publicKey(pk);
            }

            LoadKeyPair.console.fine("Enter path to private key:");
            path = LoadKeyPair.console.readLine();

            if (!path.isEmpty()) {
                FileInputStream in = new FileInputStream(path);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                int b = 0;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                in.close();

                byte[] bytes = out.toByteArray();
                out.close();

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
                PrivateKey pk = factory.generatePrivate(spec);

                Settings.privateKey = pk;
                EncryptionManager.instance().privateKey(pk);
            }

            LoadKeyPair.console.finer("Key pair loaded!");
        } catch (Exception e) {
            LoadKeyPair.console.exception("Can't load key pair!", e);
        }
    }

    /**
     * Checks whether this command can execute `name` command.
     *
     * @param name Command name to check.
     *
     * @return Whether this command can execute `name`.
     */
    @Override
    public boolean canHandle(String name) {
        return name.equalsIgnoreCase(this.name());
    }

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    @Override
    public String name() {
        return "load_keypair";
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    @Override
    public String description() {
        return "Loads an existing keypair";
    }
}
