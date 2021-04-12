package com.tobin.recipe.linkage.bean;

public class DefaultGroupedItem extends BaseGroupedItem<DefaultGroupedItem.ItemInfo> {

    public DefaultGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public DefaultGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String content;

        public ItemInfo(String title, String group, String content) {
            super(title, group);
            this.content = content;
        }

        public ItemInfo(String title, String group) {
            super(title, group);
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
