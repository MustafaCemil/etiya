package com.etiya.etiya.dto;

import com.etiya.etiya.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private static final long serialVersionUID = 1293119099651337432L;

    private User user;
    private String token;

}
