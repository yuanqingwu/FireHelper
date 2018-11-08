package com.wyq.firehelper.component.share;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.StringDef;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.N;

/**
 * Author: Uni.W
 * Time: 2018/11/7 15:57
 * Desc:
 */
public class FireShare {

    @StringDef({ContentType.TEXT, ContentType.IMAGE, ContentType.AUDIO, ContentType.VIDEO, ContentType.FILE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ContentType {
        String TEXT = "text/plain";
        String IMAGE = "image/*";
        String AUDIO = "audio/*";
        String VIDEO = "video/*";
        String FILE = "*/*";
    }

    /**
     * 利用原生系统分享一个文件，会先判断文件类型
     * @param context
     * @param title
     * @param fileUri
     */
    public static void shareFileWithSys(Context context, String title, Uri fileUri) {
        if (fileUri == null ) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory("android.intent.category.DEFAULT");

        String fileType = getFileTypeByUri(context, fileUri);
        if (fileType == null) {
            fileType = ContentType.FILE;
        }
        Logger.i("File path:"+fileType+"  "+fileUri.toString());
//            Uri fileUri = getFileUri(context, file, fileType);

        intent.setType(fileType);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);

        //设置临时权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(Build.VERSION.SDK_INT <= KITKAT){
            List<ResolveInfo> infoList = context.getPackageManager().queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
            for(ResolveInfo info :infoList){
                context.grantUriPermission(info.resolvePackageName,fileUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, title));
        }
    }

    /**
     * 利用原生系统分享一段字符串
     * @param context
     * @param contentText
     * @param title
     */
    public static void shareTextWithSys(Context context, String contentText, String title) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(ContentType.TEXT);
        intent.putExtra(Intent.EXTRA_TEXT, contentText);

        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 根据内容URI获取内容类型
     * @param context
     * @param uri
     * @return
     */
    private static String getFileTypeByUri(Context context,Uri uri){
        ContentResolver resolver = context.getContentResolver();
        if (resolver != null) {
            String fileMimeType = resolver.getType(uri);//如：image/jpeg
//            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//            return mimeTypeMap.getExtensionFromMimeType(fileMimeType);//如：jpg
            return fileMimeType;
        }
        return null;
    }

    private static String getFileTypeBySys(Context context, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);

        ContentResolver resolver = context.getContentResolver();
        if (resolver != null) {
            String fileMimeType = resolver.getType(uri);
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//            return mimeTypeMap.getExtensionFromMimeType(fileMimeType);
            return fileMimeType;
        }
        return null;
    }

    /**
     * 根据传入的 File 对象和类型来查询系统 ContentProvider 来获取相应的 Uri
     * @param context
     * @param file
     * @param type
     * @return
     */
    public static Uri getFileUri(Context context, File file, @ContentType String type) {

        Uri uri = null;
        if (Build.VERSION.SDK_INT < N) {
            uri = Uri.fromFile(file);
        } else {
            uri = getFileContentUri(context, file, type);
        }

        if (uri == null) {
            uri = forceGetFileUri(file);
        }
        return uri;
    }

    private static Uri getFileContentUri(Context context, File file, @ContentType String type) {
        String filePath = file.getAbsolutePath();
        String volumeName = "external";

        Cursor cursor = null;
        Uri uri = null;
        Uri baseUri = null;
        switch (type) {
            case ContentType.TEXT:

                break;
            case ContentType.IMAGE:
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                        new String[]{filePath}, null);
                baseUri = Uri.parse("content://media/external/images/media");
                break;
            case ContentType.AUDIO:
                cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Audio.Media._ID}, MediaStore.Audio.Media.DATA + "=? ",
                        new String[]{filePath}, null);
                baseUri = Uri.parse("content://media/external/audio/media");
                break;
            case ContentType.VIDEO:
                cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Video.Media._ID}, MediaStore.Video.Media.DATA + "=? ",
                        new String[]{filePath}, null);
                baseUri = Uri.parse("content://media/external/video/media");
                break;
            case ContentType.FILE:
                cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(volumeName),
                        new String[]{MediaStore.Files.FileColumns._ID}, MediaStore.Files.FileColumns.DATA + "=? ",
                        new String[]{filePath}, null);

                break;
            default:
                break;
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                if (type == ContentType.FILE) {
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
                    uri = MediaStore.Files.getContentUri(volumeName, id);
                } else {
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                    uri = Uri.withAppendedPath(baseUri, id + "");
                }
            }
            cursor.close();
        }

        return uri;
    }

    private static Uri forceGetFileUri(File file) {
        if (Build.VERSION.SDK_INT >= N) {//fixme : FileProvider
            try {
                Method method = StrictMode.class.getDeclaredMethod("disableDeathOnFileUriExposure");
                method.invoke(null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return Uri.parse("file://" + file.getAbsolutePath());
    }
}
