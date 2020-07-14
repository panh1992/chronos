package org.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.storage.entity.StoreBackend;

public interface StoreBackendRepository extends JpaRepository<StoreBackend, Long> {

}
