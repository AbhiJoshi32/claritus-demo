
package com.example.claritus.claritus.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegisterResponse {

    @SerializedName("api_current_token")
    private String ApiCurrentToken;
    @SerializedName("code")
    private Long Code;
    @SerializedName("data")
    private RegisterData RegisterData;
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

    public RegisterData getRegisterData() {
        return RegisterData;
    }

    public void setRegisterData(RegisterData registerData) {
        RegisterData = registerData;
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
