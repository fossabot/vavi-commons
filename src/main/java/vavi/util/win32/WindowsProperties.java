/*
 * Copyright (c) 2002 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.win32;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import vavi.util.Debug;
// main
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * .ini ���������߂̃N���X�ł��D
 * 
 * Properties �N���X�̊g���N���X�Ƃ��Ĉ������߃v���p�e�B�̃L�[����
 * "�Z�N�V������" + "." + "�L�[��" �Ƃ��Ĉ����Ă��������D
 * 
 * ���ӁF�v���p�e�B�̏����A�R�����g�͕ۑ�����܂���B
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 020502 nsano initial version <br>
 *          0.01 031220 nsano clean imports <br>
 */
public class WindowsProperties extends Properties {

    /** The vector for section */
    private Vector sections = new Vector();

    /**
     * .ini �̃X�g���[����ǂݍ��݂܂��D
     */
    public void load(InputStream in) throws IOException {

        String section = null;

        this.clear();

        BufferedReader r = new BufferedReader(new InputStreamReader(in));
Debug.println("---- loading ----");
        while (r.ready()) {
            String s = r.readLine().trim();

            if (s.startsWith(";")) {
                // comment
Debug.println("comment: " + s);
            } else if (s.startsWith("[") && s.endsWith("]")) {
                // start a section
                section = s.substring(1, s.length() - 1);
//Debug.println("start section: " + section);
            } else {
                // inside a section
                if (section != null) {
                    int p = s.indexOf('=');
                    if (p != -1) {
                        String key = s.substring(0, p);
                        String value = s.substring(p + 1, s.length());
//System.err.println("set prop: " + section + "." + key + "=" + value);
                        this.setProperty(section + "." + key, value);
                    } else {
Debug.println("bad line: " + s);
                    }
                } else {
Debug.println("outside section: " + s);
                }
            }
        }
    }

    /**
     * �Z�N�V�������������߃I�[�o�[���C�h���܂��D
     * @param	key	"�Z�N�V������" + "." + "�L�[��"
     * @throws	IllegalArgumentException �Z�N�V�������������Ƃ�
     */
    public Object setProperty(String key, String value) {
        int p = key.indexOf('.');
        if (p == -1) {
            throw new IllegalArgumentException("no section: " + key);
        }
        String section = key.substring(0, p);
        if (!sections.contains(section)) {
            sections.addElement(section);
//Debug.println("add section: " + section);
        }
        return super.setProperty(key, value);
    }

    /**
     * .ini �`���ŃX�g���[���ɏ������݂܂��D
     * ���ӁF�v���p�e�B�̏����A�R�����g�͕ۑ�����܂���B
     */
    public void store(OutputStream out, String header) throws IOException {

        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(out));

Debug.println("---- storing ----");
        w.write(";" + header);
        w.newLine();
        w.write(";" + new Date());
        w.newLine();

        for (int i = 0; i < sections.size(); i++) {
            String section = (String) sections.elementAt(i);
Debug.println("start section: " + section);
            w.write("[" + section + "]");
            w.newLine();

            Enumeration e = this.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                if (key.startsWith(section + ".")) {
                    String value = this.getProperty(key);
                    key = key.substring(key.indexOf('.') + 1);
Debug.println("prop: " + key + "=" + value);
                    w.write(key + "=" + value);
                    w.newLine();
                }
            }
        }

        w.flush();
    }
    
    /**
     * �Y���Z�N�V�����̃v���p�e�B�����폜���܂��D
     */
    public void removePropertiesOfSection(String section) {
        Enumeration e = this.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key.startsWith(section + ".")) {
                this.remove(key);
Debug.println("delete: " + key);
            }
        }
        sections.removeElement(section);
    }

    /**
     * Tests this class.
     */
    public static void main(String[] args) throws IOException {
        WindowsProperties ini = new WindowsProperties();
        ini.load(new FileInputStream(args[0]));
        ini.list(System.out);
        ini.removePropertiesOfSection(args[2]);
        ini.store(new FileOutputStream(args[1]), args[1]);
    }
}

/* */
