package com.swiatek.benford.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UploadedDocumentRepository extends JpaRepository<UploadedDocumentEntity, Long> {
}
