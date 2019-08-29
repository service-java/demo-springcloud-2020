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
public enum ResourceTypeEnum implements IEnum<Integer> {
  BTN(0, "按钮"),
  MENU(1, "菜单");

  private int value;
  private String desc;

  ResourceTypeEnum(final int value, final String desc) {
    this.value = value;
    this.desc = desc;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
