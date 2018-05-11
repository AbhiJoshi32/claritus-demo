
package com.example.claritus.claritus.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegistrationResponse {

    @SerializedName("api_current_token")
    private String mApiCurrentToken;
    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<Object> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("reason")
    private Object mReason;
    @SerializedName("status")
    private Long mStatus;

    public String getApiCurrentToken() {
        return mApiCurrentToken;
    }

    public void setApiCurrentToken(String apiCurrentToken) {
        mApiCurrentToken = apiCurrentToken;
    }

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public List<Object> getData() {
        return mData;
    }

    public void setData(List<Object> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Object getReason() {
        return mReason;
    }

    public void setReason(Object reason) {
        mReason = reason;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
