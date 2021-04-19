package com.tobin.recipe.linkage.bean;

public class RecipeGroupedItem extends BaseGroupedItem<RecipeGroupedItem.ItemInfo> {

    public RecipeGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public RecipeGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String classid;
        private String parentid;

        public ItemInfo(String classid, String parentid, String group, String content) {
            super(content, group);
            this.classid = classid;
            this.parentid = parentid;
        }

        public ItemInfo(String content, String group) {
            super(content, group);
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        @Override
        public String toString() {
            return "ItemInfo{" +
                    "classid='" + classid + '\'' +
                    "parentid='" + parentid + '\'' +
                    "content='" + getContent() + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DefaultGroupedItem{" +
                "isHeader=" + isHeader +
                ", info=" + info +
                ", header='" + header + '\'' +
                '}';
    }
}
