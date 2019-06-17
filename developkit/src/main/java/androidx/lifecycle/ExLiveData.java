package androidx.lifecycle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;

/**
 * @author yuanqingwu
 * @date 2019/05/17
 */
public class ExLiveData<T> extends MutableLiveData<T> {

    public static final int START_VERSION = LiveData.START_VERSION;

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if (owner.getLifecycle().getCurrentState() == DESTROYED) {
            // ignore
            return;
        }
        LifecycleBoundObserver wrapper = new ExLifecycleBoundObserver(owner, observer);
        LifecycleBoundObserver existing = (LifecycleBoundObserver)invokeCallIfAbsent(observer, wrapper);
        if (existing != null && !existing.isAttachedTo(owner)) {
            throw new IllegalArgumentException("Cannot add the same observer"
                    + " with different lifecycles");
        }
        if (existing != null) {
            return;
        }
        owner.getLifecycle().addObserver(wrapper);
    }

    @Override
    public int getVersion() {
        return super.getVersion();
    }

    protected Lifecycle.State observerActiveLevel() {
        return Lifecycle.State.CREATED;
    }

    class ExLifecycleBoundObserver extends LifecycleBoundObserver {

        ExLifecycleBoundObserver(@NonNull LifecycleOwner owner, Observer<? super T> observer) {
            super(owner, observer);
        }

        @Override
        boolean shouldBeActive() {
            return mOwner.getLifecycle().getCurrentState().isAtLeast(observerActiveLevel());
        }
    }


    /**
     * 反射调用LiveData的putIfAbsent方法
     * @param observer
     * @param wrapper
     * @return
     */
    private Object invokeCallIfAbsent(Object observer,Object wrapper){
        try {
            Field observerField = LiveData.class.getDeclaredField("mObservers");
            observerField.setAccessible(true);
            Object observers = observerField.get(this);

            Method method = observers.getClass().getDeclaredMethod("putIfAbsent",Object.class,Object.class);
            method.invoke(observers,observer,wrapper);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
