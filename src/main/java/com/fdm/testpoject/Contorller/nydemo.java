package com.fdm.testpoject.Contorller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fdm
 * @date 2019/11/23 15:06
 * @Description:
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class nydemo {
    @RequestMapping(name = "get")
    public  List<Map<String,String>> getimg(){
        List<Map<String,String>> list = new ArrayList<>();
        List<String> urls  = new ArrayList<>();
        urls.add("http://picd70.huitu.com/res/20161031/20161031084510234800_1.jpg");
        urls.add("http://b-ssl.duitang.com/uploads/item/201604/23/20160423165323_k4rhF.jpeg");
        urls.add("http://img17.3lian.com/d/file/201702/09/54fa2e4df6867f09b2df42e411d3bbdf.jpg");
        urls.add("http://img.juimg.com/tuku/yulantu/120326/2910-12032614450256.jpg");
        urls.add("http://clubimg.club.vmall.com/data/attachment/forum/201812/21/215626ci9yvgddppv53slw.jpg");
        urls.add("http://pic1.win4000.com/wallpaper/8/589c2f0f014ba.jpg");
        urls.add("http://f.01ny.cn/forum/201308/11/173240r2b2dwlhlbf7kfuf.jpg");
        urls.add("http://img.taopic.com/uploads/allimg/140720/240467-140H00K62786.jpg");
        urls.add("http://img.tupianzj.com/uploads/allimg/180808/23-1PPQ20220-50.jpg");
        urls.add("http://img.taopic.com/uploads/allimg/140415/240490-1404150GZ381.jpg");
        for (int i = 0;i<10;i++){
            Map<String,String> map = new HashMap<>();
            map.put("url",urls.get(i));
            map.put("text","手机"+i);
            list.add(map);
        }
        return list;

    }
}
