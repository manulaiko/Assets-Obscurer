package com.manulaiko.assetsobscurer.main.commands;

import com.manulaiko.assetsobscurer.main.EncryptionManager;
import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import com.manulaiko.tabitha.utils.ICommand;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateKeyPair implements ICommand {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(GenerateKeyPair.class);

    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    @Override
    public void handle(String[] command) {
        try {
            GenerateKeyPair.console.fine("Generating key pair...");

            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair keyPair = generator.genKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            FileOutputStream output = new FileOutputStream("public.key");
            output.write(publicKey.getEncoded());
            output.close();

            output = new FileOutputStream("private.key");
            output.write(privateKey.getEncoded());
            output.close();

            Settings.publicKey = publicKey;
            Settings.privateKey = privateKey;

            EncryptionManager.instance().publicKey(publicKey);
            EncryptionManager.instance().privateKey(privateKey);

            GenerateKeyPair.console.finer("Key pair generated!");
        } catch (Exception e) {
            GenerateKeyPair.console.exception("Can't generate key pair!", e);
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
        return "generate_keypair";
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    @Override
    public String description() {
        return "Generates and saves a new keypair";
    }
}
