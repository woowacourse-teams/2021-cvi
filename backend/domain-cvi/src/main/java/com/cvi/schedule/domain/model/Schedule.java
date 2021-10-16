package com.cvi.schedule.domain.model;

import com.cvi.config.entity.BaseEntity;
import com.cvi.config.entity.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "schedule_id"))
public class Schedule extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Version
    private Integer version;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean isRunning;

    @Builder
    public Schedule(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, String name, Integer version, boolean isRunning) {
        super(id, createdAt, lastModifiedAt);
        this.name = name;
        this.version = version;
        this.isRunning = isRunning;
    }

    public void reversRunningState() {
        this.isRunning = !isRunning;
    }
}
