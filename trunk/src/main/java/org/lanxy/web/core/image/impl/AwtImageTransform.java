package org.lanxy.web.core.image.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:53
 */
public class AwtImageTransform {

    private static final Logger logger = LoggerFactory.getLogger(AwtImageTransform.class);
    private BufferedImage srcBImage;
    private BufferedImage destBImage;
    private int imgWidth;
    private int imgHeight;

    public boolean load(String srcFilePath) {
        try {
            srcBImage = ImageIO.read(new java.io.File(srcFilePath));
            imgWidth = srcBImage.getWidth();
            imgHeight = srcBImage.getHeight();
        } catch (IOException e) {
            logger.info("Cannot open image [{}]", srcFilePath);
            return false;
        }
        return true;
    }

    public boolean save(String destFilePath) {
        if (destBImage == null)
            return false;
        try {
            String postfix = destFilePath.substring(destFilePath.lastIndexOf(".") + 1);
            ImageIO.write(destBImage, postfix, new File(destFilePath));
        } catch (IOException e) {
            logger.info("Cannot open image [{}]", destFilePath);
            return false;
        }
        return true;
    }

    public void resize(int width, int height) {
        double xRatio = (double) height / imgHeight;
        double yRatio = (double) width / imgWidth;
        double ratio = (xRatio < yRatio) ? xRatio : yRatio;
        zoomImage(imgWidth, imgHeight, ratio);
    }

    public void rotate(double rotate) {
        rotateImage(imgWidth, imgHeight, rotate, 0);
    }

    public void resizeWithMaxWidth(int maxWidth) {
        if (imgWidth > maxWidth) {
            double ratio = (double) maxWidth / imgWidth;
            zoomImage(imgWidth, imgHeight, ratio);
        } else
            destBImage = srcBImage;
    }

    public void rotateWithMaxWidth(double rotate, int maxWidth) {
        rotateImage(imgWidth, imgHeight, rotate, maxWidth);
    }

    private void zoomImage(int width, int height, double ratio) {
        try {
            int w = (int) (width * ratio);
            int h = (int) (height * ratio);
            destBImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = destBImage.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, w, h);
            destBImage.getGraphics().drawImage(srcBImage.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);

        } catch (Exception e) {
            throw new ImagingOpException("Unable to transform src image");
        }
    }

    private void rotateImage(int imgWidth, int imgHeight, double rotate, int maxWidth) {
        if (rotate < 1e-3) {
            return;
        }
        try {
            double radian = rotate * Math.PI / 180;  //get radian
            double w = Math.abs(imgHeight * Math.sin(radian)) + Math.abs(imgWidth * Math.cos(radian));  //get rotated height
            double h = Math.abs(imgHeight * Math.cos(radian)) + Math.abs(imgWidth * Math.sin(radian));  //get rotated width
            double ratio = 1;
            if (maxWidth > 0) {
                ratio = (double) maxWidth / w;
            }
            AffineTransform transform = new AffineTransform();
            transform.setToScale(ratio, ratio);
            transform.rotate(radian, w / 2, h / 2);
            transform.translate(w / 2 - imgWidth / 2, h / 2 - imgHeight / 2);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            w *= ratio;
            h *= ratio;
            destBImage = new BufferedImage((int) w, (int) h, srcBImage.getType());
            Graphics gs = destBImage.getGraphics();
            gs.setColor(Color.white); //set canvas background to white
            gs.fillRect(0, 0, (int) w, (int) h);
            ato.filter(srcBImage, destBImage);
        } catch (Exception e) {
            throw new ImagingOpException("Unable to transform src image");
        }
    }

}
