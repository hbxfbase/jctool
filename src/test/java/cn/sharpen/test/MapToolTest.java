package cn.sharpen.test;

import cn.hutool.core.collection.ListUtil;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.MapTool;
import cn.sharpen.jctool.util.StrTool;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapToolTest {

    @Data
    static class User {
        private String name;
        private String pwd;
        private String address;

        public static User inst(String name, String pwd){
            User user = new User();
            user.name= name;
            user.pwd = pwd;
            return user;
        }
        public static User inst(String name, String pwd, String address){
            User user = new User();
            user.name= name;
            user.pwd = pwd;
            user.address = address;
            return user;
        }
    }

    @Test
    public void beans2mapListTest(){
        ArrayList<User> list = ListUtil.toList(User.inst("aa", "a2"),User.inst("bb", "b2", "北京"));
        List<Map<String, Object>> mapList = MapTool.beans2mapList(list, ListUtil.toList("name","address"));
        StrTool.pl("beans2mapListTest=" + JsonTool.obj2json(mapList));
    }
}
