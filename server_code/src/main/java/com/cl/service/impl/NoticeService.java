package com.cl.service.impl;

import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.entity.TongzhiRecordEntity;
import com.cl.service.JiuzhentongzhiService;
import com.cl.service.TongzhiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 通知发送服务
 */
@Service
public class NoticeService {

    @Autowired
    private JiuzhentongzhiService jiuzhentongzhiService;

    @Autowired
    private TongzhiRecordService tongzhiRecordService;

    /**
     * 发送通知
     * @param tongzhi 通知实体
     * @return 是否发送成功
     */
    public boolean sendNotice(JiuzhentongzhiEntity tongzhi) {
        try {
            // 模拟发送通知的逻辑
            // TODO 实际项目中，这里可以调用短信服务、邮件服务等
            System.out.println("发送通知给用户：" + tongzhi.getZhanghao() + "，内容：" + tongzhi.getTongzhibeizhu());

            // 模拟发送成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 记录发送结果
     * @param tongzhi 通知实体
     * @param success 是否发送成功
     * @param reason 失败原因（如果失败）
     */
    private void recordSendResult(JiuzhentongzhiEntity tongzhi, boolean success, String reason) {
        TongzhiRecordEntity record = new TongzhiRecordEntity();
        record.setTongzhiId(tongzhi.getId());
        record.setTongzhibianhao(tongzhi.getTongzhibianhao());
        record.setZhanghao(tongzhi.getZhanghao());
        record.setShouji(tongzhi.getShouji());
        record.setTongzhineirong(tongzhi.getTongzhibeizhu());
        record.setFasongshijian(new Date());
        record.setFasongzhuangtai(success ? "success" : "failed");
        record.setShibaiyuanyin(reason);

        // 查询之前的重试次数
        List<TongzhiRecordEntity> records = tongzhiRecordService.selectList(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<TongzhiRecordEntity>()
                        .eq("tongzhi_id", tongzhi.getId())
                        .eq("fasongzhuangtai", "failed")
        );
        record.setChongcishu(records.size() + 1);

        tongzhiRecordService.insert(record);
    }

    /**
     * 处理待发送的通知
     */
    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void processPendingNotices() {
        // 查询所有待发送的通知 暂时为中文，实际改成英语。
        List<JiuzhentongzhiEntity> notices = jiuzhentongzhiService.selectList(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<JiuzhentongzhiEntity>()
                        .eq("tongzhizhuangtai", "待发送")
        );

        for (JiuzhentongzhiEntity notice : notices) {
            boolean success = sendNotice(notice);
            if (success) {
                // 更新通知状态为已发送
                notice.setTongzhizhuangtai("已发送");
                jiuzhentongzhiService.updateById(notice);

                // 记录发送成功
                recordSendResult(notice, true, null);
            } else {
                // 更新通知状态为发送失败
                notice.setTongzhizhuangtai("发送失败");
                jiuzhentongzhiService.updateById(notice);

                // 记录发送失败
                recordSendResult(notice, false, "通知发送失败，请检查通知服务");
            }
        }
    }

    /**
     * 重试发送失败的通知
     */
    @Scheduled(fixedRate = 3600000) // 每小时执行一次
    public void retryFailedNotices() {
        // 查询所有发送失败的通知 暂时为中文，实际改成英语。
        List<JiuzhentongzhiEntity> notices = jiuzhentongzhiService.selectList(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<JiuzhentongzhiEntity>()
                        .eq("tongzhizhuangtai", "发送失败")
        );

        for (JiuzhentongzhiEntity notice : notices) {
            // 将状态改为待发送，准备重新发送
            notice.setTongzhizhuangtai("待发送");
            jiuzhentongzhiService.updateById(notice);

            boolean success = sendNotice(notice);
            if (success) {
                // 更新通知状态为已发送
                notice.setTongzhizhuangtai("已发送");
                jiuzhentongzhiService.updateById(notice);

                // 记录发送成功
                recordSendResult(notice, true, null);
            } else {
                // TODO: 更新通知状态为发送失败
                notice.setTongzhizhuangtai("发送失败");
                jiuzhentongzhiService.updateById(notice);

                // 记录发送失败
                recordSendResult(notice, false, "重试发送失败，请检查通知服务");
            }
        }
    }
}
