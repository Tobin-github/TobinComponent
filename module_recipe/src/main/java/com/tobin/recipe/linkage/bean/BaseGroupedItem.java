package com.tobin.recipe.linkage.bean;

import java.io.Serializable;

/**
 * items which support grouped
 */
public abstract class BaseGroupedItem<T extends BaseGroupedItem.ItemInfo> implements Serializable {
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
        private String title;

        public ItemInfo(String title, String group) {
            this.title = title;
            this.group = group;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }
}
