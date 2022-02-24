package pers.gxj.security.oauth2.framework.security.provider.clientdetails;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientDO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static pers.gxj.security.oauth2.common.utils.CollectionUtils.stringToCollection;

/**
 * spring-security-oauth2#ClientDetails实现类
 *
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/17 15:22
 */
@Data
public class AuthClient implements ClientDetails {

    // ~ Instance fields
    // ========================================================================================================

    private ClientDO clientDetails;

    // ~ Constructors
    // ===================================================================================================

    /**
     * 私有无参构造器，仅由于一些类反射代码需要(fastJson的反序列化)。clientDetails能为空，所以不允许跨类使用这个构造函数
     */
    private AuthClient() {
    }

    public AuthClient(ClientDO clientDO) {
        this.clientDetails = clientDO;
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    @JSONField(serialize = false)
    public String getClientId() {
        return clientDetails.getClientId();
    }

    @Override
    @JSONField(serialize = false)
    public Set<String> getResourceIds() {
        return stringToCollection(clientDetails.getResourceIds(), ",", HashSet::new);
    }

    /**
     * 调研时参考的依赖版本：spring-security-oauth2-2.3.6.RELEASE-sources.jar
     * 这个版本的这个属性没有任何作用，从源码看，没有任何地方引用。也就是说true和false的取值不会影响任何security框架里的逻辑，除非用户自行拓展。
     * 这里参照源码进行实现。 {@link org.springframework.security.oauth2.provider.client.BaseClientDetails#isSecretRequired}
     */
    @Override
    @JSONField(serialize = false)
    public boolean isSecretRequired() {
        return clientDetails.getClientSecret() != null;
    }

    @Override
    @JSONField(serialize = false)
    public String getClientSecret() {
        return clientDetails.getClientSecret();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isScoped() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public Set<String> getScope() {
        return stringToCollection(clientDetails.getScope(), ",", HashSet::new);
    }

    @Override
    @JSONField(serialize = false)
    public Set<String> getAuthorizedGrantTypes() {
        return stringToCollection(clientDetails.getAuthorizedGrantTypes(), ",", HashSet::new);
    }

    @Override
    @JSONField(serialize = false)
    public Set<String> getRegisteredRedirectUri() {
        return stringToCollection(clientDetails.getWebServerRedirectUri(), ",", HashSet::new);
    }

    @Override
    @JSONField(serialize = false)
    public Collection<GrantedAuthority> getAuthorities() {
        String defaultAuth = "ROLE_DEFAULT";
        String customAuth = StringUtils.defaultIfEmpty(clientDetails.getAuthorities(), "");
        String authorities = StringUtils.strip(defaultAuth + "," + customAuth, ",");
        return AuthorityUtils.createAuthorityList(authorities.split(","));
    }

    @Override
    @JSONField(serialize = false)
    public Integer getAccessTokenValiditySeconds() {
        return clientDetails.getAccessTokenValidity();
    }

    @Override
    @JSONField(serialize = false)
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetails.getRefreshTokenValidity();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAutoApprove(String scope) {
        return clientDetails.getAutoApprove();
    }

    @Override
    @JSONField(serialize = false)
    public Map<String, Object> getAdditionalInformation() {
        return JSON.parseObject(clientDetails.getAdditionalInformation());
    }
}
