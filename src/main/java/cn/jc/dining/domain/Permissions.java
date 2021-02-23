package cn.jc.dining.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {
    private Integer id;
    private String permissionName;
    private Role role;
}