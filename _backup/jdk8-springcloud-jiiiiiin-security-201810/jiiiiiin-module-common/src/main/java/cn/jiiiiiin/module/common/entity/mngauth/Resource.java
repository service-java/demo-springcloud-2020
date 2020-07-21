package cn.jiiiiiin.module.common.entity.mngauth;

import cn.jiiiiiin.data.orm.entity.BaseEntity;
import cn.jiiiiiin.module.common.dto.mngauth.Menu;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceChannelEnum;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.val;

import java.util.*;

/**
 * <p>
 * 权限资源表
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mng_resource")
@ApiModel(value="Resource对象", description="权限资源表")
public class Resource extends BaseEntity<Resource> {

    /**
     * 根节点
     */
    public static final Long IS_ROOT_MENU = 0L;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单父id: 0标识(默认)为根节点")
    private Long pid;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "页面地址")
    private String path;

    @ApiModelProperty(value = "接口类型，如GET(默认)")
    private String method;

    @ApiModelProperty(value = "菜单排序号")
    private Integer num;

    @ApiModelProperty(value = "菜单层级")
    private Integer levels;

    @ApiModelProperty(value = "类型: 1:菜单(默认) 0:按钮")
    private ResourceTypeEnum type;

    @ApiModelProperty(value = "菜单状态: 1:启用(默认) 0:不启用")
    private Integer status;

    @ApiModelProperty(value = "标识渠道，不同的渠道就是不同的资源分组: 0:内管")
    private ResourceChannelEnum channel;

    @TableField(exist = false)
    private List<Resource> children;

    public static final String PID = "pid";

    public static final String NAME = "name";

    public static final String ICON = "icon";

    public static final String URL = "url";

    public static final String PATH = "PATH";

    public static final String NUM = "num";

    public static final String LEVELS = "levels";

    public static final String TYPE = "TYPE";

    public static final String METHOD = "method";

    public static final String STATUS = "status";

    public static final String CHANNEL = "channel";

    /**
     * 递归解析一级节点下面的子节点
     *
     * @param resource
     * @param menuResources
     * @return
     */
    public static Resource parserMenu(Resource resource, Collection<Resource> menuResources) {
        val children = new ArrayList<Resource>();
        val pid = resource.getId();
        menuResources.forEach((item) -> {
            // 添加子节点
            if (item.getPid().equals(pid)) {
                // 递归出子元素
                val node = parserMenu(item, menuResources);
                children.add(node);
            }
        });
        if (children.size() > 0) {
            resource.setChildren(children);
            resource.getChildren().sort(Comparator.comparingInt(Resource::getNum));
        }
        return resource;
    }

}
