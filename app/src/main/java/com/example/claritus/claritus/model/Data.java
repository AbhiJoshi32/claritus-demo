
package com.example.claritus.claritus.model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("API_CURRENT_TOKEN")
    private String mAPICURRENTTOKEN;

    public String getAPICURRENTTOKEN() {
        return mAPICURRENTTOKEN;
    }

    public void setAPICURRENTTOKEN(String APICURRENTTOKEN) {
        mAPICURRENTTOKEN = APICURRENTTOKEN;
    }

}
