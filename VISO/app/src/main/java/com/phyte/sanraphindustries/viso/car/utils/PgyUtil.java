package com.phyte.sanraphindustries.viso.car.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.classic.android.consts.MIME;
import com.classic.android.utils.SDCardUtil;
import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.consts.Consts;
import com.phyte.sanraphindustries.viso.car.ui.activity.MainActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.views.PgyerDialog;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 *Application Name: CarAssistant
 * Package Name: com.classic.car.utils
 *
 * File Description: Dandelion SDK Tools
 * Created by: Continued to write classic
 * Created on: 16/7/9 4:00 PM
 */
public final class PgyUtil {

    public static void checkUpdate(final Activity activity, final boolean showHint) {
        if (!isNetworkAvailable(activity.getApplicationContext())) {
            if (showHint) {
                ToastUtil.showToast(activity.getApplicationContext(), R.string.network_error);
            }
            return;
        }
        final WeakReference<Activity> reference = new WeakReference<>(activity);
        PgyUpdateManager.register(activity, "", new UpdateManagerListener() {
            @Override public void onUpdateAvailable(final String result) {
                final Activity act = reference.get();
                if (null == act) {
                    return;
                }
                final AppBean appBean = getAppBeanFromString(result);
                final String apkName = appBean.getVersionName() + Consts.APK;
                final File file = new File(SDCardUtil.getApkDirPath(), apkName);
                if (file.exists()) {
                    IntentUtil.installApp(act.getApplicationContext(), file.getPath(),
                                          act.getPackageName() + Consts.AUTHORITIES_SUFFIX);
                    return;
                }

                new MaterialDialog.Builder(act).title(R.string.update_dialog_title)
                                               .titleColorRes(R.color.primary_text)
                                               .backgroundColorRes(R.color.white)
                                               .content(appBean.getReleaseNote())
                                               .contentColorRes(R.color.primary_light)
                                               .positiveText(R.string.update)
                                               .negativeText(R.string.cancel)
                                               .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                   @Override
                                                   public void onClick(MaterialDialog dialog, DialogAction which) {
                                                       final Activity act = reference.get();
                                                       if (null != act) {
                                                           //蒲公英SDK在Android N上崩溃，需要处理
                                                           if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                                               startDownloadTask(act, appBean.getDownloadURL());
                                                           } else {
                                                               ((MainActivity)act).registerAppUpdateReceiver(
                                                                       download(act.getApplicationContext(),
                                                                                appBean.getDownloadURL(), apkName),
                                                                       file.getPath());
                                                           }
                                                       }
                                                   }
                                               })
                                               .show();
            }

            @Override public void onNoUpdateAvailable() {
                final Activity act = reference.get();
                if (null != act && showHint) {
                    ToastUtil.showToast(act.getApplicationContext(), R.string.no_update);
                }
            }
        });
    }

    public static void setDialogStyle(@NonNull String backgroundColor, @NonNull String textColor) {
        PgyerDialog.setDialogTitleBackgroundColor(backgroundColor);
        PgyerDialog.setDialogTitleTextColor(textColor);
    }

    public static void feedback(Activity activity) {
        WeakReference<Activity> reference = new WeakReference<>(activity);
        Activity act = reference.get();
        try {
            if (null != act) {
                PgyFeedback.getInstance().showDialog(act);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(Context context) {
        PgyCrashManager.register(context);
    }

    public static void destroy() {
        //noinspection EmptyCatchBlock
        try {
            PgyUpdateManager.unregister();
            PgyCrashManager.unregister();
            PgyFeedbackShakeManager.unregister();
            PgyFeedback.getInstance().destroy();
        } catch (Exception e) {
        }
    }

    private static long download(Context context, String url, String fileName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(context.getString(R.string.app_name));
        request.setDescription(context.getString(R.string.update_dialog_title));
        request.setMimeType(MIME.APK);
        request.setDestinationInExternalPublicDir(Consts.DIR_NAME+File.separator+SDCardUtil.APK_DIR, fileName);
        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    @SuppressWarnings("deprecation") private static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
