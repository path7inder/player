package com.path7inder.player.model;

public class Video {
    public final long id;
    public final String displayName;
    public final long duration;

    public Video(long id, String displayName, long duration) {
        this.id = id;
        this.displayName = displayName;
        this.duration = duration;
    }
}
