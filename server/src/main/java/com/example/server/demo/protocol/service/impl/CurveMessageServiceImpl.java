package com.example.server.demo.protocol.service.impl;


import com.example.server.demo.protocol.entity.CurveMessage;
import com.example.server.demo.protocol.mapper.CurveMessageMapper;
import com.example.server.demo.protocol.service.CurveMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author huangmanjing
 * @since 2020-11-12
 */
@Service("curveMessageServiceImpl")
@Component
public class CurveMessageServiceImpl implements CurveMessageService {
    @Autowired
    private CurveMessageMapper curveMessageMapper;

    public static CurveMessageServiceImpl curveMessageService = new CurveMessageServiceImpl();

    @Override
    public void insert(CurveMessage curveMessage) {
        curveMessageMapper.insert(curveMessage);
    }
}
