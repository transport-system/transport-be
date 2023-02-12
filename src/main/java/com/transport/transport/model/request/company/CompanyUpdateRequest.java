package com.transport.transport.model.request.company;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyUpdateRequest {
    private String companyName;
    private String description;
}
