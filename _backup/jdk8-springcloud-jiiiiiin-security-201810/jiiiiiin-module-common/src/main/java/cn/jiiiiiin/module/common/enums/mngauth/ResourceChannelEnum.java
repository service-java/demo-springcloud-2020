package cn.jiiiiiin.module.common.enums.mngauth;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>
 * 通用枚举注入演示，注意需要实现 IEnums 也需要扫描枚举包
 * </p>
 *
 * @author hubin
 * @since 2018-08-15
 */
public enum ResourceChannelEnum implements IEnum<Integer> {
  MNG(0, "内管");

  private int value;
  private String desc;

  ResourceChannelEnum(final int value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
