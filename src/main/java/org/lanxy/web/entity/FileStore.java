package org.lanxy.web.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.lanxy.web.core.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-16 下午11:08
 */
@Entity
@Table(name = "t_file_store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileStore extends AbstractEntity {

    @Column(name = "file_size", nullable = true, precision = 10)
    private double fileSize;

    @Column(name = "parent_id", length = 32)
    private String parentId;

    @Column(nullable = true, length = 512)
    private String path;

    @Column(nullable = true, length = 10)
    private String type;

    public FileStore() {
        setCreateAt(new Date());
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
