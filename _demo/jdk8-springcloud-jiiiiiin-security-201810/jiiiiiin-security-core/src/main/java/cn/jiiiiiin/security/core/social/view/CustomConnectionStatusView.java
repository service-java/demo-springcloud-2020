/**
 *
 */
package cn.jiiiiiin.security.core.social.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * spring social给我们提供的绑定解绑功能查询接口自定义响应视图
 * <p>
 * 需要访问`/connect`接口，GET方式
 *
 * @author zhailiang
 * @see org.springframework.social.connect.web.ConnectController#connectionStatus
 */
@Component("connect/status")
public class CustomConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 渲染视图
     * <p>
     * 将授权信息以json格式响应出去
     *
     * @param model 参考{@link org.springframework.social.connect.web.ConnectController#connectionStatus}中设置的值
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        final Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
        final Set<String> keySet = connections.keySet();
        // 自定义响应数据
        final Map<String, Boolean> result = new HashMap<>(keySet.size());
        for (String key : keySet) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
