package com.hyjf.cs.user.bean;

import com.hyjf.am.response.WrbResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version WrbNoticeinfoResponse, v0.1 2018/9/20 14:37
 */

public class WrbNoticeinfoResponse extends WrbResponse {
    private List<NoticeinfoDetail> all_notices;

    public WrbNoticeinfoResponse(){
        super();
        this.all_notices = new ArrayList<>();
    }

    public List<NoticeinfoDetail> getAll_notices() {
        return all_notices;
    }

    public void setAll_notices(List<NoticeinfoDetail> all_notices) {
        this.all_notices = all_notices;
    }


    public static final class NoticeinfoDetail {

        private String id;
        /*公告的标题*/
        private String title;
        /*公告的路径*/
        private String url;
        /*公告的内容*/
        private String content;
        /*公告发布时间*/
        private String release_time;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getRelease_time() {
            return release_time;
        }
        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }


    }

}
