package com.hyjf.am.config.dao.model.customize;

/**
 * @author lisheng
 * @version HelpContentCustomize, v0.1 2018/7/16 16:21
 */

public class HelpContentCustomize {
    //帮助id
    public String id;
    //帮助所属id
    public String   pcate_id;
    //帮助子类id
    public String  cate_id;
    //问题标题
    public String  title;
    //问题内容
    public String   content;
    //排序
    public String   order;
    //状态  0启用 1开启
    public String  status;
    //是否单页1单页，0非单页
    public String  type;
    //别名
    public String  code;
    //添加时间
    public String  create_time;
    //更新时间
    public String  update_time;
    //文章来源
    public String  source;
    //作者
    public String  author;
    //缩略图
    public String thumb;
    //   简要介绍
    public String summary;
    //   简要介绍
    public String seo_title;
    public String seo_keyword;
    public String seo_description;
    //  点击数
    public String hits;
    public String out_link;
    //特殊返回页面要生成的ID
    public String  itemId;
    //特殊返回页面要生成的分类ID
    public String  typeId;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPcate_id() {
        return pcate_id;
    }
    public void setPcate_id(String pcate_id) {
        this.pcate_id = pcate_id;
    }
    public String getCate_id() {
        return cate_id;
    }
    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getThumb() {
        return thumb;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getSeo_title() {
        return seo_title;
    }
    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }
    public String getSeo_keyword() {
        return seo_keyword;
    }
    public void setSeo_keyword(String seo_keyword) {
        this.seo_keyword = seo_keyword;
    }
    public String getSeo_description() {
        return seo_description;
    }
    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }
    public String getHits() {
        return hits;
    }
    public void setHits(String hits) {
        this.hits = hits;
    }
    public String getOut_link() {
        return out_link;
    }
    public void setOut_link(String out_link) {
        this.out_link = out_link;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
