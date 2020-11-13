package com.example.server.demo.protocol.service;

import com.example.server.demo.protocol.entity.CurveMessage;
import org.springframework.stereotype.Service;

/**
 *  服务类
 * @author huangmanjing
 * @since 2020-11-12
 */
@Service
public interface CurveMessageService {
    void insert(CurveMessage curveMessage);
}
