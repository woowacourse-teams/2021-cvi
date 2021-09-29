package com.cvi.publicdata.domain.repository;

import com.cvi.publicdata.domain.model.PublicData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicDataRepository extends JpaRepository<PublicData, Long> {
}
