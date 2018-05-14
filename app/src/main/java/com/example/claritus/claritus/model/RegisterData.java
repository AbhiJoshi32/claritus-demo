
package com.example.claritus.claritus.model;
import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("requireUserVerification")
    private String RequireUserVerification;
    @SerializedName("token")
    private String Token;
    @SerializedName("verificationMethod")
    private String VerificationMethod;
    @SerializedName("verificationMethodName")
    private String VerificationMethodName;

    public String getRequireUserVerification() {
        return RequireUserVerification;
    }

    public void setRequireUserVerification(String requireUserVerification) {
        RequireUserVerification = requireUserVerification;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getVerificationMethod() {
        return VerificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        VerificationMethod = verificationMethod;
    }

    public String getVerificationMethodName() {
        return VerificationMethodName;
    }

    public void setVerificationMethodName(String verificationMethodName) {
        VerificationMethodName = verificationMethodName;
    }

}
