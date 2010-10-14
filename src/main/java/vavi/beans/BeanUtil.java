/*
 * Copyright (c) 2008 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;


/**
 * BeanUtil.
 *
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version $Revision: 1.0 $ $Date: 2008/01/24 14:17:10 $ $Author: sano-n $
 */
public abstract class BeanUtil {

//  private static Log logger = LogFactory.getLog(CsvUtil.class);

    /**
     * ���߂ɒʏ�ɓǂ߂�t�B�[���h�Ŏ擾���܂��B
     * ���� Getter ���\�b�h(Bean�����K��, boolean�̏ꍇisFoo����)�Ŏ擾���܂��B
     * �Ō�� private �t�B�[���h�������I�Ɏ擾���܂��B
     * 
     * TODO use {@link java.beans.Introspector} ??? or {@link org.apache.commons.beanutils.BeanUtils}
     * 
     * @param field �ΏۂƂȂ�t�B�[���h��`
     * @param bean �擾�Ώۂ̃I�u�W�F�N�g
     */
    public static Object getFieldValue(Field field, Object bean) {
        Class<?> beanClass = bean.getClass();
        Class<?> fieldClass = field.getType();
        try {
            return field.get(bean);
        } catch (IllegalAccessException e) {
            String fieldName = field.getName();
            try {
//logger.debug("method: " + "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
                Method method = beanClass.getMethod("get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
                return method.invoke(bean);
            } catch (NoSuchMethodException e2) {
                if (Boolean.TYPE.equals(fieldClass)) {
                    try {
                        Method method = beanClass.getMethod("is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
                        return method.invoke(bean);
                    } catch (InvocationTargetException e3) {
                        throw (RuntimeException) new IllegalStateException().initCause(e); 
                    } catch (NoSuchMethodException e3) {
                        return getPrivateFieldValue(field, bean);
//                      throw new IllegalStateException("no access method for: " + fieldName); 
                    } catch (IllegalAccessException e3) {
                        throw (RuntimeException) new IllegalStateException().initCause(e); 
                    }
                } else {
                    return getPrivateFieldValue(field, bean);
//                  throw new IllegalStateException("no access method for: " + fieldName); 
                }
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException(e); 
            } catch (IllegalAccessException e2) {
                throw new IllegalStateException(e); 
            }
        } 
    }

    /**
     * ���߂ɒʏ�ɓǂ߂�t�B�[���h�Őݒ肵�܂��B
     * ���� Setter ���\�b�h�Őݒ肵�܂��B
     * �Ō�� private �t�B�[���h�������I�ɐݒ肵�܂��B
     * 
     * TODO use {@link java.beans.Introspector} ??? or {@link org.apache.commons.beanutils.BeanUtils}
     * 
     * @param field �ΏۂƂȂ�t�B�[���h��`
     * @param bean �ݒ�Ώۂ̃I�u�W�F�N�g
     * @param value �ݒ肷��l
     */
    public static void setFieldValue(Field field, Object bean, Object value) {
        Class<?> beanClass = bean.getClass();
        Class<?> fieldClass = field.getType();
        try {
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            String fieldName = field.getName();
            try {
//logger.debug("method: " + "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1));
                Method method = beanClass.getMethod("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), fieldClass);
                method.invoke(bean, value);
            } catch (NoSuchMethodException e2) {
                setPrivateFieldValue(field, bean, value);
//              throw new IllegalStateException("no access method for: " + fieldName); 
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException(e); 
            } catch (IllegalAccessException e2) {
                throw new IllegalStateException(e); 
            }
        } 
    }

    /** */
    private static Field getPrivateField(final Class<?> clazz, final String name) throws PrivilegedActionException {
        return AccessController.doPrivileged(new PrivilegedExceptionAction<Field>() {
            /** @throws NoSuchFieldException */
            public Field run() throws Exception {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            }
        });
    }

    /** */
    private static Object getPrivateFieldValue(Field field, Object bean) {
        try {
            Field accessibleField = getPrivateField(field.getDeclaringClass(), field.getName());
            return accessibleField.get(bean);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e); 
        } catch (PrivilegedActionException e) {
            throw new IllegalStateException(e); 
        }
    }

    /** */
    private static void setPrivateFieldValue(Field field, Object bean, Object value) {
        try {
            Field accessibleField = getPrivateField(field.getDeclaringClass(), field.getName());
            accessibleField.set(bean, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e); 
        } catch (PrivilegedActionException e) {
            throw new IllegalStateException(e); 
        }
    }
}

/* */
