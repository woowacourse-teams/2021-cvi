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

    @Enumerated(value = EnumType.STRING)
    protected RegionPopulation regionPopulation;

    public PublicData(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, RegionPopulation regionPopulation) {
        super(id, createdAt, lastModifiedAt);
        this.regionPopulation = regionPopulation;
    }
}
