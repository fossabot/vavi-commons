/*
 * Copyright (c) 2003 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.event;

import java.util.EventListener;


/**
 * �ėp�̃��X�i�C���^�[�t�F�[�X�ł��D
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 030318 nsano initial version <br>
 */
public interface GenericListener extends EventListener {

    /**
     * �C�x���g�����s���ꂽ���ɌĂ΂�܂��D
     * 
     * @param ev �ėp�C�x���g
     */
    void eventHappened(GenericEvent ev);
}

/* */
