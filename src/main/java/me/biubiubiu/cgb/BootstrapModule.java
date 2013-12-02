package me.biubiubiu.cgb;

import android.accounts.AccountManager;
import android.content.Context;

import me.biubiubiu.cgb.authenticator.BootstrapAuthenticatorActivity;
import me.biubiubiu.cgb.authenticator.LogoutService;
import me.biubiubiu.cgb.core.CheckIn;
import me.biubiubiu.cgb.core.TimerService;
import me.biubiubiu.cgb.ui.BootstrapTimerActivity;
import me.biubiubiu.cgb.ui.CarouselActivity;
import me.biubiubiu.cgb.ui.CheckInsListFragment;
import me.biubiubiu.cgb.ui.ItemListFragment;
import me.biubiubiu.cgb.ui.NewsActivity;
import me.biubiubiu.cgb.ui.NewsListFragment;
import me.biubiubiu.cgb.ui.UserActivity;
import me.biubiubiu.cgb.ui.UserListFragment;
import me.biubiubiu.cgb.ui.AddActivity;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class,
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
