package de.novatec.vccservice;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LoadSimulator {

    private LoadWorker loadWorker;

    public void startCpuLoad() {
        if(loadWorker == null || !loadWorker.isRunning()) {
            loadWorker = new LoadWorker();
            Thread loadThread = new Thread(loadWorker);
            loadThread.run();
        }
    }

    public void stopCpuLoad() {
        if(loadWorker != null) {
            loadWorker.stop();
        }
    }

    public boolean isOverloading() {
        return loadWorker != null && loadWorker.isRunning();
    }
}

class LoadWorker implements Runnable {

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void run() {
        isRunning.set(true);
        while(isRunning.get()) {}
    }

    public void stop() {
        isRunning.set(false);
    }

    public boolean isRunning() {
        return isRunning.get();
    }
}
