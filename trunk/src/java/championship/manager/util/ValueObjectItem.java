package championship.manager.util;

// TODO: document me!!!

/**
 * ValueObjectItem.
 * <p/>
 * User: rro
 * Date: 01.01.2006
 * Time: 12:34:26
 *
 * @author Roman R&auml;dle
 * @version $Id: ValueObjectItem.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class ValueObjectItem<V, O> {

    private V value;
    private O object;

    public ValueObjectItem() {

    }

    public ValueObjectItem(V value, O object) {
        this.value = value;
        this.object = object;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }


    public String toString() {
        return value.toString();
    }
}
