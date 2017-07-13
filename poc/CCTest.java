package poc;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import com.futureadvisor.Config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ManifestBuilder {
    private static Map<String, String> REQUIREMENTS_DOCUMENTS = ImmutableMap.<String, String>builder()
        .put("brd", "https://docs.google.com/document/d/1EEa5zhQpJZwon49--FDuu-Qpy1u-vWrOSXu14VqgoEs/")
        .put("erd", "https://docs.google.com/document/d/1Oibs3hhvCkqzL5s8AEjl_sxoYvxKFTm8hPtcmbBNXoc/")
        .put("prd", "https://futureadvisor.atlassian.net/wiki/display/PI/%5BPRD%5D+Trading+Tool+v2.x")
        .build();

    public static void main(String[] args) {
        try {
            List<String> lines = Arrays.asList(generateFile().toString());
            Files.write(Paths.get("manifest.json"), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject generateFile() {
        Map<String, Object> result = ImmutableMap.<String, Object>builder()
                .put("manifest_version", "1.0.0")
                .put("information",      getInformation())
                .put("endpoints",        getEndpoints())
                .build();
        return new JSONObject(result);
    }

    private static JSONObject getEndpoints() {
        Map<String, String> result = ImmutableMap.<String, String>builder()
                .put("metrics_command", "/health/metrics")
                .put("maintenance_uri", "/health/maintenance")
                .build();
        return new JSONObject(result);
    }

    private static JSONObject getRequirementsDocumentInfo() {
        return new JSONObject(REQUIREMENTS_DOCUMENTS);
    }
}
