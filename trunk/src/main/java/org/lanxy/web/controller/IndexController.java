package org.lanxy.web.controller;

import org.lanxy.web.core.log.BaseLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-11 下午5:39
 */
@Controller
public class IndexController extends BaseLogger {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        logger.info("index request");
        return "index";
    }
}
