package com.swiatek.benford.graph;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GraphRepository extends JpaRepository<GraphEntity, Long> {
    List<GraphEntity> findByDocumentId(Long documentId);
}
