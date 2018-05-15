package com.entity;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Max
 * @since 2018/4/23
 */
public class Series {
    private String seriesName;
    private Long seriesId;
    private Set<String> modelSet;
    private List<Model> models;

    public Set<String> getModelSet() {
        return modelSet;
    }

    public void setModelSet(Set<String> modelSet) {
        this.modelSet = modelSet;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
