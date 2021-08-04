package com.backjoongwon.cvi.publicdata.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PublicData extends BaseEntity {

    private String sido;

    public PublicData(Long id, LocalDateTime createdAt, String sido) {
        super(id, createdAt);
        this.sido = sido;
    }
}
