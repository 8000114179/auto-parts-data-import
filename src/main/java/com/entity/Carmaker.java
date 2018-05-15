package com.entity;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Max
 * @since 2018/4/23
 */
public class Carmaker {
    private String armakerName;
    private Long carmakerId;
    private Set<String> seriesSet;

    public Set<String> getSeriesSet() {
        return seriesSet;
    }

    public void setSeriesSet(Set<String> seriesSet) {
        this.seriesSet = seriesSet;
    }

    private List<Series> series;

    public String getArmakerName() {
        return armakerName;
    }

    public void setArmakerName(String armakerName) {
        this.armakerName = armakerName;
    }

    public Long getCarmakerId() {
        return carmakerId;
    }

    public void setCarmakerId(Long carmakerId) {
        this.carmakerId = carmakerId;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
