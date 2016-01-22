package me.tinggu.sample.model;

import java.util.List;

public class PrettyGirl extends BaseModel {

    public int id;
    public String created_at;
    public Meta meta;
    public String url;
    public String channel;

    @Override
    public String toString() {
        return "PrettyGirl{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", meta=" + meta +
                ", url='" + url + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }

    public class Meta {
        public String type;
        public int width;
        public int height;
        public List<String> colors;

        @Override
        public String toString() {
            return "Meta{" +
                    "type='" + type + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", colors=" + colors +
                    '}';
        }
    }
}
