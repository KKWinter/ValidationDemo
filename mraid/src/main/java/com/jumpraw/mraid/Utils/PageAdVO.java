package com.jumpraw.mraid.Utils;


public class PageAdVO {

    public PageAd pagedAd;

    public PageAdVO(PageAd pageAd) {
        this.pagedAd = pageAd;
    }

    public boolean isDataValid() {
        return pagedAd != null;
    }


    public static class PageAd {
        // 有值表示替换html标签属性
        public String manifest;

        // html片段，广告数据
        public String html_tag;

        // vast广告数据
        public String vast_tag;


        @Override
        public String toString() {
            return "PagedAd{" +
                    "manifest='" + manifest + '\'' +
                    ", html_tag='" + html_tag + '\'' +
                    ", vast_tag='" + vast_tag + '\'' +
                    '}';
        }
    }

}
