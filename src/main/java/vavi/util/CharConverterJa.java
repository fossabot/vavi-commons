/*
 * Copyright (c) 2002 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import java.lang.String;
import java.lang.StringBuilder;


/**
 * ���{�ꕶ���̕������ƃJ�^�J�i�̑��ݕϊ��ƁC
 * ���p�ƑS�p�����̑��ݕϊ����s���܂��D
 * 
 * TODO StringNormalizer#normalize
 * 
 * @author Takashi Okamoto
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.20 T.O original version <br>
 *          0.30 021120 nsano refine <br>
 */
public final class CharConverterJa {

    /** */
    private CharConverterJa() {}

    /** */
    private static final String kanaTableJa[] = {
        "��B##",
        "��u##",
        "��v##",
        "��E##",
        "���##",
        "��@##",
        "��B##",
        "��D##",
        "��F##",
        "��H##",
        "���##",
        "���##",
        "���##",
        "��b##",
        "��[##",
        "��A##",
        "��C##",
        "��E##",
        "��G##",
        "��I##",
        "��J�K#",
        "��L�M#",
        "��N�O#",
        "��P�Q#",
        "��R�S#",
        "��T�U#",
        "��V�W#",
        "��X�Y#",
        "��Z�[#",
        "��\�]#",
        "��^�_#",
        "��`�a#",
        "c�d#",
        "Ãe�f#",
        "ăg�h#",
        "Ńi##",
        "ƃj##",
        "ǃk##",
        "ȃl##",
        "Ƀm##",
        "ʃn�o�p",
        "˃q�r�s",
        "̃t�u�v",
        "̓w�x�y",
        "΃z�{�|",
        "σ}##",
        "Ѓ~##",
        "у�##",
        "҃�##",
        "Ӄ�##",
        "ԃ�##",
        "Ճ�##",
        "փ�##",
        "׃�##",
        "؃�##",
        "ك�##",
        "ڃ�##",
        "ۃ�##",
        "܃�##",
        "݃�##",
        "ށJ##",
        "߁K##"
    };

    /**
     * ���������J�^�J�i�ɕϊ��ϊ�����B
     * @param str ���������܂ޕ�����
     * @return ���������J�^�J�i�ɕϊ����ꂽ������
     */
    public static String toKatakana(String str) {

        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char code = str.charAt(i);
            if ((code >= 0x3041) && (code <= 0x3093)) {
                // �������̂Ƃ��J�^�J�i�ɕϊ�
                ret.append((char) (code + 0x60));
            } else {
                // �������ȊO�́A���̂܂�
                ret.append(code);
            }
        }

        return ret.toString();
    }

    /**
     * �J�^�J�i�𕽉����ɕϊ��ϊ�����B
     * �������A���̕����́A�ϊ��ł��Ȃ�(�Ђ炪�ȂɑΉ����镶�����Ȃ�)
     * <UL>
     *  <LI>��(0x30f4)</LI>
     *  <LI>��(0x30f5)</LI>
     *  <LI>��(0x30f6)</LI>
     * </UL>
     * @param str �J�^�J�i���܂ޕ�����
     * @return �J�^�J�i���������ɕϊ����ꂽ������
     */
    public static String toHiragana(String str) {

        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char code = str.charAt(i);
            if ((code >= 0x30a1) && (code <= 0x30f3)) {
                // �J�^�J�i�̂Ƃ��������ɕϊ�
                ret.append((char) (code - 0x60));
            } else {
                // �J�^�J�i�ȊO�́A���̂܂�
                ret.append(code);
            }
        }

        return ret.toString();
    }

    /**
     * �S�p�p���L���𔼊p�ɕϊ�����B
     * @param str �S�p�p���L�����܂ޕ�����
     * @return �S�p�p���L�������p�ɕϊ����ꂽ������
     */
    public static String toHalfANS(String str) {

        StringBuilder ret = new StringBuilder();

        for (int i = 0;i < str.length(); i++) {
            int code = str.charAt(i);
            if ((code >= 0xff01) && (code <= 0xff5e)) {
                ret.append((char) (code - 0xfee0));
            } else {
                ret.append((char) code);
            }
        }

        return ret.toString();
    }

    /**
     * �S�p�������\�Ȍ��蔼�p�����ɕϊ�����B
     * @param str �S�p�������܂ޕ�����
     * @return �S�p���������p�ɕϊ����ꂽ������
     */
    public static String toHalf(String str) {
        return toHalfKana(toHalfANS(str));
    }

    /**
     * ���p�������\�Ȍ���S�p�����ɕϊ�����B
     * @param str �S�p�������܂ޕ�����
     * @return �S�p���������p�ɕϊ����ꂽ������
     */
    public static String toFull(String str) {
        return toFullKana(toFullANS(str));
    }

    /**
     * ���p�p���L����S�p�ɕϊ�����B
     * @param str ���p�p���L�����܂ޕ�����
     * @return ���p�p���L�����S�p�ɕϊ����ꂽ������
     */
    public static String toFullANS(String str) {

        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            int code = str.charAt(i);
            if ((code >= 0x21) && (code <= 0x7e)) {
                ret.append((char) (code + 0xfee0));
Debug.println((int) ret.charAt(i));
            } else {
                ret.append((char) code);
            }
        }

        return ret.toString();
    }

    /**
     * �S�p�����𔼊p�����ɕϊ�����B
     * @param str �S�p�������܂ޕ�����
     * @return �S�p���������p�ɕϊ����ꂽ������
     */
    public static String toHalfKana(String str) {

        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            int base = -1;
            int type = -1;
            char code = str.charAt(i);

            if (code != '#') {
                for (int t = 1; t < 4; t++) {
                    for (int j = 0; j < kanaTableJa.length; j++) {
                        if (code == kanaTableJa[j].charAt(t)) {
                            base = j;
                            type = t;
                            break;
                        }
                    }
                    if (type != -1) {
                        break;
                    }
                }
            }		

            switch (type) {
            case 1:
                ret.append(kanaTableJa[base].charAt(0));
                break;
            case 2:
                ret.append(kanaTableJa[base].charAt(0));
                ret.append('�');
                break;
            case 3:
                ret.append(kanaTableJa[base].charAt(0));
                ret.append('�');
                break;
            default:
                ret.append(code);
            }
        }

        return ret.toString();
    }

    /**
     * ���p�J�i��S�p�J�^�J�i�ɕϊ�����B<p>
     *
     * ���_�A�����_���܂܂��ꍇ�A�O�̕����ɓ���<br>
     * ��u�ށv���u�o�v(�u�n�J�v�ƂȂ�Ȃ�)<br>
     *
     * @param str ���p�J�^�J�i���܂ޕ�����
     * @return ���p�J�^�J�i���S�p�ɕϊ����ꂽ������
     */
    public static String toFullKana(String str) {

        StringBuilder ret = new StringBuilder();
        int prevBase = 0;

        for (int i = 0; i < str.length(); i++) {
// 	    int base = -1;
            char code = str.charAt(i);

            if ((code == '�') && (kanaTableJa[prevBase].charAt(2) != '#')) {
                // ���_���󗝂��A1�O�̕��������_�t�^�\�ȕ����̏ꍇ

                // 1�����폜
                ret.deleteCharAt(ret.length() - 1);

                // ���_�����ǉ�
                ret.append(kanaTableJa[prevBase].charAt(2));

                prevBase = 0;
                continue;
            } else if ((code == '�') && (kanaTableJa[prevBase].charAt(2) != '#')) {
                // �����_���󗝂��A1�O�̕��������_�t�^�\�ȕ����̏ꍇ

                // 1�����폜
                ret.deleteCharAt(ret.length() - 1);

                // �����_�����ǉ�
                ret.append(kanaTableJa[prevBase].charAt(3));

                prevBase = 0;
                continue;
            }

            boolean flag = false;

            for (int j = 0; j < kanaTableJa.length; j++) {
                // �e�|�u���𑖍�
                if (code == kanaTableJa[j].charAt(0)) {
                    // ������������Βǉ�
                    flag = true;
                    ret.append(kanaTableJa[j].charAt(1));
                    prevBase = j;
                    break;
                }
            }
            if (!flag) {
                ret.append(code);
                prevBase = 0;
            }
        }

        return ret.toString();
    }

    //----

    /** */
    private static final String fullTable =
        "�`�a�b�c�d�e�f�g�h�i�j�k�l�m�n�o�p�q�r�s�t�u�v�w�x�y" +
        "����������������������������������������������������" +
        "�O�P�Q�R�S�T�U�V�W�X" +
        "�@�i�j�g�h�f�D�C�o�p�m�n�Q��";

    /** */
    private static final String halfTable =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789" +
            " ()\"\"'.,{}[]_&";

    /** */
    public static String toFull2(String str) {
        for (int i = 0; i < halfTable.length(); i++) {
            str = str.replace(halfTable.charAt(i), fullTable.charAt(i));
        }

        return str;
    }

    /** */
    public static String toHalf2(String str) {
        for (int i = 0; i < fullTable.length(); i++) {
            str = str.replace(fullTable.charAt(i), halfTable.charAt(i));
        }

        return str;
    }
}

/* */
