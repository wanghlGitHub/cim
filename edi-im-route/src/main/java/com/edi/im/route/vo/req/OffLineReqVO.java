package com.edi.im.route.vo.req;

import com.edi.im.common.req.BaseRequest;

/**
 * 用户下线
 * @author: <a href="568227120@qq.com">heliang.wang</a>
 * @date:   2019-08-21 0021 11:06
 */
public class OffLineReqVO extends BaseRequest{
    private Long userId ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "OffLineReqVO{" +
                "userId=" + userId +
                "} " + super.toString();
    }
}
