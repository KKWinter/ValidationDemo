package com.jumpraw.mraid.cache;

import java.io.InputStream;
import java.util.Map;

public class DownloadResult {
        public int responseCode;
        public String responseMsg;
        public InputStream inputStream;
        public Map<String, String> responseHeaders;
        public String contentEncoding;
        public String contentType;
        boolean fromCache;

        DownloadResult(int responseCode, String responseMsg, InputStream inputStream, Map<String, String> responseHeaders, String contentEncoding, String contentType, boolean fromCache) {
            this.responseCode = responseCode;
            this.responseMsg = responseMsg;
            this.inputStream = inputStream;
            this.responseHeaders = responseHeaders;
            this.contentEncoding = contentEncoding;
            this.contentType = contentType;
            this.fromCache = fromCache;
        }
    }