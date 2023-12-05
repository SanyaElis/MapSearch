package ru.cs.vsu.eliseev.OSMReader.model;

import java.util.HashMap;
import java.util.Map;

public class ElementOnMap {
        private long id;
        private String user;
        private long uid;
        private boolean visible;
        private String timestamp;
        private long changeset;
        private int version;
        private Map<String, String> tags;

        public ElementOnMap(long id) {
                this.id = id;
                version = 1;
                visible = true;
                tags = new HashMap<>();
        }

        public long getId() {
                return id;
        }

        public String getUser() {
                return user;
        }

        public long getUid() {
                return uid;
        }

        public boolean isVisible() {
                return visible;
        }

        public String getTimestamp() {
                return timestamp;
        }

        public long getChangeset() {
                return changeset;
        }

        public int getVersion() {
                return version;
        }

        public Map<String, String> getTags() {
                return tags;
        }

        public void setUser(String user) {
                this.user = user;
        }

        public void setUid(long uid) {
                this.uid = uid;
        }

        public void setVisible(boolean visible) {
                this.visible = visible;
        }

        public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
        }

        public void setChangeset(long changeset) {
                this.changeset = changeset;
        }

        public void setVersion(int version) {
                this.version = version;
        }

        public void addTag(String key, String value){
                tags.put(key, value);
        }

        public void deleteTag(String key){
                tags.remove(key);
        }
}
