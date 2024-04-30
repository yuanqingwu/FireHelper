package com.wyq.firehelper.article;

import android.util.LruCache;

import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.article.entity.ArticleSaveEntity;
import com.wyq.firehelper.base.aop.aspectj.FireLogTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 统一数据来源
 */
public class ArticleRepository {
    private static ArticleRepository repository;

    private MMKV articleMMKV = null;
    public static final String MMKV_ID_ARTICLE = "ARTICLE";
    public static final String MMKV_KEY_Hot_ARTICLE = "HOT_ARTICLE";

    private Map<String, Integer> frequencyMap = null;

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

    public synchronized boolean save(String key, ArticleSaveEntity saveEntity) {
        if (key == null || saveEntity == null) {
            return false;
        }
        return articleMMKV.encode(key, ArticleSaveEntity.convert2Bytes(saveEntity));
    }

    public synchronized boolean delete(String key) {
        if (key == null) {
            return false;
        }
        articleMMKV.removeValueForKey(key);
        articleMMKV.clearMemoryCache();
        return !articleMMKV.contains(key);
    }

    public synchronized boolean clearAll() {
        articleMMKV.clearAll();
        return articleMMKV.totalSize() <= 0;
    }

    public synchronized ArticleSaveEntity get(String key) {
        if (key == null) {
            return null;
        }
        byte[] bytes = articleMMKV.decodeBytes(key);
        if (bytes != null && bytes.length > 0) {
            return ArticleSaveEntity.convertFromBytes(bytes);
        } else {
            return null;
        }
    }

    public synchronized List<String> getAllSavedKeys() {
        if (articleMMKV.count() == 0) {
            return null;
        }
        return Arrays.asList(articleMMKV.allKeys());
    }

    public synchronized List<ArticleResource> getListByFilter(String[] filters) {
        return ArticleConstants.getListByFilter(filters);
    }

    public synchronized List<ArticleSaveEntity> getAllSavedArticles() {
        if (articleMMKV.count() == 0) {
            return null;
        }
        List<ArticleSaveEntity> articles = new ArrayList<>();
        for (String key : articleMMKV.allKeys()) {
            if (!key.equals(MMKV_KEY_Hot_ARTICLE)) {
                ArticleSaveEntity entity = ArticleSaveEntity.convertFromBytes(articleMMKV.decodeBytes(key));
                if (entity != null) {
                    articles.add(entity);
                }
            }
        }
        return articles;
    }

    public synchronized boolean contains(String key) {
        if (key == null) {
            return false;
        }
        articleMMKV.clearMemoryCache();
        return articleMMKV.contains(key);
    }


    private synchronized int getAllArticleSize() {
        return ArticleConstants.getAllArticles().size();
    }

    public class LruMap<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {

        private int capacity = 0;

        public LruMap(int capacity) {
            super(0, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    LruCache<String, Integer> lruMap = new LruCache<String, Integer>(1) {
        @Override
        protected int sizeOf(String key, Integer value) {
            return super.sizeOf(key, value);
        }
    };

    @FireLogTime
    public synchronized void initFrequencyMap() {
        if (frequencyMap == null) {
            frequencyMap = new HashMap<>();
        }

        frequencyMap.clear();
        articleMMKV.clearMemoryCache();
        if (articleMMKV.contains(MMKV_KEY_Hot_ARTICLE)) {
            String hotArticle = articleMMKV.decodeString(MMKV_KEY_Hot_ARTICLE);
            try {
                JSONObject jsonObject = new JSONObject(hotArticle);
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    frequencyMap.put(key, jsonObject.getInt(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //如果有新加的文章,map未包含所有定义的文章的情况
        if (frequencyMap.size() != 0 && frequencyMap.size() != getAllArticleSize()) {
            List<String> urls = ArticleConstants.diffArticlesByUrl(frequencyMap.keySet().toArray());
            for (String url : urls) {
                frequencyMap.put(url, 0);
            }
        }

        //如果列表还是为空则初始化
        if (frequencyMap == null || frequencyMap.size() == 0) {
            List<ArticleResource> resources = ArticleConstants.getAllArticles();
            for (ArticleResource resource : resources) {
                frequencyMap.put(resource.getUrl(), 0);
            }
            saveHotArticleToMMKV(frequencyMap);
        }

    }

    @FireLogTime
    private synchronized boolean saveHotArticleToMMKV(Map<String, Integer> frequencyMap) {
        JSONObject jsonObject = new JSONObject(frequencyMap);
        return articleMMKV.encode(MMKV_KEY_Hot_ARTICLE, jsonObject.toString());
    }

    @FireLogTime
    public synchronized void commitHotDegree(final String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                if (frequencyMap == null || frequencyMap.size() != getAllArticleSize()) {
                    initFrequencyMap();
                }
                if (frequencyMap.containsKey(url)) {
                    frequencyMap.put(url, frequencyMap.get(url) + 1);
                    saveHotArticleToMMKV(frequencyMap);
                }
            }
        }).subscribe();
    }

    @FireLogTime
    public synchronized List<ArticleResource> getAllArticleByFrequency() {
        if (frequencyMap == null) {
            initFrequencyMap();
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //降序
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<ArticleResource> articleResources = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            articleResources.add(ArticleConstants.getArticleByUrl(entry.getKey()));
        }
        return articleResources;
    }

    /**
     * 获取访问次数前几的文章列表
     *
     * @param topCount
     * @return
     */
    @FireLogTime
    public synchronized Observable getArticleTopFrequency(final int topCount) {
        Observable hotObservable = Observable.create(new ObservableOnSubscribe<List<ArticleResource>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleResource>> emitter) throws Exception {
                List<ArticleResource> articles = getAllArticleByFrequency();
                if (articles != null && articles.size() >= topCount) {
                    emitter.onNext(articles.subList(0, topCount));
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return hotObservable;
    }
}
