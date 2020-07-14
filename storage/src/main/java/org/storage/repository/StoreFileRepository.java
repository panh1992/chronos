package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.storage.entity.StoreFile;

public interface StoreFileRepository extends JpaRepository<StoreFile, Long> {

    @Query(value = "SELECT CONCAT('/', group_concat(ancestor.name separator '/')) as filePath "
            + "FROM store_file AS ancestor INNER JOIN path_tree ON ancestor.store_file_id = ancestor_id "
            + "INNER JOIN store_file AS descendant ON descendant_id = descendant.store_file_id WHERE "
            + "descendant.store_space_id = :storeSpaceId AND descendant.store_file_id = :storeFileId", nativeQuery = true)
    String findStoreFilePath(@Param("storeSpaceId") Long storeSpaceId, @Param("storeFileId") String storeFileId);

}
