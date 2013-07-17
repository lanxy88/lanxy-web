package org.lanxy.web.core.image;

import org.lanxy.web.core.log.BaseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:50
 */
public class ImageTransformFactory {

    private static final Logger logger = LoggerFactory.getLogger(ImageTransformFactory.class);

    /**
     * 默认转换工具类的名称.
     */
    public static final String defaultImageTransform = "org.lanxy.web.core.image.impl.AwtImageTransform";

    /**
     * 图像转换的实现类.
     */
    private static Class imageTransformClazz;

    /**
     * 同步对象.
     */
    private static final Object imageTransformLock = new Object();

    /**
     * 设置图像转换的实现类.
     *
     * @param className 图像转换的实现类.
     */
    public static void setTransformProvider(final String className) {
        try {
            imageTransformClazz = Class.forName(className);
        } catch (Exception e) {
            logger.error("Invalid provider class of image transforming.", e);
        }
    }

    /**
     * 获取图像转换的实现类.
     *
     * @return 图像转换的实现类.
     */
    public static ImageTransform getImageTransform() {
        if (imageTransformClazz == null) {
            synchronized (imageTransformLock) {
                // double check.
                if (imageTransformClazz == null) {
                    setTransformProvider(defaultImageTransform);
                }
            }
        }
        try {
            return (ImageTransform) imageTransformClazz.newInstance();
        } catch (Exception e) {
            logger.error("instance class error", e);
        }
        return null;
    }
}
