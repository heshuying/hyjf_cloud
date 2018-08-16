package com.hyjf.cs.user.result;

import java.io.Serializable;

/**
 * @author fuqiang
 */
public class BindCardResultBean extends BaseResultBeanFrontEnd {
    BindBeanDeatail formData;

    public BindBeanDeatail getFormData() {
        return formData;
    }

    public void setFormData(BindBeanDeatail formData) {
        this.formData = formData;
    }
}


