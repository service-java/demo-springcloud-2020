/**
 *
 */
package cn.jiiiiiin.security.core.social.view;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 绑定/解绑结果默认视图
 * <p>
 * 支持多渠道可重用
 * <p>
 * 应用需要自定义定义当前视图
 *
 * @author zhailiang
 * @see org.springframework.social.connect.web.ConnectController#connect(String, NativeWebRequest) 定义该接口的响应视图
 * @see cn.jiiiiiin.security.core.social.weixin.config.WeixinAutoConfiguration#weixinConnectedView 在这里去声明具体的响应视图组件
 */
public class CustomBindingConnectView extends AbstractView {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel
     * (java.util.Map, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        // 响应绑定页面，微信在授权完毕之后的回调url接口响应内容
        response.setContentType("text/html;charset=UTF-8");
        // 区分绑定还是解绑成功
        if (model.get("connections") == null) {
            // TODO 待修正
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.sendRedirect(request.getContextPath() + "/userBinding.html");
        }

    }

}
