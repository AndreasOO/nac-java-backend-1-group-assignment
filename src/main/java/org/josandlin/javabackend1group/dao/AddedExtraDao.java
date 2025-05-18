package org.josandlin.javabackend1group.dao;

import jakarta.transaction.Transactional;
import org.josandlin.javabackend1group.entity.AddedExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddedExtraDao extends JpaRepository<AddedExtra, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM AddedExtra ae WHERE ae.id = :id")
    void deleteById(@Param("id") Long id);
}
