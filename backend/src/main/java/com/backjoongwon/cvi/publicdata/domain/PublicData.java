package com.backjoongwon.cvi.publicdata.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@AttributeOverride(name = "id", column = @Column(name = "public_data_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PublicData extends BaseEntity {

    private String sido;

    public PublicData(Long id, LocalDateTime createdAt, String sido) {
        super(id, createdAt);
        this.sido = sido;
    }
}
