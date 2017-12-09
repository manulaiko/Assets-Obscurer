package com.manulaiko.assetsobscurer.assets;

import com.manulaiko.assetsobscurer.main.EncryptionManager;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Obscurer class.
 * ===============
 *
 * This class will (de)obscure the given assets.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
public class Obscurer {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(Obscurer.class);

    /**
     * Assets to obscure.
     */
    private final List<Index.Asset> _assets;

    /**
     * Encrypts the assets.
     *
     * @return Encrypted assets.
     */
    public List<Index.Asset> encrypt() {
        List<Index.Asset> assets = new ArrayList<>();

        for (Index.Asset asset : this.assets()) {
            if (this.encrypt(asset)) {
                assets.add(asset);
            }
        }

        return assets;
    }

    /**
     * Encrypts a given asset.
     *
     * @param asset Asset to encrypt.
     *
     * @return Whether the asset was successfully encrypted or not.
     */
    public boolean encrypt(Index.Asset asset) {
        if (asset.isEncrypted()) {
            return false;
        }

        try {
            Obscurer.console.fine("Encrypting " + asset.path() + "...");
            File f = new File(asset.path());
            Path p = f.toPath();

            DigestInputStream input = new DigestInputStream(new FileInputStream(f), MessageDigest.getInstance("SHA1"));
            byte[] bytes = new byte[(int) f.length()];
            input.read(bytes);
            input.close();
            String hash = DatatypeConverter.printHexBinary(input.getMessageDigest().digest());

            bytes = EncryptionManager.instance().encrypt(bytes);

            if (bytes == null) {
                Obscurer.console.warning("Couldn't encrypt asset!");

                return false;
            }
            Files.write(p, bytes);
            p = Files.move(p, p.resolveSibling(hash));

            asset.isEncrypted(true);
            asset.path(p.toString());
            asset.hash(hash);

            Obscurer.console.fine(f.getPath() + " encrypted to " + p.toString());
        } catch (IOException e) {
            Obscurer.console.exception("Couldn't read asset!", e);

            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        return true;
    }
}
