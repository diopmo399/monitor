package utils;


import java.util.concurrent.Future;

public abstract class TacheNotifImpl implements TacheNotif{

    /**
     * Execute le test En Asynchrone
     * @return Future<Void>
     */
    @Override
    public Future<Void> doit() {
        executor.submit(() -> job());
        return null;
    }


    protected abstract void job();
}
