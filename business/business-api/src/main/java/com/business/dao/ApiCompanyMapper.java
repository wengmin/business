package com.business.dao;

import com.business.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 创建人：Vsoft
 * @version 版本号：V1.0
 * @Description 功能说明：
 * @date 创建日期：2020/4/15
 * @time 创建时间: 9:36
 */
public interface ApiCompanyMapper extends BaseDao<CompanyVo> {

    List<CompanyFileVo> queryFileList(@Param(value = "companyId") Integer companyId);

    Integer saveFile(@Param(value = "fileList") List<CompanyFileVo> fileList);

    Integer deleteFile(Integer id);

    Integer deleteFileByCompany(Integer id);

    CompanyPostVo queryPost(Integer postId);

    List<CompanyPostVo> queryPostList(@Param(value = "companyId") Integer companyId, @Param(value = "postId") Integer postId);

    CompanyRoomVo queryRoom(Integer roomId);

    List<CompanyServiceVo> queryServiceList(@Param(value = "companyId") Integer companyId, @Param(value = "serviceClass") String serviceClass);
    List<CompanyServiceVo> queryServiceGroup(Integer companyId);
}
