package cn.jiiiiiin.module.mngauth.component;

import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author jiiiiiin
 */
@Getter
@Setter
public class MngUserDetails implements UserDetails {

    private static final long serialVersionUID = -8362660409491439833L;

    private Admin admin;

    public MngUserDetails(Admin admin) {
        this.admin = admin;
    }

    /**
     * 简化：AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return admin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthorityName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
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
}
