package com.php25.common.db.repository;

import com.php25.common.core.util.PageUtil;
import com.php25.common.db.Db;
import com.php25.common.db.cnd.CndJdbc;
import com.php25.common.db.manager.JdbcModelManager;
import com.php25.common.db.specification.SearchParamBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author: penghuiping
 * @date: 2019/7/25 15:38
 * @description:
 */
public class JdbcDbRepositoryImpl<T, ID> implements JdbcDbRepository<T, ID> {

    protected Db db;

    protected Class<?> model;

    protected String pkName;

    public JdbcDbRepositoryImpl(Db db) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.model = (Class<?>) params[0];
        this.pkName = JdbcModelManager.getPrimaryKeyFieldName(model);
        this.db = db;
    }

    @Override
    public List<T> findAllEnabled() {
        return db.cndJdbc(model).whereEq("enable", 1).select();
    }

    @Override
    public Optional<T> findByIdEnable(ID id) {
        return Optional.of(db.cndJdbc(model).ignoreCollection(false).whereEq(pkName, id).andEq("enable", 1).single());
    }

    @Override
    public Optional<T> findOne(SearchParamBuilder searchParamBuilder) {
        CndJdbc cnd = db.cndJdbc(model).ignoreCollection(false).andSearchParamBuilder(searchParamBuilder);
        return Optional.ofNullable(cnd.single());
    }

    @Override
    public List<T> findAll(SearchParamBuilder searchParamBuilder) {
        CndJdbc cnd = db.cndJdbc(model).andSearchParamBuilder(searchParamBuilder);
        return cnd.select();
    }

    @Override
    public Page<T> findAll(SearchParamBuilder searchParamBuilder, Pageable pageable) {
        CndJdbc cnd = db.cndJdbc(model).andSearchParamBuilder(searchParamBuilder);
        Sort sort = pageable.getSort();
        Iterator<Sort.Order> iterator = sort.iterator();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            if (order.getDirection().isAscending()) {
                cnd.asc(order.getProperty());
            } else {
                cnd.desc(order.getProperty());
            }
        }
        int[] page = PageUtil.transToStartEnd(pageable.getPageNumber(), pageable.getPageSize());
        List<T> list = cnd.limit(page[0], page[1]).select();
        long total = cnd.clone().andSearchParamBuilder(searchParamBuilder).count();
        return new PageImpl<T>(list, pageable, total);
    }

    @Override
    public List<T> findAll(SearchParamBuilder searchParamBuilder, Sort sort) {
        CndJdbc cnd = db.cndJdbc(model).andSearchParamBuilder(searchParamBuilder);
        Iterator<Sort.Order> iterator = sort.iterator();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            if (order.getDirection().isAscending()) {
                cnd.asc(order.getProperty());
            } else {
                cnd.desc(order.getProperty());
            }
        }
        return cnd.select();
    }

    @Override
    public long count(SearchParamBuilder searchParamBuilder) {
        CndJdbc cnd = db.cndJdbc(model).andSearchParamBuilder(searchParamBuilder);
        return cnd.count();
    }
}
