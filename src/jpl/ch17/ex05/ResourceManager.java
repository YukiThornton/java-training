package jpl.ch17.ex05;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public final class ResourceManager {
    final ReferenceQueue<Object> queue;
    final Map<Reference<?>, Resource> refs;
    boolean shutdown = false;
    
    public ResourceManager() {
        queue = new ReferenceQueue<Object>();
        refs = new HashMap<Reference<?>, Resource>();
        
        // Do something.
    }
    
    public synchronized void shutdown() {
        if (!shutdown) {
            shutdown = true;
            while(refs.size() > 0) {
                releaseQueuedResources();
            }
        }
    }
    
    public synchronized Resource getResource(Object key) {
        if (shutdown)
            throw new IllegalArgumentException();
        while(queue.poll() != null) {
            releaseQueuedResources();
        }
        Resource res = new ResourceImpl(key);
        Reference<?> ref = new PhantomReference<Object>(key, queue);
        refs.put(ref, res);
        return res;
    }
    
    private void releaseQueuedResources() {
        try {
            Reference<?> ref = queue.remove();
            Resource res = refs.get(ref);
            refs.remove(ref);
            res.release();
            ref.clear();
        } catch (InterruptedException e) {
            // Do nothing.
        }
    
    }
}
