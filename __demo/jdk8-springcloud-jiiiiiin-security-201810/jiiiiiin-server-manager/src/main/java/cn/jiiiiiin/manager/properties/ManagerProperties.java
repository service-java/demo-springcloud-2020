package cn.jiiiiiin.manager.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jiiiiiin
 */
@ConfigurationProperties(prefix = "manager")
@Setter
@Getter
public class ManagerProperties {

}
