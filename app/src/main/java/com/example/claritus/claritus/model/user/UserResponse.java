
package com.example.claritus.claritus.model.user;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("api_current_token")
    private String ApiCurrentToken;
    @SerializedName("code")
    private Long Code;
    @SerializedName("data")
    private com.example.claritus.claritus.model.user.User Data;
    @SerializedName("message")
    private String Message;
    @SerializedName("reason")
    private Object Reason;
    @SerializedName("status")
    private Long Status;

    public String getApiCurrentToken() {
        return ApiCurrentToken;
    }

    public void setApiCurrentToken(String apiCurrentToken) {
        ApiCurrentToken = apiCurrentToken;
    }

    public Long getCode() {
        return Code;
    }

    public void setCode(Long code) {
        Code = code;
    }

    public com.example.claritus.claritus.model.user.User getData() {
        return Data;
    }

    public void setData(com.example.claritus.claritus.model.user.User data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getReason() {
        return Reason;
    }

    public void setReason(Object reason) {
        Reason = reason;
    }

    public Long getStatus() {
        return Status;
    }

    public void setStatus(Long status) {
        Status = status;
    }

}
