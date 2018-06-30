package com.hyjf.am.vo.user;

import java.io.Serializable;

public class MspDegreeresultVO implements Serializable {
    private Integer id;

    private String applyId;

    private String status;

    private String school;

    private String degree;

    private String admissionyear;

    private String major;

    private String graduationtime;

    private String graduationconclusion;

    private String degreetype;

    private String schoolnature;

    private String photobase64code;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getAdmissionyear() {
        return admissionyear;
    }

    public void setAdmissionyear(String admissionyear) {
        this.admissionyear = admissionyear == null ? null : admissionyear.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getGraduationtime() {
        return graduationtime;
    }

    public void setGraduationtime(String graduationtime) {
        this.graduationtime = graduationtime == null ? null : graduationtime.trim();
    }

    public String getGraduationconclusion() {
        return graduationconclusion;
    }

    public void setGraduationconclusion(String graduationconclusion) {
        this.graduationconclusion = graduationconclusion == null ? null : graduationconclusion.trim();
    }

    public String getDegreetype() {
        return degreetype;
    }

    public void setDegreetype(String degreetype) {
        this.degreetype = degreetype == null ? null : degreetype.trim();
    }

    public String getSchoolnature() {
        return schoolnature;
    }

    public void setSchoolnature(String schoolnature) {
        this.schoolnature = schoolnature == null ? null : schoolnature.trim();
    }

    public String getPhotobase64code() {
        return photobase64code;
    }

    public void setPhotobase64code(String photobase64code) {
        this.photobase64code = photobase64code == null ? null : photobase64code.trim();
    }
}