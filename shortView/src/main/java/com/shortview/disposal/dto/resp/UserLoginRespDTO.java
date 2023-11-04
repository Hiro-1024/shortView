package com.shortview.disposal.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDTO {

    private String username;

    private String token;
}
