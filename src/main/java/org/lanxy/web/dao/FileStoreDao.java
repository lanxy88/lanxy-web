package org.lanxy.web.dao;

import org.lanxy.web.entity.FileStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-17 下午11:09
 */
public interface FileStoreDao extends JpaRepository<FileStore, String> {

    /**
     * 根据父ID获取FileStore
     *
     * @param parentId
     * @return
     */
    List<FileStore> findByParentId(String parentId);

}
