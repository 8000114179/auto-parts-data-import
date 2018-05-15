package com.entity;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Max
 * @since 2018/4/23
 */
public class Brand {
    private String brandName;
    private Long brandId;
    private Set<String> carmakerSet;
    private List<Carmaker> carmakers;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Set<String> getCarmakerSet() {
        return carmakerSet;
    }

    public void setCarmakerSet(Set<String> carmakerSet) {
        this.carmakerSet = carmakerSet;
    }

    public List<Carmaker> getCarmakers() {
        return carmakers;
    }

    public void setCarmakers(List<Carmaker> carmakers) {
        this.carmakers = carmakers;
    }
}
