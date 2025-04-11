package net.mysticcreations.forbric;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoader {

    public static ForgeMod load(File jarFile) throws IOException, ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        URLClassLoader classLoader = new URLClassLoader(
                new URL[]{jarFile.toURI().toURL()},
                ModLoader.class.getClassLoader()
        );

        String modId = null;
        String entrypointClass = null;

        try (JarFile jar = new JarFile(jarFile)) {
            JarEntry tomlEntry = jar.getJarEntry("META-INF/mods.toml");

            if (tomlEntry == null) {
                throw new IOException("mods.toml not found in " + jarFile.getName());
            }

            try (InputStream in = jar.getInputStream(tomlEntry)) {
                byte[] bytes = in.readAllBytes();
                String content = new String(bytes);

                for (String line : content.split("\n")) {
                    line = line.trim();
                    if (line.startsWith("modId")) {
                        modId = line.split("=")[1].trim().replace("\"", "");
                    } else if (line.startsWith("entrypoint")) {
                        entrypointClass = line.split("=")[1].trim().replace("\"", "");
                    }
                }
            }
        }

        if (modId == null || entrypointClass == null) {
            throw new IllegalArgumentException("Missing modId or entrypoint in mods.toml");
        }

        // Load the mod class
        Class<?> modClass = classLoader.loadClass(entrypointClass);
        Object modInstance = modClass.getDeclaredConstructor().newInstance();

        ForgeMod mod = new ForgeMod();
        mod.classLoader = classLoader;
        mod.modId = modId;

        // Optionally store modInstance somewhere if needed

        return mod;
    }
}
