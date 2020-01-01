package com.cpyh.Service;

import com.cpyh.Catalogue.FCB;

import java.util.Map;

public class Login {
    private String nowUser;
    public Login(String nowUser){
        this.nowUser=nowUser;
    }
    //考虑要不要密码，算了

    public String getNowUser() {
        return nowUser;
    }

    public void setNowUser(String nowUser) {
        this.nowUser = nowUser;
    }

    public String changeUser(String User, Map<String, FCB>totalUser){
        if(totalUser.containsKey(User)){
            setNowUser(User);
            return getNowUser();
        }else{
            return "没有该用户";
        }
    }
}
