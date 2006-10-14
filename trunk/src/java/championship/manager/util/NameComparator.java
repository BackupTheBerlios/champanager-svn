package championship.manager.util;

import java.util.Comparator;
import java.text.Collator;

// TODO: document me!!!

/**
 * NameComparator.
 * <p/>
 * User: rro
 * Date: 02.01.2006
 * Time: 01:59:30
 *
 * @author Roman R&auml;dle
 * @version $Id: NameComparator.java,v 1.1 2006/04/05 09:09:14 raedler Exp $
 */
public class NameComparator implements Comparator {

    private Collator collator = Collator.getInstance();

    public int compare(Object o1, Object o2) {

        if (o1 instanceof Name && o2 instanceof Name) {

            Name name1 = (Name) o1;
            Name name2 = (Name) o2;

            return collator.compare(name1.getName(), name2.getName());
        }

        return 0;
    }
}
