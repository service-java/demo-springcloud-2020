package cn.jiiiiiin.module.mngauth.controller;


import cn.jiiiiiin.module.common.controller.BaseController;
import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping
    public R<Admin> create() {
        val admin = new Admin().setUsername("haha");
        return success(admin);
    }

}
