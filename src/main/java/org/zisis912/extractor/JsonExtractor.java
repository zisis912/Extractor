package org.zisis912.extractor;

import com.google.gson.JsonElement;

public interface JsonExtractor {
    String filename();
    JsonElement extractData();
}
