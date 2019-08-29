package cn.jiiiiiin.module.mngauth.controller;


import cn.jiiiiiin.module.common.dto.mngauth.Menu;
import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import cn.jiiiiiin.module.mngauth.service.IResourceService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import cn.jiiiiiin.module.common.controller.BaseController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 权限资源表 前端控制器
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 获取资源树
     *
     * @return
     */
    @GetMapping
    public R<List<Resource>> getTree(){
        val allResource = resourceService.list(null);
        val menus = new ArrayList<Resource>();
        allResource.forEach(resource -> {
            // 过滤一级节点
            if (resource.getPid().equals(Resource.IS_ROOT_MENU)) {
                val node = Resource.parserMenu(resource, allResource);
                menus.add(node);
            }
        });
        menus.sort(Comparator.comparingInt(Resource::getNum));
        return success(menus);
    }

    /**
     * 创建资源
     * @param resource
     * @return
     */
    @PostMapping
    public R<Resource> create(@RequestBody Resource resource){
        resource.insert();
        return success(resource);
    }

}
