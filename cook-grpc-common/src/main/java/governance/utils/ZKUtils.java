package governance.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKUtils {
    public static CuratorFramework getLocalCuratorFramework() {
        RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework cf = CuratorFrameworkFactory.newClient("localhost:2181", rp);
        cf.start();
        return cf;
    }
}
