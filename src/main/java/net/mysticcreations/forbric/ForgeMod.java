package net.mysticcreations.forbric;

import java.net.URLClassLoader;

public class ForgeMod {

    public URLClassLoader classLoader;
    public String modId;
    public String modName;
    public String version;
    public String entrypoint;
    public Object modInstance;

    public ForgeMod(URLClassLoader classLoader, String modId, String modName, String version, String entrypoint, Object modInstance) {
        this.classLoader = classLoader;
        this.modId = modId;
        this.modName = modName;
        this.version = version;
        this.entrypoint = entrypoint;
        this.modInstance = modInstance;
    }

    @Override
    public String toString() {
        return "ForgeMod{" +
                "modId='" + modId + '\'' +
                ", modName='" + modName + '\'' +
                ", version='" + version + '\'' +
                ", entrypoint='" + entrypoint + '\'' +
                ", modInstance=" + modInstance +
                '}';
    }
}
