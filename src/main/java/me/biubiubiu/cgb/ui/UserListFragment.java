package me.biubiubiu.cgb.ui;

import static me.biubiubiu.cgb.core.Constants.Extra.USER;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import me.biubiubiu.cgb.BootstrapServiceProvider;
import me.biubiubiu.cgb.Injector;
import me.biubiubiu.cgb.R;
import me.biubiubiu.cgb.authenticator.LogoutService;
import me.biubiubiu.cgb.core.User;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import javax.inject.Inject;
import java.util.*;

public class UserListFragment  extends ItemListFragment<User> {

    @Inject BootstrapServiceProvider serviceProvider;
    @Inject LogoutService logoutService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_users);


    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.user_list_item_labels, null));
    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }


    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        final List<User> initialItems = items;
        return new ThrowableLoader<List<User>>(getActivity(), items) {
            @Override
            public List<User> loadData() throws Exception {

                try {
                    List<User> latest = null;

                    if(getActivity() != null)
                        serviceProvider.getService(getActivity()).getUsers();
                        latest = mockUser();

                    if (latest != null)
                        return latest;
                    else
                        return Collections.emptyList();
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };

    }

    private List<User> mockUser() {
        List<User> l = new ArrayList<User>();
        l.add(new User("佐登妮丝", "http://www.cgbchina.com.cn/CMS5_G20306002Resource?info=16548110;res=1375068188407387843389"));
        l.add(new User("大商新玛特", "http://www.cgbchina.com.cn/CMS5_G20306002Resource?info=16540451;res=1375067956666684575346"));
        l.add(new User("庄胜崇光百货", "http://www.cgbchina.com.cn/Info/16347642"));
        return l;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = ((User) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), UserActivity.class).putExtra(USER, user));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_users;
    }

    @Override
    protected SingleTypeAdapter<User> createAdapter(List<User> items) {
        return new UserListAdapter(getActivity().getLayoutInflater(), items);
    }
}
