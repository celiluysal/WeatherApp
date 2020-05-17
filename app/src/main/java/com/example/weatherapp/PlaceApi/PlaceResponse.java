
package com.example.weatherapp.PlaceApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceResponse {

    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;
    @SerializedName("nbHits")
    @Expose
    private Integer nbHits;
    @SerializedName("processingTimeMS")
    @Expose
    private Integer processingTimeMS;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("params")
    @Expose
    private String params;
    @SerializedName("degradedQuery")
    @Expose
    private Boolean degradedQuery;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public Integer getNbHits() {
        return nbHits;
    }

    public void setNbHits(Integer nbHits) {
        this.nbHits = nbHits;
    }

    public Integer getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(Integer processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Boolean getDegradedQuery() {
        return degradedQuery;
    }

    public void setDegradedQuery(Boolean degradedQuery) {
        this.degradedQuery = degradedQuery;
    }

}
