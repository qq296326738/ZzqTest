package com.zzq.zzq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zzq.zzq.common.Constants;
import com.zzq.zzq.common.DataUtil;
import com.zzq.zzq.common.InstanceUtil;
import com.zzq.zzq.mapper.IBaseMapper;
import com.zzq.zzq.model.BaseModel;
import com.zzq.zzq.redis.RedisLock;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class BaseService<T extends BaseModel> implements ApplicationContextAware {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected IBaseMapper<T> mapper;
    @Autowired
    private RedisLock redisLock;
    protected ApplicationContext applicationContext;

    public BaseService() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static Page<Long> getPage(Map<String, Object> params) {
        Integer current = Integer.valueOf(1);
        Integer size = Integer.valueOf(10);
        String orderBy = "create_time";
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }

        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
            current = Integer.valueOf(params.get("pageIndex").toString());
        }

        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }

        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }

        if (size.intValue() == -1) {
            return new Page();
        } else {
            Page<Long> page = new Page(current.intValue(), size.intValue(), orderBy);
            page.setAsc(false);
            return page;
        }
    }

    public Page<T> getPage(Page<Long> ids) {
        if (ids == null) {
            return new Page();
        } else {
            Page<T> page = new Page(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<T> records = InstanceUtil.newArrayList();
            Iterator var4 = ids.getRecords().iterator();

            while (var4.hasNext()) {
                Long id = (Long) var4.next();
                records.add(this.queryById(id));
            }

            page.setRecords(records);
            return page;
        }
    }

    public Page<Map<String, Object>> getPageMap(Page<Long> ids) {
        if (ids == null) {
            return new Page();
        } else {
            Page<Map<String, Object>> page = new Page(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<Map<String, Object>> records = InstanceUtil.newArrayList();
            Iterator var4 = ids.getRecords().iterator();

            while (var4.hasNext()) {
                Long id = (Long) var4.next();
                records.add(InstanceUtil.transBean2Map(this.queryById(id)));
            }

            page.setRecords(records);
            return page;
        }
    }

    public <K> Page<K> getPage(Page<Long> ids, Class<K> cls) {
        if (ids == null) {
            return new Page();
        } else {
            Page<K> page = new Page(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<K> records = InstanceUtil.newArrayList();
            Iterator var5 = ids.getRecords().iterator();

            while (var5.hasNext()) {
                Long id = (Long) var5.next();
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                records.add(k);
            }

            page.setRecords(records);
            return page;
        }
    }

    public List<T> getList(List<Long> ids) {
        List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            Iterator var3 = ids.iterator();

            while (var3.hasNext()) {
                Long id = (Long) var3.next();
                T val = this.queryById(id);
                if (val != null) {
                    list.add(val);
                }
            }
        }

        return list;
    }

    public <K> List<K> getList(List<Long> ids, Class<K> cls) {
        List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            Iterator var4 = ids.iterator();

            while (var4.hasNext()) {
                Long id = (Long) var4.next();
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                list.add(k);
            }
        }

        return list;
    }

    @Transactional
    public void del(Long id, Long userId) throws Exception {
        try {
            T record = this.queryById(id);
            record.setEnable(Boolean.FALSE);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            this.mapper.updateById(record);
        } catch (Exception var4) {
            this.logger.error(var4.getMessage(), var4);
            throw new Exception(var4.getMessage(), var4);
        }
    }

    @Transactional
    public void delete(Long id) throws Exception {
        try {
            this.mapper.deleteById(id);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new Exception(var3.getMessage(), var3);
        }
    }

    @Transactional
    public Integer deleteByMap(Map<String, Object> columnMap) {
        return this.mapper.deleteByMap(columnMap);
    }

    @Transactional
    public Integer deleteByEntity(T t) {
        Wrapper<T> wrapper = new EntityWrapper(t);
        return this.mapper.delete(wrapper);
    }

    public Integer selectCount(Map<String, Object> params) {
        List<Long> ids = this.mapper.selectIdPage(params);
        return ids == null ? Integer.valueOf(0) : ids.size();
    }

    public Integer selectCount(String field, Object val) {
        Map<String, Object> params = new HashMap();
        params.put(field, val);
        List<Long> ids = this.mapper.selectIdPage(params);
        return ids == null ? Integer.valueOf(0) : ids.size();
    }

    public List<T> findByField(String field, Object val) {
        Map<String, Object> params = new HashMap();
        params.put(field, val);
        return this.queryList(params);
    }

    public List<T> findByField(String field, Object val, Integer max, String createBy) {
        Map<String, Object> params = new HashMap();
        params.put(field, val);
        if (max != null) {
            params.put("pageSize", max);
        }

        if (createBy != null) {
            params.put("createBy", createBy);
        }

        Page<T> page = this.query(params);
        return page.getRecords();
    }

    public T findObjectByField(String field, Object val) {
        List<T> list = this.findByField(field, val);
        return list != null && !list.isEmpty() ? (T) list.get(0) : null;
    }

    @Transactional
    public T save(T record) {
        return this.save(record, (Boolean) null);
    }

    public T save(T record, Boolean custId) {
        String msg;
        try {
            record.setUpdateTime(new Date());
            if (custId == null || !custId.booleanValue()) {
                record.setId((Long) null);
            }

            record.setCreateTime(new Date());
            if (record.getEnable() == null) {
                record.setEnable(Boolean.TRUE);
            }

            this.mapper.insert(record);
            record = (T) this.mapper.selectById(record.getId());
            return record;
        } catch (DuplicateKeyException var5) {
            this.logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", var5);
        } catch (Exception var6) {
            this.logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", var6);
            throw var6;
        }
        return null;
    }

    private T update(T record, Boolean updateAllField) throws Exception {
        String msg;
        try {
            record.setUpdateTime(new Date());
            if (record.getId() == null) {
                throw new RuntimeException("id不能为空.");
            } else {
                String lockKey = this.getLockKey("U" + record.getId());
                if (!this.redisLock.tryLock(lockKey, 0L, TimeUnit.SECONDS)) {
                    throw new Exception("数据不一致!请刷新页面重新编辑!");
                } else {
                    try {
                        T org = this.queryById(record.getId());
                        record.setCreateBy(org.getCreateBy());
                        record.setCreateTime(org.getCreateTime());
                        if (updateAllField != null && updateAllField.booleanValue()) {
                            this.mapper.updateAllColumnById(record);
                        } else {
                            this.mapper.updateById(record);
                        }

                        record = (T) this.mapper.selectById(record.getId());
                    } finally {
                        this.redisLock.unlock(lockKey);
                    }

                    return record;
                }
            }
        } catch (DuplicateKeyException var10) {
            this.logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", var10);
            throw new Exception("已经存在重复字段(字段唯一限制).");
        } catch (Exception var11) {
            this.logger.error("OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :", var11);
            throw var11;
        }
    }

    @Transactional
    public T updateAllField(T record) throws Exception {
        return this.update(record, true);
    }

    @Transactional
    public T updateNonNullFiled(T record) throws Exception {
        return this.update(record, false);
    }

    @Transactional
    public T update(T record) throws Exception {
        return this.update(record, true);
    }

    public T queryById(Long id) {
        return this.queryById(id, 1);
    }

    private T queryById(Long id, int times) {
        this.getCacheKey(id);
        T record = null;
        String lockKey = this.getLockKey(id);
        if (this.redisLock.tryLock(lockKey, 0L, TimeUnit.SECONDS)) {
            try {
                record = (T) this.mapper.selectById(id);
            } finally {
                this.redisLock.unlock(lockKey);
            }

            return record;
        } else if (times > 3) {
            return (T) this.mapper.selectById(id);
        } else {
            this.logger.debug(this.getClass().getSimpleName() + ":" + id + " retry queryById.");
            this.sleep(20);
            return this.queryById(id, times + 1);
        }
    }

    protected void sleep(int millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException var3) {
            this.logger.error("", var3);
        }

    }

    public T find(Long id) {
        return this.queryById(id);
    }

    public Page<T> query(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(this.mapper.selectIdPage(page, params));
        return this.getPage(page);
    }

    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(this.mapper.selectIdPage(page, params));
        return this.getPageMap(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        List<Long> ids = this.mapper.selectIdPage(params);
        List<T> list = this.getList(ids);
        return list;
    }

    public List<T> findAll() {
        Map<String, Object> params = new HashMap();
        List<Long> ids = this.mapper.selectIdPage(params);
        List<T> list = this.getList(ids);
        return list;
    }

    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Long> page = getPage(params);
        page.setRecords(this.mapper.selectIdPage(page, params));
        return this.getPage(page, cls);
    }

    public T selectOne(T entity) {
        return (T) this.mapper.selectOne(entity);
    }

    public List<T> selectList(Wrapper<T> entity) {
        return this.mapper.selectList(entity);
    }

    protected String getCacheKey(Object id) {
        String cacheName = this.getCacheKey();
        return cacheName + ":" + id;
    }

    protected String getLockKey(Object id) {
        String cacheName = this.getCacheKey();
        return cacheName + ":LOCK:" + id;
    }

    private String getCacheKey() {
        Class<?> cls = this.getClass();
        String cacheName = (String) Constants.cacheKeyMap.get(cls);
        if (StringUtils.isBlank(cacheName)) {
            GdsCacheConfig cacheConfig = cls.getAnnotation(GdsCacheConfig.class);
            if (cacheConfig == null) {
                cacheName = this.getClass().getName();
            } else {
                cacheName = cacheConfig.cacheNS() + ":" + cacheConfig.cacheName();
            }

            Constants.cacheKeyMap.put(cls, cacheName);
        }

        return cacheName;
    }
}
