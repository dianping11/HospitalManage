package com.cl.entity.view;

import com.cl.entity.TongzhiRecordEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 通知发送记录视图
 */
@TableName("tongzhi_record")
public class TongzhiRecordView extends TongzhiRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

}
