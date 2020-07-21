package cn.jiiiiiin.module.common.entity.mngauth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import cn.jiiiiiin.data.orm.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mng_role")
@ApiModel(value="Role对象", description="角色表")
public class Role extends BaseEntity<Role> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色名称")
    private String authorityName;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "父角色id")
    private Long pid;

    @TableField(exist = false)
    private Set<Resource> resources = new HashSet<>();

    public static final String NAME = "name";

    public static final String AUTHORITY_NAME = "authority_name";

    public static final String NUM = "num";

    public static final String PID = "pid";

}
