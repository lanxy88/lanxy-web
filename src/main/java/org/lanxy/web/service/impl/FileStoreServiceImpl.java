package org.lanxy.web.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.lanxy.web.core.log.BaseLogger;
import org.lanxy.web.dao.FileStoreDao;
import org.lanxy.web.entity.FileStore;
import org.lanxy.web.service.FileStoreService;
import org.lanxy.web.utils.FilesUtils;
import org.lanxy.web.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:10
 */
@Service
public class FileStoreServiceImpl extends BaseLogger implements FileStoreService {

    private static final int THRESHOLD = 1024 * 1024 * 10;

    private static final String THUMB_SUFFIX = "_thumb";

    @Autowired
    private FileStoreDao fileStoreDao;

    private Resource baseLocation;
    private Resource location;
    private Resource tempFile;
    private long maxSize;

    /**
     * get one
     *
     * @param id
     * @return
     */
    @Override
    public FileStore get(String id) {
        return fileStoreDao.findOne(id);
    }

    /**
     * save
     *
     * @param request
     * @param parentId
     * @return
     */
    @Override
    public FileStore save(HttpServletRequest request, String parentId) {
        Assert.notNull(parentId, getMessage("file.parentId.notnull"));
        FileStore fileStore = null;
        try {
            List<FileItem> fileItems = parseRequest(request);
            for (FileItem item : fileItems) {
                if (!item.isFormField()) {
                    DiskFileItem diskFileItem = (DiskFileItem) item;
                    File newFile = getNewFile(item.getName());
                    if (diskFileItem.isInMemory()) {
                        FileUtils.copyInputStreamToFile(diskFileItem.getInputStream(), newFile);
                    } else {
                        File tmpFile = diskFileItem.getStoreLocation();
                        FileUtils.copyFile(tmpFile, newFile, true);
                    }
                    createThumb(newFile);
                    fileStore = save2DB(newFile, parentId, diskFileItem);
                    logger.debug(getMessage("file.upload.success",item.getName()));
                }
            }
        } catch (FileUploadException e) {
            logger.error(getMessage("file.upload.error",e.getLocalizedMessage()));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return fileStore;
    }

    /**
     * delete
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        return false;
    }

    /**
     * delete
     *
     * @param fileStore
     * @return
     */
    @Override
    public boolean delete(FileStore fileStore) {
        return false;
    }

    /**
     * get ids by parentid
     *
     * @param parentId
     * @return
     */
    @Override
    public String[] getFileIds(String parentId) {
        return new String[0];
    }

    /**
     * get file by id
     *
     * @param id
     * @return
     */
    @Override
    public File getFile(String id) throws IOException {
        return null;
    }

    /**
     * get thumb
     *
     * @param id
     * @return
     */
    @Override
    public File getThumb(String id) throws IOException {
        return null;
    }

    /**
     * 解析请求
     *
     * @param servletRequest
     * @return
     * @throws FileUploadException
     */
    private List<FileItem> parseRequest(HttpServletRequest servletRequest) throws FileUploadException, IOException {
        DiskFileItemFactory fac = createDiskFileItemFactory(tempFile.getURI().getPath(), servletRequest);
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setSizeMax(maxSize);
        return upload.parseRequest(createRequestContext(servletRequest));
    }

    /**
     * @param saveDir
     * @return
     */
    private DiskFileItemFactory createDiskFileItemFactory(String saveDir, HttpServletRequest request) {
        DiskFileItemFactory fac = new DiskFileItemFactory();
        fac.setSizeThreshold(THRESHOLD);
        if (saveDir != null) {
            fac.setRepository(new File(saveDir));
        }
        fac.setFileCleaningTracker(FileCleanerCleanup.getFileCleaningTracker(request.getSession().getServletContext()));
        return fac;
    }

    /**
     * @param req
     * @return
     */
    private RequestContext createRequestContext(final HttpServletRequest req) {
        return new RequestContext() {
            public String getCharacterEncoding() {
                return req.getCharacterEncoding();
            }

            public String getContentType() {
                return req.getContentType();
            }

            public int getContentLength() {
                return req.getContentLength();
            }

            public InputStream getInputStream() throws IOException {
                InputStream in = req.getInputStream();
                if (in == null) {
                    throw new IOException("Missing content in the request");
                }
                return req.getInputStream();
            }
        };
    }

    /**
     * 生成缩略图
     *
     * @param file
     */
    private void createThumb(File file) {
        Assert.notNull(file, getMessage("file.notexist"));
        try {
            String path = file.getPath();
            String suffix = path.substring(path.lastIndexOf("."), path.length());
            String aPath = path.replace(suffix, THUMB_SUFFIX.concat(suffix));
            File aFile = new File(aPath);
            if (FilesUtils.isImage(file)) {
                ImageUtils.resizeImageWithMaxWidth(file.getPath(), aFile.getPath(), 100);
            }
            logger.debug(getMessage("file.thumb.success", file.getPath()));
        } catch (Exception e) {
            logger.error(getMessage("file.thumb.fail", e.getLocalizedMessage()));
        }
    }

    /**
     * 数据库保存数据
     *
     * @param file
     * @param parentId
     * @param itemInfo
     */
    private FileStore save2DB(File file, String parentId, DiskFileItem itemInfo) {
        try {
            String path = file.getPath();
            FileStore fs = new FileStore();
            fs.setName(itemInfo.getName());
            fs.setPath(path);
            fs.setParentId(parentId);
            fs.setCreateAt(Calendar.getInstance().getTime());
            fs.setFileSize(itemInfo.getSize());
            return fileStoreDao.save(fs);
        } catch (Exception e) {
            throw new RuntimeException(getMessage("file.database.error",e.getLocalizedMessage()));
        }
    }

    /**
     * @param name
     * @return
     */
    private File getNewFile(String name) {
        File file = null;
        try {
            String path = location.getURI().getPath();
            file = new File(path.concat("/" + name));
            if (file.exists()) {
                file = new File(path.concat("/" + reNameFile(name)));
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return file;
    }

    /**
     * @param name
     * @return
     */
    private String reNameFile(String name) {
        if (name.lastIndexOf(".") > -1) {
            int index = name.lastIndexOf(".");
            String suffix = name.substring(index, name.length());
            return name.replace(suffix, "_" + System.currentTimeMillis() + suffix);
        } else {
            return name + "_" + System.currentTimeMillis();
        }
    }

    public void setBaseLocation(Resource baseLocation) {
        this.baseLocation = baseLocation;
    }

    public void setLocation(Resource location) {
        this.location = location;
    }

    public void setTempFile(Resource tempFile) {
        this.tempFile = tempFile;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
