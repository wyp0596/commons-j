package io.github.wyp0596.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.wyp0596.util.GsonUtils;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * example:
 * <pre>
 * {@code
 * {
 *  "active": "dev",
 *  "default":{
 *      "config1":"default",
 *      "config2":"default"
 *  },
 *  "dev":{
 *      "config1":"dev"
 *  },
 *  "prod":{
 *      "config2":"prod"
 *  }
 * }
 * }
 * </pre>
 * <p>
 * while be :
 * <pre>
 * {@code
 * {
 *  "active": "dev",
 *  "config1":"dev",
 *  "config2":"default"
 * }
 * }
 * </pre>
 * <p>
 * priority is system property -> system env -> external config -> resource config
 * Created by wyp0596 on 05/02/2018.
 */
public abstract class JsonConfig {

    private static final String DEFAULT_CONFIG = "default";
    private static final String ACTIVE_CONFIG = "active";

    private JsonConfig() {
        throw new UnsupportedOperationException();
    }

    /**
     * 1. read file config to json object
     * 2. extract active config name (system property > system env > external config > resource config)
     * 3. put resource config (active config > default)
     * 4. put external config (active config > default)
     * 5. put system env
     * 6. put system property
     */
    public static Map<String, String> readConfig(Class a, String fileName) {
        Map<String, String> configMap = new LinkedHashMap<>();

        // read file config
        JsonObject resourceConf = null;
        JsonObject extConf = null;
        File conf = new File(fileName);
        if (conf.exists() && conf.isFile()) {
            extConf = readJsonConfig(fileName);
        }
        URL resConfig = a.getClassLoader().getResource(fileName);
        if (resConfig != null) {
            resourceConf = readJsonConfig(resConfig.getFile());
        }

        String activeConfig = getActiveConfigName(resourceConf, extConf);
        if (resourceConf != null) {
            putConfig(activeConfig, resourceConf, configMap);
        }
        if (extConf != null) {
            putConfig(activeConfig, extConf, configMap);
        }
        // put system env
        configMap.putAll(System.getenv());
        // put system property
        System.getProperties().forEach((k, v) -> configMap.put(k.toString(), v.toString()));
        return configMap;
    }

    private static String getActiveConfigName(JsonObject resourceConf, JsonObject extConf) {
        String active = System.getProperty(ACTIVE_CONFIG);
        if (active != null) {
            return active;
        }
        active = System.getenv(ACTIVE_CONFIG);
        if (active != null) {
            return active;
        }
        if (extConf != null) {
            JsonElement jsonElement = extConf.get(ACTIVE_CONFIG);
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                return jsonElement.getAsString();
            }
        }
        if (resourceConf != null) {
            JsonElement jsonElement = resourceConf.get(ACTIVE_CONFIG);
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                return jsonElement.getAsString();
            }
        }
        return null;
    }

    private static void putConfig(String activeConfig, JsonObject jsonConfig, Map<String, String> configMap) {
        // put default config
        JsonObject defaultConfig = jsonConfig.getAsJsonObject(DEFAULT_CONFIG);
        putJsonConfig(defaultConfig, configMap);
        // put active config
        configMap.put(ACTIVE_CONFIG, activeConfig);
        // put active config
        if (activeConfig != null) {
            JsonObject conf = jsonConfig.getAsJsonObject(activeConfig);
            if (conf != null) {
                putJsonConfig(conf, configMap);
            }
        }
    }

    private static JsonObject readJsonConfig(String fileName) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            while (true) {
                String line = fileReader.readLine();
                if (line == null) {
                    break;
                }
                line = line.replaceAll("//.*", "");
                sb.append(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return GsonUtils.toJsonObject(sb.toString());
    }

    private static void putJsonConfig(JsonObject jsonConfig, Map<String, String> configMap) {
        jsonConfig.entrySet().forEach((entry -> configMap.put(entry.getKey(), entry.getValue().getAsString())));
    }
}
