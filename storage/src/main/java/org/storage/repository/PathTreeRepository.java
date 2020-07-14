package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.storage.entity.PathTree;

public interface PathTreeRepository extends JpaRepository<PathTree, Long> {

    @Query(value = "INSERT INTO path_tree(ancestor_id, descendant_id, depth, is_deleted, create_time) "
            + "SELECT t.ancestor_id, :descendantId, t.depth + 1, false, now() FROM path_tree AS t "
            + "WHERE t.descendant_id = :ancestorId UNION ALL SELECT :descendantId, :descendantId, 0, false, now()",
            nativeQuery = true)
    void save(@Param("ancestorId") Long ancestorId, @Param("descendantId") Long descendantId);

}
