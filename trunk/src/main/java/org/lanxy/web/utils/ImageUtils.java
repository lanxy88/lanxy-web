package org.lanxy.web.utils;

import org.lanxy.web.core.image.ImageTransform;
import org.lanxy.web.core.image.ImageTransformFactory;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:47
 */
public final class ImageUtils {
    /**
     * use imageTransform to resize,rotate image.
     *
     * @param srcFilePath  源文件路径,eg D:\\ccs\\web\\upload_images\\original\\1.jpg
     * @param destFilePath 目标文件路径,eg D:\\ccs\\web\\upload_images\\big\\1.jpg
     * @param width        宽度
     * @param height       高度
     * @throws Exception Exception
     */
    public static void resizeImage(String srcFilePath, String destFilePath, int width, int height) throws Exception {
        ImageTransform it = ImageTransformFactory.getImageTransform();

        it.load(srcFilePath);
        it.resize(width, height);
        it.save(destFilePath);
    }

    public static void resizeImageWithMaxWidth(String srcFilePath, String destFilePath, int maxWidth) throws Exception {
        ImageTransform it = ImageTransformFactory.getImageTransform();

        it.load(srcFilePath);
        it.resizeWithMaxWidth(maxWidth);
        it.save(destFilePath);
    }

    public static void rotateImageWithMaxWidth(String srcFilePath, String destFilePath, double rotate, int maxWidth)
            throws Exception {
        ImageTransform it = ImageTransformFactory.getImageTransform();

        it.load(srcFilePath);
        it.rotateWithMaxWidth(rotate, maxWidth);
        it.save(destFilePath);
    }

    public static void rotateImage(String srcFilePath, String destFilePath, double rotate) throws Exception {
        ImageTransform it = ImageTransformFactory.getImageTransform();

        it.load(srcFilePath);
        it.rotate(rotate);
        it.save(destFilePath);
    }
}
