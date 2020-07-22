package com.spring.cloud.chapter14.type.handler;

import com.spring.cloud.chapter14.enumeration.PaymentChannelEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**** imports ****/
// 定义需要转换的Java类型
@MappedTypes(PaymentChannelEnum.class)
// 定义需要转换的Jdbc类型
@MappedJdbcTypes(JdbcType.INTEGER) // ①
public class PaymentChannelHandler
        implements TypeHandler<PaymentChannelEnum> {  // ②
    @Override
    public void setParameter(PreparedStatement ps, int idx,
                             PaymentChannelEnum pc, JdbcType jdbcType) throws SQLException {
        ps.setInt(idx, pc.getId());
    }

    @Override
    public PaymentChannelEnum getResult(ResultSet rs, String name)
            throws SQLException {
        int id = rs.getInt(name);
        return PaymentChannelEnum.getById(id);
    }

    @Override
    public PaymentChannelEnum getResult(
            ResultSet rs, int idx) throws SQLException {
        int id = rs.getInt(idx);
        return PaymentChannelEnum.getById(id);
    }

    @Override
    public PaymentChannelEnum getResult(
            CallableStatement cs, int idx) throws SQLException {
        int id = cs.getInt(idx);
        return PaymentChannelEnum.getById(id);
    }
}