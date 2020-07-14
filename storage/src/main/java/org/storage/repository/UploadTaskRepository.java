package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.storage.entity.UploadTask;

public interface UploadTaskRepository extends JpaRepository<UploadTask, Long> {

}
