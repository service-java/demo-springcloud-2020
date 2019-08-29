package cn.jiiiiiin.module.common.dto.mngauth;

import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.io.Serializable;
import java.util.*;

/**
 * 前端所需菜单
 *
 * @author jiiiiiin
 */
@Data
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = -7510473557351395479L;

    public static ModelMapper MODEL_MAPPER = new ModelMapper();

    static {
        MODEL_MAPPER.addMappings(new PropertyMap<Resource, Menu>() {

            @Override
            protected void configure() {
                map().setTitle(source.getName());
            }
        });
    }


    @ApiModelProperty(value = "url地址")
    private String url;

    @ApiModelProperty(value = "页面地址")
    private String path;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单排序号")
    private Integer num;

    private List<Menu> children;

    /**
     * 递归解析一级节点下面的子节点
     *
     * @param resource
     * @param menuResources
     * @return
     */
    public static Menu parserMenu(Resource resource, Collection<Resource> menuResources) {
        val menu = Menu.MODEL_MAPPER.map(resource, Menu.class);
        val children = new ArrayList<Menu>();
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
            menu.setChildren(children);
            menu.getChildren().sort(Comparator.comparingInt(Menu::getNum));
        }
        return menu;
    }
}
