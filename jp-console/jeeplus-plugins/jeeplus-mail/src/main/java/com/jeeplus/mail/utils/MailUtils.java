package com.jeeplus.mail.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class MailUtils {


    /**
     * 获取收件人用户Name
     *
     * @return
     */
    public static String getReceiverNames (String receiverIds) {
        if( StrUtil.isBlank(receiverIds)){
            return "";
        }
        List <UserDTO> receiverList = Lists.newArrayList();
        for (String id : StrUtil.split(receiverIds, ",")) {

            receiverList.add( SpringUtil.getBean( UserService.class).get(id));
        }
        List<String> receiverNames = receiverList.stream ().filter ( userDTO -> {
            return  userDTO !=null && StrUtil.isNotBlank ( userDTO.getName () );
        } ).map ( userDTO -> {
            return userDTO.getName ();
        } ).collect( Collectors.toList());
        return StrUtil.join (  ",", receiverNames );
    }

    /**
     * 获取收件人用户
     *
     * @return
     */

    public static List<User> getReceiverList(String receiverIds) {
        List receiverList = Lists.newArrayList();
        if(StrUtil.isBlank(receiverIds)){
            return receiverList;
        }
        for (String id : StrUtil.split(receiverIds, ",")) {

            receiverList.add(new User(id));
        }
        return receiverList;
    }
}
