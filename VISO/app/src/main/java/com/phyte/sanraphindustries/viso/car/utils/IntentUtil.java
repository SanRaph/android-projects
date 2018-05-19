package com.phyte.sanraphindustries.viso.car.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.classic.android.consts.MIME;

import java.io.File;

/**
 * File Description: Jump to the corresponding system interface
 * Creator: Write a classic
 * Created on: 2015/11/4 17:26
 */
public final class IntentUtil {

    private IntentUtil() { }

    /**
     *File selection
     *
     * @param activity
     * @param mimeType mime type
     * @param title selection header
     * @param fileChooserCode startActivityForResult requestCode
     * @param activityNotFoundHint did not find the system default file selection error
     */
    public static void showFileChooser(@NonNull Fragment activity, String mimeType, @StringRes int title,
                                       int fileChooserCode, @StringRes int activityNotFoundHint) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(title)),
                                            fileChooserCode);
        } catch (android.content.ActivityNotFoundException ex) {
            ToastUtil.showToast(activity.getContext(), activityNotFoundHint);
        }
    }

    /**Use browser to open url*/
    public static void browser(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * Share text
     *
     * @param title
     * @param subject topic
     * @param content content
     */
    public static void shareText(@NonNull Context context, @NonNull String title, @NonNull String subject,
                                 @NonNull String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(MIME.TEXT);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * Install the application
     *
     * @param apkPath apk path
     * @param authority Parameters used in Android 7.0
     * The authority of a {@link FileProvider} defined in a
     * {@code <provider>} element in your app's manifest.
     */
    public static void installApp(
            @NonNull Context context, @NonNull String apkPath, String authority) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri  uri;
        File file = new File(apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
