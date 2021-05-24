package ru.fazziclay.openvkindiscord;

import ru.fazziclay.openvkindiscord.console.Logger;
import ru.fazziclay.openvkindiscord.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LibsLoader {
    public static boolean isDownloaded = false;

    public static String[][] libs = {
            {"json.jar", "json-20201115.jar", "https://github.com/FazziCLAY/OpenVkInDiscord/blob/b6b04a872ecd042b46f312b40fcaba7964a29d09/lib/json-20201115.jar?raw=true"},
            {"jda.jar", "JDA-4.2.0_168-withDependencies.jar", "https://github.com/FazziCLAY/OpenVkInDiscord/blob/b6b04a872ecd042b46f312b40fcaba7964a29d09/lib/JDA-4.2.0_168-withDependencies.jar?raw=true"}
    };

    public static boolean downloadLibs() throws Exception {
        for (String[] lib : libs) {
            String fileNameSaveAs = lib[0];
            String filePathSaveAs = "libs" + File.separator + fileNameSaveAs;
            String fileName = lib[1];
            String downloadUrl = lib[2];

            if (FileUtils.isExist(filePathSaveAs)) {
                continue;
            }
            (new File("libs")).mkdir();
            if (!isDownloaded) {
                isDownloaded = true;
            }
            Logger.info("Downloading '"+fileName+"'(save as: '"+fileNameSaveAs+"')...");

            URL website = new URL(downloadUrl);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(filePathSaveAs);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            Logger.info("Lib '"+fileName+"' downloaded!");
        }

        return isDownloaded;
    }
}
