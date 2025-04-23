package com.dashboard.desktopapp.dtos.user.request;

import com.dashboard.desktopapp.dtos.base.UserDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DeleteUserRequestDTO {
    private UserDTO user;
}
