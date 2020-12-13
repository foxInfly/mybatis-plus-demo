package com.lp.test.mybatisplusdemo.poi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lp.test.mybatisplusdemo.poi.domain.po.TodayCount;
import com.lp.test.mybatisplusdemo.poi.service.TodayCountService;
import com.lp.test.mybatisplusdemo.poi.service.mapper.TodayCountMapper;
import org.springframework.stereotype.Service;

/**
 * @author lp
 * @since 2020-12-13 16:31:37
 */
@Service
public class TodayCountServiceImpl extends ServiceImpl<TodayCountMapper, TodayCount> implements TodayCountService {
}
