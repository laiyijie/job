package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbAdmin;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by laiyijie on 12/2/17.
 */

public interface TbAdminRepository extends CrudRepository<TbAdmin, Integer> {
    TbAdmin findByUsername(String username);
}
