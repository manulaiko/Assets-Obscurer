Assets Obscurer
===============

Encrypt/decrypt the assets of your game easily.

Table of contents
-----------------

 - [Introduction](#introduction)
 - [Stand alone](#stand-alone)
   - [Command based](#command-based)
   - [Automagic mode](#automagic-mode)
 - [As a library](#as-a-library)
 - [Encryption](#encryption)
 - [Arguments](#arguments)

Introduction
------------
<a name="introduction"></a>

This tool encrypts the assets and renames them to their SHA-1 hash. It allows you
to easily encrypt/decrypt them so you don't have to worry about them being ripped.

This tool runs in two modes: **stand alone** and **as a library**.

Stand alone
-----------
<a name="stand-alone"></a>

The stand alone mode runs through the command line and allows you to encrypt/decrypt
the whole folder easily.

It provides two modes: **command based** and **automagic mode**.

### Command based
<a name="command-based"></a>

The command based mode is enabled by default and launches a command prompt that allows
you to perform the actions you want.

First of all, the application will load the specified encryption key and the `assets.index`
which contains the indexed assets.

The first thing you would need to do is scan the folder:

```
Assets Obscurer v0.0.0 by Manulaiko
======================================================
Enter a command to execute ('_help' for a list of commands): scan
Loading AES key...
Checking assets...
Scanning /home/manulaiko/Programming/Java/Assets-Obscurer/assets...
Found assets/testto/test
Found assets/test
Found 2 assets!
Added 2 new entries to the index!
```

Then you'll either want to encrypt or decrypt the indexed assets:

```
Enter a command to execute ('_help' for a list of commands):  encrypt
Checking assets...
Encrypting 2 assets...
Encrypting assets/testto/test...
assets/testto/test encrypted to assets/85B310D47A7E8821216DCA09F1EB2706FF81D64D
Encrypting assets/test...
assets/test encrypted to assets/1BC9E9E4A46BF8019A624F230CBDADEEFA56E0E7
2 assets encrypted!
Updated 2 entries of the index!
```

```
Enter a command to execute ('_help' for a list of commands): decrypt
Checking assets...
Decrypting 2assets...
Decrypting assets/testto/test...
assets/85B310D47A7E8821216DCA09F1EB2706FF81D64D decrypted to assets/testto/test
Decrypting assets/test...
assets/1BC9E9E4A46BF8019A624F230CBDADEEFA56E0E7 decrypted to assets/test
2 assets decrypted!
Updated 2 entries of the index!
```

Once you've made your changes remember to save the new index file:

```
Enter a command to execute ('_help' for a list of commands): save_index
Checking assets...
Saving assets index to assets/assets.index...
Assets index updated.
```

You can dump the index at any moment with the `dump_index` command:

```
Enter a command to execute ('_help' for a list of commands): dump_index
assets/testto/test:
      Hash: 85B310D47A7E8821216DCA09F1EB2706FF81D64D
      Encrypted: false

assets/test:
      Hash: 1BC9E9E4A46BF8019A624F230CBDADEEFA56E0E7
      Encrypted: false

Total: 2 indexed assets.
      0 encrypted assets.
      2 decrypted assets.
```

If you add new assets to the folder while the application is running
you just need to rescan it and the new index will be updated:

```
Enter a command to execute ('_help' for a list of commands): scan
Checking assets...
Scanning /home/manulaiko/Programming/Java/Assets-Obscurer/assets...
Found assets/testto/test
Found assets/new_asset
Found assets/test
Found 3 assets!
Added 1 new entries to the index!
```

### Automagic mode
<a name="automagic-mode"></a>

Having control on what the tool is doing is nice, but most of the times you'll either
encrypt or decrypt the assets, so why not just do it automatically?

The automatic mode is executed when the command mode is disabled, so just pass `-c=false`
as an argument and the application will automatically scan, encrypt/decrypt, save the index and
dump it to the console.

By default it automatically encrypts the assets, so to make it decrypt them just pass `-e=false`
as an argument:

```
$ java -jar bin/Assets-Obscurer.jar -c=false
Assets Obscurer v0.0.0 by Manulaiko
======================================================
Loading AES key...
Checking assets...
canning /home/manulaiko/Programming/Java/Assets-Obscurer/assets...
Found assets/two
Found assets/three
Found assets/one
Found 3 assets!
Added 0 new entries to the index!
Checking assets...
Encrypting 3 assets...
Encrypting assets/two...
assets/two encrypted to assets/7448D8798A4380162D4B56F9B452E2F6F9E24E7A
Encrypting assets/three...
assets/three encrypted to assets/A3DB5C13FF90A36963278C6A39E4EE3C22E2A436
Encrypting assets/one...
assets/one encrypted to assets/E5FA44F2B31C1FB553B6021E7360D07D5D91FF5E
3 assets encrypted!
Updated 3 entries of the index!
Checking assets...
Saving assets index to assets/assets.index...
Assets index updated.
assets/two:
      Hash: 7448D8798A4380162D4B56F9B452E2F6F9E24E7A
      Encrypted: true

assets/three:
      Hash: A3DB5C13FF90A36963278C6A39E4EE3C22E2A436
      Encrypted: true

assets/one:
      Hash: E5FA44F2B31C1FB553B6021E7360D07D5D91FF5E
      Encrypted: true

Total: 3 indexed assets.
      3 encrypted assets.
      0 decrypted assets.

$ java -jar bin/Assets-Obscurer.jar -c=false -e=false
Assets Obscurer v0.0.0 by Manulaiko
======================================================
Loading AES key...
Checking assets...
Scanning /home/manulaiko/Programming/Java/Assets-Obscurer/assets...
Found assets/E5FA44F2B31C1FB553B6021E7360D07D5D91FF5E
Found assets/A3DB5C13FF90A36963278C6A39E4EE3C22E2A436
Found assets/7448D8798A4380162D4B56F9B452E2F6F9E24E7A
Found 3 assets!
Added 3 new entries to the index!
Checking assets...
Decrypting 3 assets...
Decrypting assets/two...
assets/7448D8798A4380162D4B56F9B452E2F6F9E24E7A decrypted to assets/two
Decrypting assets/three...
assets/A3DB5C13FF90A36963278C6A39E4EE3C22E2A436 decrypted to assets/three
Decrypting assets/one...
assets/E5FA44F2B31C1FB553B6021E7360D07D5D91FF5E decrypted to assets/one
3 assets decrypted!
Updated 3 entries of the index!
Checking assets...
3 assets where deleted from index.
Saving assets index to assets/assets.index...
Assets index updated.
assets/two:
        Hash: 7448D8798A4380162D4B56F9B452E2F6F9E24E7A
         Encrypted: false

assets/three:
        Hash: A3DB5C13FF90A36963278C6A39E4EE3C22E2A436
        Encrypted: false

assets/one:
       Hash: E5FA44F2B31C1FB553B6021E7360D07D5D91FF5E
       Encrypted: false

Total: 3 indexed assets.
       0 encrypted assets.
       3 decrypted assets.
```

As a library
------------
<a name="as-a-library"></a>

It's fine and good to be able to encrypt/decrypt files with a tool, but how do you implement it in your game?

First you'll need to import the sources with your project so you can use the tool directly from the code.

The first thing you'll need is to configure the `com.manulaiko.assetsobscurer.main.Settings` class which contains
the configuration settings for the application.
Most of the times you'll only have to edit `Settings.assets` (the path to the assets folder) and `Settings.key` (the
path to the encryption key):

```java
package my.game;

import com.manulaiko.assetsobscurer.main.Settings;

public class Main {
    public static void main(String[] args) {
        Settings.assets = new File("./assets"); // default value.
        Settings.key    = new File("./key"); // default value.
    }
}
```

After that, you can just call `com.manulaiko.assetsobscurer.assets.AssetsManager.instance()` and do things:


```java
package my.game;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.assetsobscurer.assets.*;

public class Main {
    /**
     * Set `Settings.assets` and `Settings.key`.
     * Retrieve AssetsManager instance.
     * Use it.
     */
    public static void main(String[] args) {
        Settings.assets = new File("./assets"); // default value.
        Settings.key    = new File("./key");    // default value.
        
        AssetsManager assets = AssetsManager.instance();
        
        Index.Asset userModel = assets.find("assets/models/user.g3db");
        Index.Asset userTexture = assets.find("assets/textures/user.png");
        
        InputStream ship = assets.asInputStream("assets/models/ship.g3db");
    }
}
```

Encryption
----------
<a name="encryption"></a>

The encryption works. It isn't the most secure option (atm) but it does what is intended for.

The assets are encrypted, moved to the root of the folder and renamed to their hash. To keep track of them, they're
added to the `assets.index` which is a json file like this:

```json
{
    "assets": [
        {
        "hash": "SHA-1 hash",
        "path": "Path to the asset",
        "isEncrypted": false
        }, 
        {
        "hash": "00fd0148fdfg014fa0f8248f2kkk",
        "path": "assets/models/ship",
        "isEncrypted": true
        }
    ]
}
```

Of course, it's also encrypted.

The AES encryption key is generated if it doesn't exist (`-k=path/to/key`) or loaded if it exist.

You can specify the length of the key with the `-l=length` argument (default is 128).

If you're making an online game, it's a better idea to send the encryption key through a socket.
An implementation would look like this:


```java
package my.game;

import com.manulaiko.assetsobscurer.main.EncryptionManager;
import java.security.InvalidKeyException;

public class Main {
    /**
     * Build a secret key from the received command.
     * Back up the current secret key (if something goes wrong).
     * Set new secret key.
     * Reinitialize assets manager.
     */
    public static void main(String[] args) {
        CommandManager.onSecretKeyCommand(command -> {
            SecretKey key = new SecretKey(command.bytes, 0, command.bytes.length, "AES");
            SecretKey old = EncryptionManager.instance().secretKey();
            
            EncryptionManager.instance().secretKey(key);
            
            try {
                AssetsManager.instance().reinitialize();
            } catch (InvalidKeyException e) {
                System.out.println("Invalid secret key!");
                
                EncryptionManager.instance().secretKey(old);
            } catch (Exception e) {
                System.out.println("Couldn't reinitialize assets manager!");
            }
        });
    }
}
```

Arguments
---------
<a name="arguments"></a>

This are the supported command line arguments:

| Name |  Value  |   Default  |              Description            |    Example    |
|:-----|---------|------------|-------------------------------------|--------------:|
| *a*  | string  | `./assets` | Sets the path to the asset folder   | `-a=./assets` |
| *k*  | string  | `./key`    | Sets the path to the encryption key |    `-k=./key` |
| *l*  | int     | `128`      | Sets the encryption key length      |      `-l=128` |
| *c*  | boolean | `true`     | Enables the command based mode      |     `-c=true` |
| *e*  | boolean | `true`     | Automatically encrypts the assets   |     `-e=true` |