package com.business.dao;

import com.business.entity.DocumentsFolderVo;
import com.business.entity.DocumentsPageVo;
import com.business.entity.DocumentsViewVo;
import com.business.entity.DocumentsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author xuyang
 * @email 295640759@qq.com
 * @date 2021-03-04 10:53:30
 */
public interface ApiDocumentsMapper extends BaseDao<DocumentsVo> {

    List<DocumentsFolderVo> queryFolderList(Map<String, Object> map);

    void saveFolder(DocumentsFolderVo vo);

    void deleteFolder(@Param("userId") Long userId, @Param("folderId") Integer folderId);


    List<DocumentsPageVo> queryPageList(Integer documentsId);

    void savePage(DocumentsPageVo vo);

    void deletePageByDocumentsId(Integer documentsId);

    void saveView(DocumentsViewVo vo);

    void deleteViewByDocumentsId(Integer documentsId);

    List<DocumentsFolderVo> queryFolderDocList(Map<String, Object> map);
}
