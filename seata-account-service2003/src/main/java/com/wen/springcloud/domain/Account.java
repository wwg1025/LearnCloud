package com.wen.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;

    private Long userId;

    private Long total;

    private Long used;

    private Long residue;
}
