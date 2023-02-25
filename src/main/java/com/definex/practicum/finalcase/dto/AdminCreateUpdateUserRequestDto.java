package com.definex.practicum.finalcase.dto;

import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateUpdateUserRequestDto {

        private RegisterDto registerDto;
        private List<Role> roles;

        // Builds a user object without password or roles. Any credit score, limit or limit application are omitted as well.
        public User buildUserWithoutPasswordAndRoles(){
            User user = new User();
            user.setTckn(registerDto.getTckn());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setGsmNumber(registerDto.getGsmNumber());
            user.setDateOfBirth(registerDto.getDateOfBirth());
            return user;
        }


}
