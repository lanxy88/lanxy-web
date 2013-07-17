package org.lanxy.web.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-1-22 上午9:21
 */
public final class FilesUtils {

    public static final String IMAGE_PNG = "png";
    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_BMP = "bmp";
    public static final String IMAGE_GIF = "gif";
    public static final String IMAGE_TIF = "tif";

    static List<String> images = new ArrayList<String>();

    static {
        images.add(IMAGE_PNG);
        images.add(IMAGE_JPG);
        images.add(IMAGE_BMP);
        images.add(IMAGE_GIF);
        images.add(IMAGE_TIF);
    }

    /**
     * 判别文件是否为图片
     *
     * @param file
     * @return
     */
    public static final boolean isImage(File file) {
        Assert.notNull(file, "文件不可为空");
        String suffix = file.getPath().substring(file.getPath().lastIndexOf(".") + 1, file.getPath().length());
        if (StringUtils.isBlank(suffix)) return false;
        if (images.contains(suffix)) return true;
        return false;
    }

}
