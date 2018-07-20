package com.hyjf.am.config.dao.model.customize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version HelpCategoryCustomize, v0.1 2018/7/16 16:19
 */

public class HelpCategoryCustomize {
    //帮组类别id
    public Integer   id;
    //帮组类别标题
    public String  title;
    //帮组类别父id
    public Integer pid;

    public String code;
    //帮组类别分组
    public String group;
    //帮组类别排序
    public Integer  sort;
    //帮组类别是否隐藏 0显示 1隐藏
    public Integer  hide;
    //帮组类别提示
    public String tip;
    //帮组类别等级
    public Integer  level;
    //特殊返回页面要生成的ID
    public String  itemId;
    //内容
    public List<HelpContentCustomize> listsunContent = new ArrayList<HelpContentCustomize>();

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getHide() {
        return hide;
    }
    public void setHide(Integer hide) {
        this.hide = hide;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public List<HelpContentCustomize> getListsunContent() {
        return listsunContent;
    }
    public void setListsunContent(List<HelpContentCustomize> listsunContent) {
        this.listsunContent = listsunContent;
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
