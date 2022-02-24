package pers.gxj.security.oauth2.framework.security.provider.userdetails;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.gxj.security.oauth2.common.enums.UserStatus;
import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;

import java.io.Serializable;
import java.util.Collection;

/**
 * spring-security#UserDetails实现类
 *
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/16 10:17
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUser implements UserDetails, Serializable {

    // ~ Instance fields
    // ========================================================================================================

    private UserDO userDetails;

    private Collection<GrantedAuthority> authorities;

    // ~ Constructors
    // ===================================================================================================

    /**私有无参构造器，仅由于一些类反射代码需要(fastJson的反序列化)。userDetails不能为空，所以不允许跨类使用这个构造函数*/
    private AuthUser() {}

    public AuthUser(UserDO userDO) {
        this.userDetails = userDO;
        this.authorities = Sets.newHashSet();
    }

    public AuthUser(UserDO userDO, Collection<GrantedAuthority> authorities) {
        this.userDetails = userDO;
        this.authorities = authorities;
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return this.userDetails.getPassword();
    }

    @Override
    @JSONField(serialize = false)
    public String getUsername() {
        return this.userDetails.getUsername();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return !UserStatus.LOCK.name().equals(this.userDetails.getStatus());
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return UserStatus.NORMAL.name().equals(this.userDetails.getStatus());
    }
}
