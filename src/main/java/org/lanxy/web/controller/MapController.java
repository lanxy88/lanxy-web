package org.lanxy.web.controller;

import org.lanxy.web.core.log.BaseLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
