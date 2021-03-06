package com.github.chaossss.ishuhui.ui.presenter.selected;

import com.github.chaossss.httplibrary.listener.BaseCallbackListener;
import com.github.chaossss.ishuhui.domain.AppConstant;
import com.github.chaossss.ishuhui.domain.dao.AppDao;
import com.github.chaossss.ishuhui.domain.model.AllBookModels;
import com.github.chaossss.ishuhui.domain.util.LogUtils;

/**
 * Interactor that helps SelectedFragment implement get selected comic function
 * Created by chaossss on 2016/2/2.
 */
public class SelectedInteractor implements ISelectedInteractor {
    @Override
    public void getSelectedComic(final OnSelectedComicGotListener listener) {
        AppDao.getInstance().subscribeByUser("0", new BaseCallbackListener<AllBookModels>() {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                LogUtils.logI(this, AppConstant.GET_SELECTED_COMIC_RESULT + result);
            }

            @Override
            public void onSuccess(AllBookModels result) {
                super.onSuccess(result);
                listener.onSelectedComicGotSuccess(result.Return.List);


            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                listener.onSelectedComicGotFail(AppConstant.NET_RESPONSE_ERROR);
                LogUtils.logI(listener, AppConstant.EXCEPTION + e.toString());
            }
        });
    }
}
