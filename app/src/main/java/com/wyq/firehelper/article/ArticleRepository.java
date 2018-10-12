package com.wyq.firehelper.article;

import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.article.entity.ArticleSaveEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private static ArticleRepository repository;

    private MMKV articleMMKV = null;
    public static final String MMKV_ID_ARTICLE = "ARTICLE";

    private ArticleRepository() {
        articleMMKV = MMKV.mmkvWithID(MMKV_ID_ARTICLE, MMKV.MULTI_PROCESS_MODE);
    }

    public static ArticleRepository getInstance() {
        if (repository == null) {
            synchronized (ArticleRepository.class) {
                repository = new ArticleRepository();
            }
        }
        return repository;
    }

    public boolean save(String key, ArticleSaveEntity saveEntity) {
        if (key == null || saveEntity == null) {
            return false;
        }
        return articleMMKV.encode(key, ArticleSaveEntity.convert2Bytes(saveEntity));
    }

    public boolean delete(String key) {
        if (key == null) {
            return false;
        }
        articleMMKV.removeValueForKey(key);
        articleMMKV.clearMemoryCache();
        if (articleMMKV.contains(key)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean clearAll() {
        articleMMKV.clearAll();
        if (articleMMKV.totalSize() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArticleSaveEntity get(String key) {
        if (key == null) {
            return null;
        }
        byte[] bytes = articleMMKV.decodeBytes(key);
        if (bytes != null && bytes.length > 0)
            return ArticleSaveEntity.convertFromBytes(bytes);
        else
            return null;
    }

    public List<ArticleSaveEntity> getAll() {
        if (articleMMKV.count() == 0) {
            return null;
        }
        List<ArticleSaveEntity> articles = new ArrayList<>();
        for (String key : articleMMKV.allKeys()) {
            ArticleSaveEntity entity = ArticleSaveEntity.convertFromBytes(articleMMKV.decodeBytes(key));
            if (entity != null) {
                articles.add(entity);
            }
        }
        return articles;
    }

    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        articleMMKV.clearMemoryCache();
        return articleMMKV.contains(key);
    }
}
