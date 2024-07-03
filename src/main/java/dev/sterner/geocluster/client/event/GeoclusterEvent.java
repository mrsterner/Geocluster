package dev.sterner.geocluster.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class GeoclusterEvent {

    public static Event<GetClusterInfo> MODIFY_CLUSTER_INFO = EventFactory.createArrayBacked(GetClusterInfo.class, callbacks -> ((info) -> {
        for (GetClusterInfo event : callbacks)
            return event.getClusterInfo(info);
        return new ClusterInfo(info.player(), info.state(), info.name(), info.msg(), info.toastTexture(),false);
    }));


    @FunctionalInterface
    public interface GetClusterInfo {
        ClusterInfo getClusterInfo(ClusterInfo info);
    }
}
