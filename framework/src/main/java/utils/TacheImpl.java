package utils;


import java.util.concurrent.Future;

public abstract class TacheImpl implements Tache{
    /**
     * Execute le test En Asynchrone
     * @return Future<Void>
     */
    @Override
    public Future<Void> doit() {
        executor.submit(this::job);
        return null;
    }

    protected abstract void job();
}
