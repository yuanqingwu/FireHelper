package com.wyq.firehelper.article.entity;

import android.graphics.Bitmap;

import com.wyq.firehelper.utils.BitmapUtils;
import com.wyq.firehelper.utils.CloseUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ArticleSaveEntity implements Serializable {

    private static final long serialVersionUID = -797152762623399874L;

    private ArticleResource resource;
    private String webTitle;
    private String webIcon;

    private int scrollY;//记录收藏时的阅读位置
    private float scaleSize;//记录收藏时界面缩放大小，默认1
    private String saveUserName;//收藏的用户名
    private long saveDate;//收藏的日期，默认当前时间

    public ArticleSaveEntity() {

    }

    public ArticleSaveEntity(ArticleResource resource) {
        this.resource = resource;
        this.scrollY = 0;
        this.scaleSize = 1;
        this.saveUserName = "";
        this.saveDate = System.currentTimeMillis();
    }

    public ArticleSaveEntity(ArticleResource resource, String webTitle, Bitmap webIcon, int scrollY, float scaleSize, String saveUserName, long saveDate) {
        this.resource = resource;
        this.webTitle = webTitle;
        this.webIcon = webIcon == null ? "" : BitmapUtils.bitmap2String(webIcon);
        this.scrollY = scrollY;
        this.scaleSize = scaleSize;
        this.saveUserName = saveUserName;
        this.saveDate = saveDate == 0 ? System.currentTimeMillis() : saveDate;
    }

    public ArticleResource getResource() {
        return resource;
    }

    public void setResource(ArticleResource resource) {
        this.resource = resource;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public Bitmap getWebIcon() {
        return BitmapUtils.string2Bitmap(webIcon);
    }

    public void setWebIcon(Bitmap webIcon) {
        this.webIcon = BitmapUtils.bitmap2String(webIcon);
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }

    public float getScaleSize() {
        return scaleSize;
    }

    public void setScaleSize(float scaleSize) {
        this.scaleSize = scaleSize;
    }

    public String getSaveUserName() {
        return saveUserName;
    }

    public void setSaveUserName(String saveUserName) {
        this.saveUserName = saveUserName;
    }

    public long getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(long saveDate) {
        this.saveDate = saveDate;
    }

    @Override
    public String toString() {
        return "ArticleSaveEntity{" +
                "resource=" + resource.toString() +
                ", \nwebTitle='" + webTitle + '\'' +
                ", \nwebIcon=" + webIcon.length() +
                ", \nscrollY=" + scrollY +
                ", \nscaleSize=" + scaleSize +
                ", \nsaveUserName='" + saveUserName + '\'' +
                ", \nsaveDate=" + saveDate +
                '}';
    }

    public static byte[] convert2Bytes(ArticleSaveEntity entity) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(entity);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(oos);
            CloseUtils.closeIO(bos);
        }
        return null;
    }


    public static ArticleSaveEntity convertFromBytes(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            ArticleSaveEntity entity = (ArticleSaveEntity) ois.readObject();
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(ois);
            CloseUtils.closeIO(bis);
        }
        return null;
    }
}
