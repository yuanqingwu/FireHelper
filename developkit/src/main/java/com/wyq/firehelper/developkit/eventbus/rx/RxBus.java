package com.wyq.firehelper.developkit.eventbus.rx;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.developkit.eventbus.EventBusMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;


/**
 * @author yuanqingwu
 * @date 2019/05/09
 */
public class RxBus {

    public abstract static class OnEventListener<T> {
        public abstract void onEvent(T t);
    }

    private final Map<Class, List<EventMessage>> stickyEventCache = new HashMap<>();

    private final FlowableProcessor<Object> mBus;

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }


    public void post(String key, Object event) {
        BusUtils.requireNonNull(key, event);
        EventMessage message = new EventMessage(key, event);
        mBus.onNext(message);
    }

    public void postSticky(String key, Object event) {
        BusUtils.requireNonNull(key, event);
        EventMessage message = new EventMessage(key, event);
        addStickyEvent(event, key);
        mBus.onNext(message);
    }

    public <T> void subscribe(final Object subscriber,
                              String tag,
                              boolean isSticky,
                              final OnEventListener<T> listener) {

        BusUtils.requireNonNull(subscriber, tag, listener);
        final Class<T> typeClass = BusUtils.getTypeClassFromParadigm(listener);

        if(isSticky){

        }

        mBus.ofType(EventMessage.class)
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<EventMessage>() {
                    @Override
                    public boolean test(EventMessage message) {
                        return message.isSameType(typeClass, tag);
                    }
                }).map(new Function<EventMessage, Object>() {
            @Override
            public Object apply(EventMessage eventMessage) throws Exception {
                return eventMessage.getAction();
            }
        }).cast(typeClass)
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        listener.onEvent(t);
                    }
                })
        ;
    }


    private void addStickyEvent(final Object event, final String tag) {
        Class eventType = BusUtils.getClassFromObject(event);
        synchronized (stickyEventCache) {
            List<EventMessage> stickyEvents = stickyEventCache.get(eventType);
            if (stickyEvents == null) {
                stickyEvents = new ArrayList<>();
                stickyEvents.add(new EventMessage(tag, event));
                stickyEventCache.put(eventType, stickyEvents);
            } else {
                for (int i = stickyEvents.size() - 1; i >= 0; --i) {
                    EventMessage tmp = stickyEvents.get(i);
                    if (tmp.isSameType(eventType, tag)) {
                        Logger.i("The sticky event already added.");
                        return;
                    }
                }
                stickyEvents.add(new EventMessage(tag, event));
            }
        }
    }

    EventMessage findStickyEvent(final Class eventType, final String tag) {
        synchronized (stickyEventCache) {
            List<EventMessage> stickyEvents = stickyEventCache.get(eventType);
            if (stickyEvents == null) {
                return null;
            }
            int size = stickyEvents.size();
            EventMessage res = null;
            for (int i = size - 1; i >= 0; --i) {
                EventMessage stickyEvent = stickyEvents.get(i);
                if (stickyEvent.isSameType(eventType, tag)) {
                    res = stickyEvents.get(i);
                    break;
                }
            }
            return res;
        }
    }
}
