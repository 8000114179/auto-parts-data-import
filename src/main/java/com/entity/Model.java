package com.entity;

/**
 *
 * @author Max
 * @since 2018/4/23
 */
public class Model {
    private Long vehicle_type_id;
    private String vehicle_type_number;
    private String sales_name;
    private Long brand_id;
    private Long carmarker_id;
    private Long series_id;
    private Long tag_id;
    private Long energy_type_id;
    private Long gear_box_number_id;
    private Long drive_type_id;
    private Long origin_type_id;
    private Long car_type_id;
    private Long front_tyre_type_id;
    private Long rear_tyre_type_id;
    private Long body_type_id;
    private String displacement;
    private String generation_number;
    private String engine_number;
    private String chassis_number;
    private String max_power;

    private String productive_year;
    private String discontinued_year;
    private String sales_year;
    private String search_words;

    private Integer status;
    private String relation;
    private String remark;
    private String create_time;
    private String update_time;


    public Long getEnergy_type_id() {
        return energy_type_id;
    }

    public void setEnergy_type_id(Long energy_type_id) {
        this.energy_type_id = energy_type_id;
    }

    public Long getGear_box_number_id() {
        return gear_box_number_id;
    }

    public void setGear_box_number_id(Long gear_box_number_id) {
        this.gear_box_number_id = gear_box_number_id;
    }

    public Long getOrigin_type_id() {
        return origin_type_id;
    }

    public void setOrigin_type_id(Long origin_type_id) {
        this.origin_type_id = origin_type_id;
    }

    public Long getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(Long car_type_id) {
        this.car_type_id = car_type_id;
    }

    public Long getFront_tyre_type_id() {
        return front_tyre_type_id;
    }

    public void setFront_tyre_type_id(Long front_tyre_type_id) {
        this.front_tyre_type_id = front_tyre_type_id;
    }

    public Long getRear_tyre_type_id() {
        return rear_tyre_type_id;
    }

    public void setRear_tyre_type_id(Long rear_tyre_type_id) {
        this.rear_tyre_type_id = rear_tyre_type_id;
    }

    public String getGeneration_number() {
        return generation_number;
    }

    public void setGeneration_number(String generation_number) {
        this.generation_number = generation_number;
    }

    public String getSearch_words() {
        return search_words;
    }

    public void setSearch_words(String search_words) {
        this.search_words = search_words;
    }

    public Long getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(Long vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getVehicle_type_number() {
        return vehicle_type_number;
    }

    public void setVehicle_type_number(String vehicle_type_number) {
        this.vehicle_type_number = vehicle_type_number;
    }

    public String getSales_name() {
        return sales_name;
    }

    public void setSales_name(String sales_name) {
        this.sales_name = sales_name;
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }

    public Long getCarmarker_id() {
        return carmarker_id;
    }

    public void setCarmarker_id(Long carmarker_id) {
        this.carmarker_id = carmarker_id;
    }

    public Long getSeries_id() {
        return series_id;
    }

    public void setSeries_id(Long series_id) {
        this.series_id = series_id;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getMax_power() {
        return max_power;
    }

    public void setMax_power(String max_power) {
        this.max_power = max_power;
    }

    public String getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(String engine_number) {
        this.engine_number = engine_number;
    }

    public String getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(String chassis_number) {
        this.chassis_number = chassis_number;
    }

    public Long getDrive_type_id() {
        return drive_type_id;
    }

    public void setDrive_type_id(Long drive_type_id) {
        this.drive_type_id = drive_type_id;
    }

    public Long getBody_type_id() {
        return body_type_id;
    }

    public void setBody_type_id(Long body_type_id) {
        this.body_type_id = body_type_id;
    }

    public String getSales_year() {
        return sales_year;
    }

    public void setSales_year(String sales_year) {
        this.sales_year = sales_year;
    }

    public String getProductive_year() {
        return productive_year;
    }

    public void setProductive_year(String productive_year) {
        this.productive_year = productive_year;
    }

    public String getDiscontinued_year() {
        return discontinued_year;
    }

    public void setDiscontinued_year(String discontinued_year) {
        this.discontinued_year = discontinued_year;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
