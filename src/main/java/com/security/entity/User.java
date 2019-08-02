package com.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.model.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: Kris
 * @Date: 2019-07-29  15:17
 */
@Table
@Entity(name = "se_user")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserRoleRel> userRoleRels;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<UserRoleRel> urrs = this.getUserRoleRels();
        if (urrs == null) {
            return null;
        }
        for (UserRoleRel urr : urrs) {
            auths.add(new SimpleGrantedAuthority(urr.getRole().getName()));
        }
        return auths;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto toUser() {
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(this, userDto);

        return userDto;
    }


}
