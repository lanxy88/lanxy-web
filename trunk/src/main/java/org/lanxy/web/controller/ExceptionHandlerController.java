package org.lanxy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-12 下午5:34
 */
@Controller
public class ExceptionHandlerController {

    @RequestMapping(value = "/uncatchEx")
    public String uncatchException(){
         return "common/uncatch";
    }

    @RequestMapping(value = "/_404")
    public String error404Exception(){
        return "common/404";
    }



}
