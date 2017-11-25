package ir.jahanmir.araxx.model;

/**
 * Created by FCC on 11/7/2017.
 */

public class ModelRegisterCustomerResponse {

    int customerCode;
    String userName;
    String password;
    String Message;
    int Result;

    public int getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public ModelRegisterCustomerResponse(int customerCode, String userName, String password, String message, int result) {

        this.customerCode = customerCode;
        this.userName = userName;
        this.password = password;
        Message = message;
        Result = result;
    }
}
