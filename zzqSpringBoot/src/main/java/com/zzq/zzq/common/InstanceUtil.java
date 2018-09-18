package com.zzq.zzq.common;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceUtil {
    public static Map<String, Class<?>> clazzMap = new HashMap();
    public static Map<String, MethodAccess> methodMap = new HashMap();

    private InstanceUtil() {
    }

    public static final <T> T to(Object orig, Class<T> clazz) {
        Object bean = null;

        try {
            bean = clazz.newInstance();
            PropertyUtils.copyProperties(bean, orig);
        } catch (Exception var4) {

        }

        return (T) bean;
    }

    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var4 = propertyDescriptors;
            int var5 = propertyDescriptors.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                PropertyDescriptor property = var4[var6];
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception var11) {
            System.out.println("transMap2Bean Error " + var11);
        }

    }

    public static Map<String, Object> transBean2Map(Object obj) {
        Map<String, Object> map = newHashMap();
        if (obj == null) {
            return map;
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!key.equals("class")) {
                        Method getter = property.getReadMethod();
                        if (getter == null) {
                            System.out.println("transBean2Map Error getter-null:" + key);
                        } else {
                            Object value = getter.invoke(obj);
                            map.put(key, value);
                        }
                    }
                }
            } catch (Exception var11) {
                System.out.println("transBean2Map Error " + var11);
            }

            return map;
        }
    }

    public static Map<String, Object> transBean2StringMap(Object obj) {
        Map<String, Object> map = transBean2Map(obj);
        Map<String, Object> result = newHashMap();
        Iterator var3 = map.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var3.next();
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                Object val = entry.getValue();
                if (val.getClass().isEnum()) {
                    val = String.valueOf(val);
                }

                result.put(entry.getKey(), val);
            }
        }

        return result;
    }

    public static <T> T getDiff(T oldBean, T newBean) {
        if (oldBean == null && newBean != null) {
            return newBean;
        } else if (newBean == null) {
            return null;
        } else {
            Class cls1 = oldBean.getClass();

            try {
                T object = (T) cls1.newInstance();
                BeanInfo beanInfo = Introspector.getBeanInfo(cls1);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var6 = propertyDescriptors;
                int var7 = propertyDescriptors.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    PropertyDescriptor property = var6[var8];
                    String key = property.getName();
                    if (!key.equals("class")) {
                        Method getter = property.getReadMethod();
                        Method setter = property.getWriteMethod();
                        if (setter != null) {
                            Object oldValue = getter.invoke(oldBean);
                            Object newValue = getter.invoke(newBean);
                            if (newValue != null) {
                                if (oldValue == null) {
                                    setter.invoke(object, newValue);
                                } else if (oldValue != null && !newValue.equals(oldValue)) {
                                    setter.invoke(object, newValue);
                                }
                            }
                        }
                    }
                }

                return object;
            } catch (Exception var15) {
                throw new DataParseException(var15);
            }
        }
    }

    public static <T> Map<String, BeanValInfo> getDiff2(T oldBean, T newBean) {
        BeanWrapper oldBeanMapper = new BeanWrapperImpl(oldBean);
        BeanWrapper newBeanMapper = new BeanWrapperImpl(newBean);
        PropertyDescriptor[] pds = oldBeanMapper.getPropertyDescriptors();
        Map<String, BeanValInfo> result = new HashMap();
        PropertyDescriptor[] var6 = pds;
        int var7 = pds.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            PropertyDescriptor pd = var6[var8];
            Object oldValue = oldBeanMapper.getPropertyValue(pd.getName());
            Object newValue = newBeanMapper.getPropertyValue(pd.getName());
            if (oldValue != null || newValue != null) {
                BeanValInfo valInfo;
                if (oldValue != null && newValue != null) {
                    if (!oldValue.equals(newValue)) {
                        valInfo = new BeanValInfo();
                        valInfo.setNewVal(newValue);
                        valInfo.setOldVal(oldValue);
                        result.put(pd.getName(), valInfo);
                    }
                } else {
                    valInfo = new BeanValInfo();
                    valInfo.setNewVal(newValue);
                    valInfo.setOldVal(oldValue);
                    result.put(pd.getName(), valInfo);
                }
            }
        }

        return result;
    }

    public static final Class<?> getClass(String clazz) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            return loader != null ? Class.forName(clazz, true, loader) : Class.forName(clazz);
        } catch (ClassNotFoundException var3) {
            throw new InstanceException(var3);
        }
    }

    public static final <E> List<E> getInstanceList(Class<E> cls, List<?> list) {
        List<E> resultList = newArrayList();
        E object = null;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Map<?, ?> map = (Map) iterator.next();
            object = newInstance(cls, map);
            resultList.add(object);
        }

        return resultList;
    }

    public static final <E> List<E> getInstanceList(Class<E> cls, ResultSet rs) {
        ArrayList resultList = newArrayList();

        try {
            E object = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();

            while (rs.next()) {
                object = cls.newInstance();

                for (int i = 0; i < fields.length; ++i) {
                    String fieldName = fields[i].getName();
                    PropertyUtils.setProperty(object, fieldName, rs.getObject(fieldName));
                }

                resultList.add(object);
            }

            return resultList;
        } catch (Exception var7) {
            throw new InstanceException(var7);
        }
    }

    public static final <E> E newInstance(Class<E> cls, Map<String, ?> map) {
        Object object = null;

        try {
            object = cls.newInstance();
            BeanUtils.populate(object, map);
            return (E) object;
        } catch (Exception var4) {
            throw new InstanceException(var4);
        }
    }

    public static final Object newInstance(String clazz) {
        try {
            return getClass(clazz).newInstance();
        } catch (Exception var2) {
            throw new InstanceException(var2);
        }
    }

    public static final <K> K newInstance(Class<K> cls, Object... args) {
        try {
            Class<?>[] argsClass = null;
            if (args != null) {
                argsClass = new Class[args.length];
                int i = 0;

                for (int j = args.length; i < j; ++i) {
                    argsClass[i] = args[i].getClass();
                }
            }

            Constructor<K> cons = cls.getConstructor(argsClass);
            return cons.newInstance(args);
        } catch (Exception var5) {
            throw new InstanceException(var5);
        }
    }

    public static final Object newInstance(String className, Object... args) {
        try {
            Class<?> newoneClass = (Class) clazzMap.get(className);
            if (newoneClass == null) {
                newoneClass = Class.forName(className);
                clazzMap.put(className, newoneClass);
            }

            return newInstance(newoneClass, args);
        } catch (Exception var3) {
            throw new InstanceException(var3);
        }
    }

    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = getNullPropertySetNames(source);
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }

    public static Set<String> getNullPropertySetNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var4 = pds;
        int var5 = pds.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames;
    }

    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) {
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        Set<String> nullPropertyNames = getNullPropertySetNames(source);
        if (ignoreList != null) {
            Iterator var5 = ignoreList.iterator();

            while (var5.hasNext()) {
                String str = (String) var5.next();
                nullPropertyNames.add(str);
            }
        }

        String[] result = new String[nullPropertyNames.size()];
        result = (String[]) nullPropertyNames.toArray(result);
        org.springframework.beans.BeanUtils.copyProperties(source, target, result);
    }

    public static void copyPropertiesIgnoreNullAndBaseField(Object source, Object target) {
        copyPropertiesIgnoreNull(source, target, "id", "createTime", "updateTime", "createBy", "updateBy", "remark", "enable");
    }

    public static final Object invokeMethod(Object owner, String methodName, Object... args) {
        Class<?> ownerClass = owner.getClass();
        String key = null;
        if (args != null) {
            Class<?>[] argsClass = new Class[args.length];
            int i = 0;

            for (int j = args.length; i < j; ++i) {
                if (args[i] != null) {
                    argsClass[i] = args[i].getClass();
                }
            }

            key = ownerClass + "_" + methodName + "_" + StringUtils.join(argsClass, ",");
        } else {
            key = ownerClass + "_" + methodName;
        }

        MethodAccess methodAccess = (MethodAccess) methodMap.get(key);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(ownerClass);
            methodMap.put(key, methodAccess);
        }

        return methodAccess.invoke(owner, methodName, args);
    }

    public static final <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static final <E> ArrayList<E> newArrayList(E... e) {
        ArrayList<E> list = new ArrayList();
        Collections.addAll(list, e);
        return list;
    }

    public static final <k, v> HashMap<k, v> newHashMap() {
        return new HashMap();
    }

    public static final <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static final <k, v> Hashtable<k, v> newHashtable() {
        return new Hashtable();
    }

    public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static final <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static final <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    public static final <k, v> TreeMap<k, v> newTreeMap() {
        return new TreeMap();
    }

    public static final <E> TreeSet<E> newTreeSet() {
        return new TreeSet();
    }

    public static final <E> Vector<E> newVector() {
        return new Vector();
    }

    public static final <k, v> WeakHashMap<k, v> newWeakHashMap() {
        return new WeakHashMap();
    }

    public static final <k, v> Map<k, v> newHashMap(k key, v value) {
        Map<k, v> map = newHashMap();
        map.put(key, value);
        return map;
    }

    public static final <k, v> ConcurrentHashMap<k, v> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

}
