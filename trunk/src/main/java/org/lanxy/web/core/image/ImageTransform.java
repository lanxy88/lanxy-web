package org.lanxy.web.core.image;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:49
 */
public interface ImageTransform {

    public boolean load(String srcFilePath);

    public boolean save(String destFilePath);

    public void resize(int width, int height) throws Exception;

    public void rotate(double rotate) throws Exception;

    public void resizeWithMaxWidth(int maxWidth) throws Exception;

    public void rotateWithMaxWidth(double rotate, int maxWidth) throws Exception;

}
