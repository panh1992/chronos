package org.storage.configuration;

import org.core.util.SnowflakeIdWorker;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * SnowflakeId 生成器
 *
 * @author panhong
 */
public class SnowflakeIdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return SpringContext.getBean(SnowflakeIdWorker.class).nextId();
    }

}
