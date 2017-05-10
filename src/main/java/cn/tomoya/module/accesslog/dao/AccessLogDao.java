package cn.tomoya.module.accesslog.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.tomoya.module.accesslog.entity.AccessLog;
import cn.tomoya.module.user.entity.User;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Repository
@CacheConfig(cacheNames = "accessLogs")
public interface AccessLogDao extends JpaRepository<AccessLog, Integer> {

}
