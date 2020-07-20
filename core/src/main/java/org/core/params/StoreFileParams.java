package org.core.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreFileParams {

    private Long storeSpaceId;

    private String filePath;

    private Boolean isDir;

    private Long fileSize;

}
