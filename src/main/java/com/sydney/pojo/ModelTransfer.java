package com.sydney.pojo;

import javax.persistence.Column;

/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/18 16:31
 */
public class ModelTransfer {
    //    private String engineName;
//    private String modelName;
//    private String dataSetName;
//    private String pronDictMapName;
    @Column(name = "ModelID")
    private Integer modelId;
    @Column(name = "ModelName")
    private String modelName;
    @Column(name = "ModelDescription")
    private String modelDescription;
    @Column(name = "Accuracy")
    private Float accuracy;
    @Column(name = "Deletions")
    private Float deletions;
    @Column(name = "Insertions")
    private Float insertions;
    @Column(name = "Substitutions")
    private Float substitutions;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Float getDeletions() {
        return deletions;
    }

    public void setDeletions(Float deletions) {
        this.deletions = deletions;
    }

    public Float getInsertions() {
        return insertions;
    }

    public void setInsertions(Float insertions) {
        this.insertions = insertions;
    }

    public Float getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(Float substitutions) {
        this.substitutions = substitutions;
    }
}
