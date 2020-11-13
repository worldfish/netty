package com.example.server.demo.protocol.mapper;


import com.example.server.demo.protocol.entity.CurveMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 * @author huangmanjing
 * @since 2020-11-12
 */
@Mapper
public interface CurveMessageMapper {
    void insert(CurveMessage curveMessage);
}
