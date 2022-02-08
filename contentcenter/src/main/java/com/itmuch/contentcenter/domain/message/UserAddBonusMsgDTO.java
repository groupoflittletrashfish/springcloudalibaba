package com.itmuch.contentcenter.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：liwuming
 * @date ：Created in 2022/1/24 13:32
 * @description ：用于用户增加积分的消息体对象
 * @modified By：
 * @version:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddBonusMsgDTO {

    /**
     * 用户的ID
     */
    private Integer userId;
    /**
     * 积分数
     */
    private Integer bonus;
}
