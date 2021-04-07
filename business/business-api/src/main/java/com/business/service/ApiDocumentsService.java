package com.business.service;

import com.business.dao.ApiDocumentsMapper;
import com.business.entity.DocumentsFolderVo;
import com.business.entity.DocumentsPageVo;
import com.business.entity.DocumentsViewVo;
import com.business.entity.DocumentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2021-03-04 10:53:30
 */
@Service
public class ApiDocumentsService {
    @Autowired
    private ApiDocumentsMapper documentsDao;

    public DocumentsVo queryObject(Integer id) {
        return documentsDao.queryObject(id);
    }

    public List<DocumentsVo> queryList(Map<String, Object> map) {
        return documentsDao.queryList(map);
    }

    public Integer queryTotal(Map<String, Object> map) {
        return documentsDao.queryTotal(map);
    }

    public void save(DocumentsVo vo) {
        vo.setCreateTime(new Date());
        documentsDao.save(vo);
    }

    public void update(DocumentsVo vo) {
        vo.setUpdateTime(new Date());
        documentsDao.update(vo);
    }

    public void delete(Integer id) {
        documentsDao.delete(id);
    }

    public List<DocumentsFolderVo> queryFolderList(Map<String, Object> map) {
        return documentsDao.queryFolderList(map);
    }

    public Integer hasFolderName(String name) {
        return documentsDao.hasFolderName(name);
    }

    public void saveFolder(DocumentsFolderVo vo) {
        vo.setCreateTime(new Date());
        documentsDao.saveFolder(vo);
    }

    public void deleteFolder(Long userId, Integer folderId) {
        documentsDao.deleteFolder(userId, folderId);
    }

    public List<DocumentsPageVo> queryPageList(Integer documentsId) {
        return documentsDao.queryPageList(documentsId);
    }

    public void savePage(DocumentsPageVo vo) {
        vo.setCreateTime(new Date());
        documentsDao.savePage(vo);
    }

    public void deletePageByDocumentsId(Integer documentsId) {
        documentsDao.deletePageByDocumentsId(documentsId);
    }

    public void saveView(DocumentsViewVo vo) {
        vo.setCreateTime(new Date());
        documentsDao.saveView(vo);
    }

    public void deleteViewByDocumentsId(Integer documentsId) {
        documentsDao.deleteViewByDocumentsId(documentsId);
    }

    public List<DocumentsFolderVo> queryFolderDocList(Map<String, Object> map) {
        return documentsDao.queryFolderDocList(map);
    }
}
