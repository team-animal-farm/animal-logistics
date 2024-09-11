package jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @CreatedBy
    @Column(updatable = false)
    protected String createdBy;
    @LastModifiedBy
    protected String updatedBy;
    protected String deletedBy;

    protected Boolean deletedYn = false;

    public void delete(String username) {
        this.deletedYn = true;
        this.deletedAt = LocalDateTime.now();
        // TODO: 헤더에서 받아오는 방식으로 처리하는 게 좋을듯?
        this.deletedBy = username;
    }

    public void setDeletedYnFalse() {
        this.deletedYn = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }

    public Boolean isDeleted() {
        return this.deletedYn;
    }
}
