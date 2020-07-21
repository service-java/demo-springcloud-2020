package cn.jiiiiiin.security.core.support;

/**
 * 简单响应对象
 *
 * 比如控制器需要响应一个json对象，那么就可以将接口的返回值定义为当前类，响应内容直接放到content字段
 *
 * @author jiiiiiin
 */
public class SimpleResponse {

    public SimpleResponse(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static SimpleResponse newInstance(Object content) {
        SimpleResponse fragment = new SimpleResponse(content);
        return fragment;
    }
}
