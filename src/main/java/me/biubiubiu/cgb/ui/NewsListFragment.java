package me.biubiubiu.cgb.ui;

import static me.biubiubiu.cgb.core.Constants.Extra.NEWS_ITEM;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import me.biubiubiu.cgb.BootstrapApplication;
import me.biubiubiu.cgb.BootstrapServiceProvider;
import me.biubiubiu.cgb.Injector;
import me.biubiubiu.cgb.R;
import me.biubiubiu.cgb.authenticator.LogoutService;
import me.biubiubiu.cgb.core.News;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import javax.inject.Inject;

import java.util.*;

public class NewsListFragment extends ItemListFragment<News> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_news);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.news_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        final List<News> initialItems = items;
        return new ThrowableLoader<List<News>>(getActivity(), items) {

            @Override
            public List<News> loadData() throws Exception {
                try {
                    if(getActivity() != null) {
                        serviceProvider.getService(getActivity()).getNews();
                        return mockNews();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    private List<News> mockNews() {
        List<News> l = new ArrayList<News>();
        l.add(new News("佐登妮丝", "美容纤体服务及美容纤体产品", "1"));
        l.add(new News("水磨坊", "美容纤体服务及美容纤体产品", "1"));
        l.add(new News("金夫人", "婚纱摄影、艺术照、婚庆策划", "1"));
        l.add(new News("傲胜", "家居家具家饰", "1"));
        l.add(new News("现想商贸", "百货", "1"));
        return l;
    }

    @Override
    protected SingleTypeAdapter<News> createAdapter(List<News> items) {
        return new NewsListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        News news = ((News) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), NewsActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
