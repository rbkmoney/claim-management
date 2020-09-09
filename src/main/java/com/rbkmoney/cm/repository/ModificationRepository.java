package com.rbkmoney.cm.repository;

import com.rbkmoney.cm.model.ModificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationRepository extends JpaRepository<ModificationModel, Long>, JpaSpecificationExecutor<ModificationModel> {

//    @Query("update ModificationModel mod SET disabled = true where mod.id = :modification_id")
//    void softRemove(@Param("modification_id") Long id);

}
