package org.core.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResp {

    private String credentials;

}
