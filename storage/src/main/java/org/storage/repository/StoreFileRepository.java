package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.storage.entity.StoreFile;

public interface StoreFileRepository extends JpaRepository<StoreFile, Long> {

    /**
     * 查询某存储空间下某文件路径
     */
    @Query(value = "select concat('/', group_concat(ancestor.name separator '/')) as file_path "
            + "from store_file as ancestor inner join path_tree on ancestor.store_file_id = ancestor_id "
            + "inner join store_file as descendant on descendant_id = descendant.store_file_id where "
            + "descendant.store_space_id = :storeSpaceId and descendant.store_file_id = :storeFileId",
            nativeQuery = true)
    String findStoreFilePath(@Param("storeSpaceId") Long storeSpaceId, @Param("storeFileId") Long storeFileId);

    /**
     * 查询存储空间下某文件的文件主键
     */
    @Query(value = "select store_file_id from store_file where store_space_id = :storeSpaceId and "
            + "(select concat('/', group_concat(ancestor.name separator '/')) as file_path from store_file as ancestor "
            + "inner join path_tree on ancestor.store_file_id = ancestor_id inner join store_file as descendant on "
            + "descendant_id = descendant.store_file_id where descendant.store_space_id = store_file.store_space_id "
            + "and descendant.store_file_id = store_file.store_file_id) = :filePath and is_deleted = :isDeleted",
            nativeQuery = true)
    Long findFileIdByStoreFilePath(@Param("storeSpaceId") Long storeSpaceId, @Param("filePath") String filePath,
                                   @Param("isDeleted") Boolean isDeleted);

}
