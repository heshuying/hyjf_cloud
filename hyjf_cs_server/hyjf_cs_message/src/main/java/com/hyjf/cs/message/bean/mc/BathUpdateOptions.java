/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class BathUpdateOptions {

    private Query query;
    private Update update;
    private boolean upsert = true;
    private boolean multi = false;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public boolean isUpsert() {
        return upsert;
    }

    public void setUpsert(boolean upsert) {
        this.upsert = upsert;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

}