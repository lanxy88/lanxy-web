package org.lanxy.web.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-13 下午4:02
 */
public class BaseLogger {

    /**
     * base logger
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MessageSource message;

    /**
     * 获取message
     *
     * @param key
     * @param param
     * @return
     */
    protected String getMessage(String key, Object... param) {
        return message.getMessage(key, param, Locale.getDefault());
    }

}
