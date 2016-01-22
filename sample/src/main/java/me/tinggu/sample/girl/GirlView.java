package me.tinggu.sample.girl;

import com.cyou.quick.mvp.lce.MvpLceView;

import java.util.List;

import me.tinggu.sample.model.PrettyGirl;


/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/19 11:13
 */
public interface GirlView extends MvpLceView<List<PrettyGirl>> {

    void addData(List<PrettyGirl> lists);
}
