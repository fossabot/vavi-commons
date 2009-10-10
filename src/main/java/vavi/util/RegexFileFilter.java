/*
 * Copyright (c) 2001 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;


/**
 * Regex �Ńt�B���^��������t�@�C���t�B���^�ł��D
 * 
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 010910 nsano initial version <br>
 *          0.01 010912 nsano ignors InterruptedException <br>
 */
public class RegexFileFilter extends javax.swing.filechooser.FileFilter implements FileFilter {

    /** �p�^�[���̔z�� */
    private List<String> regexs = new ArrayList<String>(1);

    /** �t�B���^�̐��� */
    private String description;

    /**
     * �t�@�C���t�B���^���쐬���܂��D
     */
    public RegexFileFilter() {
    }

    /**
     * �w�肵���p�^�[���̃t�@�C���t�B���^���쐬���܂��D
     * 
     * @param regex �p�^�[��
     */
    public RegexFileFilter(String regex) {
        regexs.add(regex);
    }

    /**
     * �w�肵���p�^�[���̃t�@�C���t�B���^���쐬���܂��D
     * 
     * @param regex �p�^�[��
     */
    public RegexFileFilter(String regex, String description) {
        regexs.add(regex);
        this.description = description;
    }

    /**
     * �t�B���^�̐�����ݒ肵�܂��D
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * �p�^�[����ǉ����܂��D
     */
    public void addPattern(String regex) {
        regexs.add(regex);
    }

    /**
     * �w�肵���t�@�C�����t�B���^���󂯕t���邩�ǂ�����Ԃ��܂��D
     * �p�^�[���������ꍇ�ƁA�f�B���N�g���� true ��Ԃ��܂��B
     * @param file �Ώۂ̃t�@�C��
     */
    public boolean accept(File file) {

        if (file.isDirectory()) {
            return true;
        }

        if (regexs.size() == 0) {
Debug.println("no pattern");
            return true;
        }

        try {
            Iterator<String> i = regexs.iterator();
            while (i.hasNext()) {
                Pattern p = Pattern.compile(i.next());
                Matcher m = p.matcher(file.getName());
                if (m.matches()) {
                    return true;
                }
            }
        } catch (Exception e) {
// Debug.println(e);
        }

        return false;
    }

    /**
     * �t�B���^�̐�����Ԃ��܂��D
     */
    public String getDescription() {
        if (description != null) {
            return description;
        } else {
            if (regexs.size() == 0) {
                return "all";
            }
            description = new String("Regex: ");
            for (int i = 0; i < regexs.size(); i++) {
                description += "\'" + regexs.get(i) + "\', ";
            }
            description = description.substring(0, description.length() - 2);
            return description;
        }
    }

    /** Tests this class. */
    public static void main(String[] args) {
        RegexFileFilter fileFilter = new RegexFileFilter(".*\\.xml");
        JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        fc.setFileFilter(fileFilter);
        fc.showOpenDialog(null);
        System.exit(0);
    }
}

/* */
