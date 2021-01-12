package com.tobin.recipe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tobin on 2021/1/12
 * email 616041023@qq.com
 * description 菜谱搜索结果
 */
public class RecipesBean implements Serializable {

    /**
     * status : 0
     * msg : ok
     * result : {"total":162,"num":1,"list":[{"id":752,"classid":5,"name":"外婆红烧肉","peoplenum":"3-4人","preparetime":"10分钟内","cookingtime":"2小时以上","content":"浓油赤酱、咸淡适中、味道醇厚鲜美、肉质Q弹，一口咬下去，肥而不腻，入口之后，一股香气扑鼻而来，是非常惹味的一道上海本帮菜哦！","pic":"http://api.jisuapi.com/recipe/upload/20160719/115420_37020.jpg","tag":"下酒菜,丰胸,五香,健脾开胃,儿童,增强免疫力,增肥,宴请,家常菜,延缓衰老,沪菜,热菜,焖,白领,私房菜,聚会","material":[{"mname":"料酒","type":0,"amount":"适量"},{"mname":"花雕酒","type":0,"amount":"适量"},{"mname":"冰糖","type":0,"amount":"60g"},{"mname":"老抽","type":0,"amount":"适量"},{"mname":"葱","type":0,"amount":"适量"},{"mname":"姜","type":0,"amount":"适量"},{"mname":"桂皮","type":0,"amount":"适量"},{"mname":"八角","type":0,"amount":"适量"},{"mname":"香叶","type":0,"amount":"适量"},{"mname":"盐","type":0,"amount":"适量"},{"mname":"鹌鹑蛋","type":1,"amount":"15个"},{"mname":"五花肉","type":1,"amount":"500g"}],"process":[{"pcontent":"首先将五花肉切块、葱白切段、姜切片。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_23746.jpg"},{"pcontent":"锅中倒入清水，下入五花肉，淋入适量料酒，加入姜、葱，焯出血水，捞入凉水洗净。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_57458.jpg"},{"pcontent":"锅中放入清水，下入鹌鹑蛋，煮熟捞出，入凉水冷却。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_62404.jpg"},{"pcontent":"锅中准备小半锅油，烧至七成热，下入鹌鹑蛋，炸至颜色金黄捞出。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_72751.jpg"},{"pcontent":"锅中放入五花肉，下入姜、葱，倒入六十克冰糖，加入香叶、八角、桂皮、老抽，少许盐，淋入花雕酒，与五花肉相平为宜，盖上锅盖，大火烧开，转小火煮三个小时，倒入鹌鹑蛋，待汤汁收干，即可出锅享用。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085827_60858.jpg"}]}]}
     */

    private int status;
    private String msg;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 162
         * num : 1
         * list : [{"id":752,"classid":5,"name":"外婆红烧肉","peoplenum":"3-4人","preparetime":"10分钟内","cookingtime":"2小时以上","content":"浓油赤酱、咸淡适中、味道醇厚鲜美、肉质Q弹，一口咬下去，肥而不腻，入口之后，一股香气扑鼻而来，是非常惹味的一道上海本帮菜哦！","pic":"http://api.jisuapi.com/recipe/upload/20160719/115420_37020.jpg","tag":"下酒菜,丰胸,五香,健脾开胃,儿童,增强免疫力,增肥,宴请,家常菜,延缓衰老,沪菜,热菜,焖,白领,私房菜,聚会","material":[{"mname":"料酒","type":0,"amount":"适量"},{"mname":"花雕酒","type":0,"amount":"适量"},{"mname":"冰糖","type":0,"amount":"60g"},{"mname":"老抽","type":0,"amount":"适量"},{"mname":"葱","type":0,"amount":"适量"},{"mname":"姜","type":0,"amount":"适量"},{"mname":"桂皮","type":0,"amount":"适量"},{"mname":"八角","type":0,"amount":"适量"},{"mname":"香叶","type":0,"amount":"适量"},{"mname":"盐","type":0,"amount":"适量"},{"mname":"鹌鹑蛋","type":1,"amount":"15个"},{"mname":"五花肉","type":1,"amount":"500g"}],"process":[{"pcontent":"首先将五花肉切块、葱白切段、姜切片。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_23746.jpg"},{"pcontent":"锅中倒入清水，下入五花肉，淋入适量料酒，加入姜、葱，焯出血水，捞入凉水洗净。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_57458.jpg"},{"pcontent":"锅中放入清水，下入鹌鹑蛋，煮熟捞出，入凉水冷却。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_62404.jpg"},{"pcontent":"锅中准备小半锅油，烧至七成热，下入鹌鹑蛋，炸至颜色金黄捞出。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_72751.jpg"},{"pcontent":"锅中放入五花肉，下入姜、葱，倒入六十克冰糖，加入香叶、八角、桂皮、老抽，少许盐，淋入花雕酒，与五花肉相平为宜，盖上锅盖，大火烧开，转小火煮三个小时，倒入鹌鹑蛋，待汤汁收干，即可出锅享用。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085827_60858.jpg"}]}]
         */

        private int total;
        private int num;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 752
             * classid : 5
             * name : 外婆红烧肉
             * peoplenum : 3-4人
             * preparetime : 10分钟内
             * cookingtime : 2小时以上
             * content : 浓油赤酱、咸淡适中、味道醇厚鲜美、肉质Q弹，一口咬下去，肥而不腻，入口之后，一股香气扑鼻而来，是非常惹味的一道上海本帮菜哦！
             * pic : http://api.jisuapi.com/recipe/upload/20160719/115420_37020.jpg
             * tag : 下酒菜,丰胸,五香,健脾开胃,儿童,增强免疫力,增肥,宴请,家常菜,延缓衰老,沪菜,热菜,焖,白领,私房菜,聚会
             * material : [{"mname":"料酒","type":0,"amount":"适量"},{"mname":"花雕酒","type":0,"amount":"适量"},{"mname":"冰糖","type":0,"amount":"60g"},{"mname":"老抽","type":0,"amount":"适量"},{"mname":"葱","type":0,"amount":"适量"},{"mname":"姜","type":0,"amount":"适量"},{"mname":"桂皮","type":0,"amount":"适量"},{"mname":"八角","type":0,"amount":"适量"},{"mname":"香叶","type":0,"amount":"适量"},{"mname":"盐","type":0,"amount":"适量"},{"mname":"鹌鹑蛋","type":1,"amount":"15个"},{"mname":"五花肉","type":1,"amount":"500g"}]
             * process : [{"pcontent":"首先将五花肉切块、葱白切段、姜切片。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_23746.jpg"},{"pcontent":"锅中倒入清水，下入五花肉，淋入适量料酒，加入姜、葱，焯出血水，捞入凉水洗净。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_57458.jpg"},{"pcontent":"锅中放入清水，下入鹌鹑蛋，煮熟捞出，入凉水冷却。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_62404.jpg"},{"pcontent":"锅中准备小半锅油，烧至七成热，下入鹌鹑蛋，炸至颜色金黄捞出。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085826_72751.jpg"},{"pcontent":"锅中放入五花肉，下入姜、葱，倒入六十克冰糖，加入香叶、八角、桂皮、老抽，少许盐，淋入花雕酒，与五花肉相平为宜，盖上锅盖，大火烧开，转小火煮三个小时，倒入鹌鹑蛋，待汤汁收干，即可出锅享用。","pic":"http://api.jisuapi.com/recipe/upload/20160720/085827_60858.jpg"}]
             */

            private int id;
            private int classid;
            private String name;
            private String peoplenum;
            private String preparetime;
            private String cookingtime;
            private String content;
            private String pic;
            private String tag;
            private List<MaterialBean> material;
            private List<ProcessBean> process;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getClassid() {
                return classid;
            }

            public void setClassid(int classid) {
                this.classid = classid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPeoplenum() {
                return peoplenum;
            }

            public void setPeoplenum(String peoplenum) {
                this.peoplenum = peoplenum;
            }

            public String getPreparetime() {
                return preparetime;
            }

            public void setPreparetime(String preparetime) {
                this.preparetime = preparetime;
            }

            public String getCookingtime() {
                return cookingtime;
            }

            public void setCookingtime(String cookingtime) {
                this.cookingtime = cookingtime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public List<MaterialBean> getMaterial() {
                return material;
            }

            public void setMaterial(List<MaterialBean> material) {
                this.material = material;
            }

            public List<ProcessBean> getProcess() {
                return process;
            }

            public void setProcess(List<ProcessBean> process) {
                this.process = process;
            }

            public static class MaterialBean {
                /**
                 * mname : 料酒
                 * type : 0
                 * amount : 适量
                 */

                private String mname;
                private int type;
                private String amount;

                public String getMname() {
                    return mname;
                }

                public void setMname(String mname) {
                    this.mname = mname;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                @Override
                public String toString() {
                    return "MaterialBean{" +
                            "mname='" + mname + '\'' +
                            ", type=" + type +
                            ", amount='" + amount + '\'' +
                            '}';
                }

            }

            public static class ProcessBean {
                /**
                 * pcontent : 首先将五花肉切块、葱白切段、姜切片。
                 * pic : http://api.jisuapi.com/recipe/upload/20160720/085826_23746.jpg
                 */

                private String pcontent;
                private String pic;

                public String getPcontent() {
                    return pcontent;
                }

                public void setPcontent(String pcontent) {
                    this.pcontent = pcontent;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                @Override
                public String toString() {
                    return "ProcessBean{" +
                            "pcontent='" + pcontent + '\'' +
                            ", pic='" + pic + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id=" + id +
                        ", classid=" + classid +
                        ", name='" + name + '\'' +
                        ", peoplenum='" + peoplenum + '\'' +
                        ", preparetime='" + preparetime + '\'' +
                        ", cookingtime='" + cookingtime + '\'' +
                        ", content='" + content + '\'' +
                        ", pic='" + pic + '\'' +
                        ", tag='" + tag + '\'' +
                        ", material=" + material +
                        ", process=" + process +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "total=" + total +
                    ", num=" + num +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RecipesBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
