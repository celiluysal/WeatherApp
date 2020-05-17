
package com.example.weatherapp.PlaceApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class County {

    @SerializedName("default")
    @Expose
    private List<String> _default = null;

    public List<String> getDefault() {
        return _default;
    }

    public void setDefault(List<String> _default) {
        this._default = _default;
    }

}
