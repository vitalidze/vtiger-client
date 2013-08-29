package su.litvak.vtiger;

public abstract class VtigerResponse<T> {
    public boolean success;
    public VtigerError error;
    public T result;
}
