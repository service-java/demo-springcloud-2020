package cn.jiiiiiin.module.common.entity.mngauth;

import cn.jiiiiiin.module.common.dto.mngauth.Menu;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.jiiiiiin.data.orm.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mng_admin")
@ApiModel(value="Admin对象", description="用户表")
public class Admin extends BaseEntity<Admin> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码，加密存储")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @TableField(exist = false)
    private Set<Role> roles = new HashSet<>();

    @ApiModelProperty(value = "前端授权资源")
    @TableField(exist = false)
    private HashSet<Resource> authorizeResources;

    @ApiModelProperty(value = "前端菜单")
    @TableField(exist = false)
    private ArrayList<Menu> menus;

    public static final String CREATE_TIME = "create_time";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String PHONE = "phone";

    public static final String EMAIL = "email";
}
