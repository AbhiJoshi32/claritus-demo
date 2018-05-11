
package com.example.claritus.claritus.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("API_CURRENT_TOKEN")
    private String APICURRENTTOKEN;

    public String getAPICURRENTTOKEN() {
        return APICURRENTTOKEN;
    }

    public void setAPICURRENTTOKEN(String APICURRENTTOKEN) {
        this.APICURRENTTOKEN = APICURRENTTOKEN;
    }

}
