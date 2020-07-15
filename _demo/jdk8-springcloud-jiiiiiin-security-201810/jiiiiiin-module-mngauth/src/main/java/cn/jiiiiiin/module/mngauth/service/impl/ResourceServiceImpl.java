package cn.jiiiiiin.module.mngauth.service.impl;

import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import cn.jiiiiiin.module.common.mapper.mngauth.ResourceMapper;
import cn.jiiiiiin.module.mngauth.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限资源表 服务实现类
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
