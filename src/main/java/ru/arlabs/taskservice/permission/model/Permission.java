package ru.arlabs.taskservice.permission.model;

/**
 * @author Jeb
 */
public enum Permission {
    READ(false, false),
    PERFORMER(false, true),
    WRITE(true, true),
    ADMINISTRATOR(true, true);

    private final boolean write;
    private final boolean perform;

    Permission(boolean write, boolean perform) {
        this.write = write;
        this.perform = perform;
    }

    public boolean canWrite() {
        return write;
    }

    public boolean canPerform() {
        return perform;
    }
}
