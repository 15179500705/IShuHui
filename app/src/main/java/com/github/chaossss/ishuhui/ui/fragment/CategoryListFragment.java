package com.github.chaossss.ishuhui.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chaossss.ishuhui.R;
import com.github.chaossss.ishuhui.domain.model.CategoryModel;
import com.github.chaossss.ishuhui.ui.adapter.CategoryListAdapter;
import com.github.chaossss.ishuhui.ui.presenter.category_list.CategoryListPresenter;
import com.github.chaossss.ishuhui.ui.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryListFragment extends Fragment implements CategoryListPresenter.View {
    public static final int CATEGORY_FIRST = 0;
    public static final int CATEGORY_SECOND = 1;
    public static final int CATEGORY_THIRD = 2;
    public static final int CATEGORY_NUM = 3;

    public static final String DATA_NUM = "30";
    public static final String PAGE_INDEX = "0";
    public static final String CATEGORY_FIRST_TITLE = "热血";
    public static final String CATEGORY_SECOND_TITLE = "国产";
    public static final String CATEGORY_THIRD_TITLE = "鼠绘";

    private CategoryListPresenter presenter;

    private int type;
    private List<CategoryModel.ReturnEntity.ListEntity> categoryListDatas;

    @Bind(R.id.category_list_list)
    RecyclerView categoryList;
    private CategoryListAdapter categoryListAdapter;

    public CategoryListFragment() {
    }

    public static CategoryListFragment newInstance(int type){
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        if(type == CATEGORY_FIRST){
            categoryListFragment.type = type;
        } else {
            categoryListFragment.type = type + 1;
        }
        return categoryListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryListDatas = new ArrayList<>();
        presenter = new CategoryListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, v);

        categoryList.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getCategoryData(String.valueOf(type), DATA_NUM, PAGE_INDEX);
        categoryListAdapter = new CategoryListAdapter(getContext(), categoryListDatas);
        categoryList.setAdapter(categoryListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCategoryGotSuccess(CategoryModel categoryModel) {
        categoryListDatas = categoryModel.Return.List;
        if(categoryListDatas.size() == 0){
            presenter.getCategoryData(String.valueOf(type), DATA_NUM, PAGE_INDEX);
        } else {
            categoryListAdapter.updateDatas(categoryListDatas);
        }
    }

    @Override
    public void onCategoryGotFail(String errorInfo) {
        ToastUtils.showToast(getContext(), errorInfo);
    }
}
