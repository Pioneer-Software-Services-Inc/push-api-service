package com.acme.services.push.file;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.validation.constraints.NotNull;

import com.acme.services.push.exception.Handler;

/**
 * @author cgordon
 * @created 09/13/2017
 * @version 1.0
 *
 * This is a java 1.8 Functional Interface that provides lazy initialization of potentially large data objects
 * and delays loading them into memory ie.e until needed. (Generalized Target-Type Inference <T> is used to represent any type of class/object)
 *  
 * Lazy<T> lazyObj = () -> new T(:) or Lazy<T> objectInstance = () -> new T();
 * System.out.println(lazyObj.get());
 *
 */
@FunctionalInterface
public interface Lazy<T> extends Supplier<T> {
    abstract class Cache {
    	
    	/** declare instances volatile to mitigate double checked locking in multi-thread complication */
        private volatile static Map<Integer, Object> instances = new HashMap<>();

        private static synchronized Object getInstance(int instanceId, @NotNull Supplier<Object> create) {

            Object instance = instances.get(instanceId);
            if (instance == null) {
                synchronized (Cache.class) {
                    instance = instances.get(instanceId);
                    if (instance == null) {
                        instance = create.get();
                        instances.put(instanceId, instance);
                    }
                }
            }
            return instance;
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public default T get() {
        return (T) Cache.getInstance(this.hashCode(), Handler.unchecked(() -> init()));
    }

    T init();
}