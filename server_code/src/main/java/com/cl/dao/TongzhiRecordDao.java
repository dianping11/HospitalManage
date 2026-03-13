package com.cl.dao;

import com.cl.entity.TongzhiRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cl.entity.view.TongzhiRecordView;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.Wrapper;
import java.util.List;

/**
 * 通知发送记录
 */
public interface TongzhiRecordDao extends BaseMapper<TongzhiRecordEntity> {

    List<TongzhiRecordView> selectListView(@Param("wrapper") Wrapper<TongzhiRecordEntity> wrapper);

    TongzhiRecordView selectListView(@Param("wrapper") Wrapper<TongzhiRecordEntity> wrapper, @Param("limit") Integer limit);

    TongzhiRecordView selectView(@Param("wrapper") Wrapper<TongzhiRecordEntity> wrapper);

}
