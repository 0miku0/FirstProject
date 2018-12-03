package WebStore.bean;

import WebStore.service.implments.MyUserService;
import WebStore.service.interfaces.UserService;

import java.sql.SQLException;

public class Order {
    int oid;
    String money;
    String recipients;
    String tel;
    String address;
    String state;
    String orderTime;
    int uid;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Order() {
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) throws SQLException {
        this.uid = uid;
        UserService userService=new MyUserService();
        User userByUid = userService.getUserByUid(uid + "");
        setUsername(userByUid.getUsername());
    }
}
