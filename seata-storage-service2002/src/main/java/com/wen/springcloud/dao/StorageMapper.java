package com.wen.springcloud.dao;

import com.wen.springcloud.domain.Storage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Storage record);

    Storage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Storage record);

    //扣减库存
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}