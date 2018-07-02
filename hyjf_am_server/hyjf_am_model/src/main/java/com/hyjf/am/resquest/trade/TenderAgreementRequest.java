/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

/**
 * @author jun
 * @version TenderAgreementRequest, v0.1 2018/6/30 11:16
 */
public class TenderAgreementRequest extends Request {

    private String tenderAgreementID;
    private String imgUrl;
    private String pdfUrl;


    public void setTenderAgreementID(String tenderAgreementID) {
        this.tenderAgreementID = tenderAgreementID;
    }

    public String getTenderAgreementID() {
        return tenderAgreementID;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }
}
