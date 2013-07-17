package org.lanxy.web.service;

import org.lanxy.web.entity.FileStore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:07
 */
public interface FileStoreService {

    /**
     * get one
     *
     * @param id
     * @return
     */
    FileStore get(String id);

    /**
     * save
     *
     * @param request
     * @param parentId
     * @return
     */
    FileStore save(HttpServletRequest request, String parentId);

    /**
     * delete
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * delete
     *
     * @param fileStore
     * @return
     */
    boolean delete(FileStore fileStore);

    /**
     * get ids by parentid
     *
     * @param parentId
     * @return
     */
    String[] getFileIds(String parentId);


    /**
     * get file by id
     *
     * @param id
     * @return
     */
    File getFile(String id) throws IOException;


    /**
     * get thumb
     *
     * @param id
     * @return
     */
    File getThumb(String id) throws IOException;

}
