/*
 * Copyright (c) 2003 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.event;

import java.io.Serializable;
import javax.swing.event.EventListenerList;


/**
 * �ėp�C�x���g�@�\�̂̊�{�����N���X�ł��D
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 030318 nsano initial version <br>
 */
public class GenericSupport implements Serializable {

    /** The generic listeners */
    private EventListenerList listenerList = new EventListenerList();

    /** GenericListener ��ǉ����܂��D */
    public void addGenericListener(GenericListener l) {
        listenerList.add(GenericListener.class, l);
    }

    /** GenericListener ���폜���܂��D */
    public void removeGenericListener(GenericListener l) {
        listenerList.remove(GenericListener.class, l);
    }

    /** �ėp�C�x���g�𔭍s���܂��D */
    public void fireEventHappened(GenericEvent ev) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == GenericListener.class) {
                ((GenericListener) listeners[i + 1]).eventHappened(ev);
            }
        }
    }
}

/* */
