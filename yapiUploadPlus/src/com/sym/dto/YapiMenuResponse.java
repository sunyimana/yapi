package com.sym.dto;

import java.util.List;

public class YapiMenuResponse {

    private Long count;

    private Long total;

    private List<YapiDataResponse> list;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<YapiDataResponse> getList() {
        return list;
    }

    public void setList(List<YapiDataResponse> list) {
        this.list = list;
    }
}
