package org.vaadin.saphana.backend;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractEntity {
	
    @Id
    @Column(columnDefinition="NVARCHAR(100)")
    private String id = UUID.randomUUID().toString();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }
    
    public Date getLastModified() {
		return lastModified;
	}
    
    public Date getCreated() {
		return created;
	}
    
    public void setCreated(Date created) {
		this.created = created;
	}
    
    public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
    
    @PrePersist
    public void onPrePersist() {
    	created = lastModified = new Date();
    }
    
    @PreUpdate
    public void onPreUpdate() {
    	lastModified = new Date();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        return getId().equals(((AbstractEntity) obj).getId());
    }

}
