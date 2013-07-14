package org.lanxy.web.controller;

import org.lanxy.web.core.log.BaseLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-13 下午4:01
 */
@Controller
@RequestMapping(value = "/map")
public class MapController extends BaseLogger {

    @RequestMapping(value = "/index")
    public String index() {
        return "map";
    }

    @RequestMapping(value = "/config")
    @ResponseBody
    public Object config() {
        Map map = new HashMap();
        map.put("name", "value");
        map.put("name1", "value");
        map.put("name2", "value");
        map.put("name3", "中文值value");
        return map;
    }

    @RequestMapping(value = "/string")
    @ResponseBody
    public String toString2() {
        String v = "中文值value";
        return v;
    }

}
