package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.storage.entity.StoreSpace;

public interface StoreSpaceRepository extends JpaRepository<StoreSpace, Long> {

}
