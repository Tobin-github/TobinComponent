package com.tobin.recipe.linkage.bean;

import java.io.Serializable;

/**
 * items which support grouped
 */
public class BaseGroupedItem<T extends BaseGroupedItem.ItemInfo> implements Serializable {
    public boolean isHeader;
    public T info;
    public String header;

    public BaseGroupedItem(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.info = null;
    }

    public BaseGroupedItem(T info) {
        this.isHeader = false;
        this.header = null;
        this.info = info;
    }

    public static class ItemInfo implements Serializable {
        private String group;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public ItemInfo(String content, String group) {
            this.group = group;
            this.content = content;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "ItemInfo{" +
                    "group='" + group + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseGroupedItem{" +
                "isHeader=" + isHeader +
                ", info=" + info +
                ", header='" + header + '\'' +
                '}';
    }
}
