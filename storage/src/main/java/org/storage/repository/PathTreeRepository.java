package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.storage.entity.PathTree;

import java.time.Instant;

public interface PathTreeRepository extends JpaRepository<PathTree, Long> {

    @Modifying
    @Query(value = "INSERT INTO path_tree(ancestor_id, descendant_id, depth, is_deleted, create_time) SELECT "
            + "t.ancestor_id, :descendantId, t.depth + 1, false, :currentTime FROM path_tree AS t WHERE t.descendant_id "
            + "= :ancestorId AND t.is_deleted = false UNION ALL SELECT :descendantId, :descendantId, 0, false, :currentTime",
            nativeQuery = true)
    void savePathTree(@Param("ancestorId") Long ancestorId, @Param("descendantId") Long descendantId,
                      @Param("currentTime") Instant currentTime);

}
