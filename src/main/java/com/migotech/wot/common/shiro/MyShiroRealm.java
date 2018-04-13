package com.migotech.wot.common.shiro;

import com.migotech.wot.user.dao.UserDao;
import com.migotech.wot.user.model.User;
import com.migotech.wot.util.MD5Util;
import com.migotech.wot.util.SpringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //获取用户角色
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);

        //获取用户权限
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限添加");
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);


        return info;
    }

    /**
     * 验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        String md5pwd = MD5Util.md5WithSalt(password);

        UserDao userDao = SpringUtils.getBean("userDao");
        User user = userDao.login(username, md5pwd);

        if(user == null){
            throw new AuthenticationException("Name or password is wrong");
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
