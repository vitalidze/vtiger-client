package su.litvak.vtiger;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaly
 * Date: 27.08.13
 * Time: 0:50
 * To change this template use File | Settings | File Templates.
 */
public class VtigerResponse<T> {
    public boolean success;
    public VtigerError error;
    public T result;
}
