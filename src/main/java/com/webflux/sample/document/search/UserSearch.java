package com.webflux.sample.document.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch {

    private Integer limit;
    private Integer offset;

    private String userId;
    private String name;
    private String email;
    private String purchaseId;
    private String purchaseValue;

    private List<String> purchaseIdList;
    private List<String> userIdList;
}
